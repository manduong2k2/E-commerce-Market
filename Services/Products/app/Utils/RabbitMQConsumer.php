<?php

namespace App\Utils;

use Illuminate\Support\Facades\Log;
use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

class RabbitMQConsumer
{
    protected $connection;
    protected $channel;
    protected $queue;

    public function __construct()
    {
        $this->queue = env('RABBITMQ_QUEUE', 'test.queue');

        $this->connection = new AMQPStreamConnection(
            config('rabbit-mq.host'),
            config('rabbit-mq.port'),
            config('rabbit-mq.login'),
            config('rabbit-mq.password'),
            config('rabbit-mq.vhost')
        );

        $this->channel = $this->connection->channel();

        // Đảm bảo queue tồn tại
        $this->channel->queue_declare($this->queue, false, true, false, false);
    }

    public function consume()
    {
        echo " [*] Waiting for messages in {$this->queue}. To exit press CTRL+C\n";

        $callback = function (AMQPMessage $msg) {
            $data = json_decode($msg->getBody(), true);

            // Xử lý message ở đây
            echo " [x] Received: " . $msg->getBody() . "\n";

            // Ví dụ lưu vào log Laravel
            Log::info('RabbitMQ message received', $data ?? []);

            // Xác nhận message đã được xử lý
            $msg->ack();
        };

        // Chỉ nhận 1 message 1 lần (fair dispatch)
        $this->channel->basic_qos(null, 1, null);

        $this->channel->basic_consume(
            $this->queue,
            '',
            false,
            false,
            false,
            false,
            $callback
        );

        // Loop để consumer luôn lắng nghe
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