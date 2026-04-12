<?php

namespace App\Console\Commands\Core;

use Illuminate\Console\Command;
use Illuminate\Filesystem\Filesystem;

class MakeConsumerCommand extends Command
{
    protected $signature = 'make:consumer {name}';
    protected $description = 'Create a queue consumer class';

    protected Filesystem $files;

    public function __construct(Filesystem $files)
    {
        parent::__construct();
        $this->files = $files;
    }

    public function handle()
    {
        $name = $this->argument('name'); // ex: LogoutConsumer
        $baseNamespace = 'App\\Queues\\Consumers';
        $basePath = app_path('Queues/Consumers');

        if (!$this->files->exists($basePath)) {
            $this->files->makeDirectory($basePath, 0755, true);
        }

        $classPath = $basePath . '/' . $name . '.php';

        if ($this->files->exists($classPath)) {
            $this->warn("Consumer already exists: {$name}");
            return;
        }

        $handlerClass = str_replace('Consumer', 'Handler', $name);

        $content = <<<PHP
            <?php

            namespace {$baseNamespace};

            use App\Constants\RabbitQueue;
            use App\Queues\Handlers\\{$handlerClass};

            class {$name} extends BaseConsumer
            {
                public function getHandler(): string
                {
                    return {$handlerClass}::class;
                }

                public function getQueue(): string
                {
                    return RabbitQueue::{$this->formatQueueName($name)};
                }
            }
            PHP;

        $this->files->put($classPath, $content);
        $this->info("Created Consumer: {$name}");
    }

    /**
     * Convert Consumer name to queue constant
     * Example: LogoutConsumer → LOGOUT
     */
    protected function formatQueueName(string $consumerName): string
    {
        // Remove 'Consumer' suffix
        $base = str_replace('Consumer', '', $consumerName);
        // Uppercase
        return strtoupper($base);
    }
}