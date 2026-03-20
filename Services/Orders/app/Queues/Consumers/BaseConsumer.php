<?php
namespace App\Queues\Consumers;

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

abstract class BaseConsumer
{
    protected AMQPStreamConnection $connection;
    protected $channel;
    protected string $queue;
    protected $handler;

    public abstract function getHandler(): string;

    private function setHandler()
    {
        $this->handler = app($this->getHandler());
    }

    public abstract function getQueue(): string;

    private function setQueue()
    {
        $this->queue = $this->getQueue();
    }

    public function __construct()
    {
        $this->setHandler();
        $this->setQueue();

        $this->connection = new AMQPStreamConnection(
            config('rabbit-mq.host'),
            config('rabbit-mq.port'),
            config('rabbit-mq.login'),
            config('rabbit-mq.password'),
            config('rabbit-mq.vhost')
        );

        $this->channel = $this->connection->channel();
        $this->channel->queue_declare($this->queue, false, true, false, false);
    }

    public function consume(): void
    {
        echo " [*] Waiting for messages in {$this->queue}. To exit press CTRL+C\n";

        $callback = function (AMQPMessage $msg) {
            $this->handler->execute($msg);
            $msg->ack();
        };

        $this->channel->basic_qos(null, 1, null);
        $this->channel->basic_consume($this->queue, '', false, false, false, false, $callback);

        while ($this->channel->is_open()) {
            $this->channel->wait();
        }
    }

    public function __destruct()
    {
        $this->channel->close();
        $this->connection->close();
    }
}