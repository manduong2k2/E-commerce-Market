<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use App\Utils\RabbitMQConsumer;

class RabbitMQListen extends Command
{
    protected $signature = 'rabbitmq:listen';
    protected $description = 'Listen RabbitMQ messages';

    public function handle()
    {
        $this->info('Starting RabbitMQ consumer...');
        $consumer = new RabbitMQConsumer();
        $consumer->consume();
    }
}