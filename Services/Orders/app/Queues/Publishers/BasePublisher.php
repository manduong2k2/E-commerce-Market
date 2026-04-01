<?php

namespace App\Queues\Publishers;

use App\Queues\Connections\BaseConnection;
use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

abstract class BasePublisher
{
    protected AMQPStreamConnection $connection;
    protected $channel;
    protected string $queue;

    public abstract function getQueue(): string;

    private function setQueue()
    {
        $this->queue = $this->getQueue();
    }

    public function __construct()
    {
        $this->setQueue();

        $this->connection = BaseConnection::make();
        $this->channel = $this->connection->channel();

        $this->channel->queue_declare(
            $this->queue,
            false,
            true,
            false,
            false
        );
    }

    public function publish(array $payload): void
    {
        $message = new AMQPMessage(
            json_encode($payload),
            [
                'delivery_mode' => 2 
            ]
        );

        $this->channel->basic_publish(
            $message,
            '', 
            $this->queue
        );
    }

    public function __destruct()
    {
        $this->channel->close();
        $this->connection->close();
    }
}