<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use Symfony\Component\Process\Process;

class RabbitMQListener extends Command
{
    protected $signature = 'rabbitmq:listen';

    protected array $consumers = [
        'logout' => [
            'command' => 'rabbitmq:consume:logout',
            'processes' => 2,
        ]
    ];

    public function handle()
    {
        $processes = [];

        foreach ($this->consumers as $name => $config) {
            for ($i = 0; $i < $config['processes']; $i++) {

                $process = new Process(['php', 'artisan', $config['command']]);
                $process->start();

                $processes[] = [
                    'name'    => $name,
                    'index'   => $i,
                    'command' => $config['command'],
                    'process' => $process,
                ];
            }
        }

        while (true) {
            foreach ($processes as &$proc) {
                /** @var Process $process */
                $process = $proc['process'];

                if (!$process->isRunning()) {
                    $this->error(sprintf(
                        '[%s-%d] died → restarting...',
                        $proc['name'],
                        $proc['index']
                    ));

                    // log error output nếu có
                    $this->line($process->getErrorOutput());

                    // restart
                    $newProcess = new Process(['php', 'artisan', $proc['command']]);
                    $newProcess->start();

                    $proc['process'] = $newProcess;
                }
            }

            sleep(2);
        }
    }
}
