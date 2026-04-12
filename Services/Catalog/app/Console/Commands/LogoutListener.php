<?php

namespace App\Console\Commands;

use App\Queues\Consumers\LogoutConsumer;
use Illuminate\Console\Command;

class LogoutListener extends Command
{
    protected $signature = 'rabbitmq:consume:logout';
    protected $description = 'Listen logout messages';

    public function handle()
    {

        $this->info("Starting consumer for queue: logout");

        $consumer = new LogoutConsumer();

        $consumer->consume();
    }
}
