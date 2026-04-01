<?php

namespace App\Queues\Connections;

use PhpAmqpLib\Connection\AMQPStreamConnection;

class BaseConnection
{
    public static function make(): AMQPStreamConnection
    {
        return new AMQPStreamConnection(
            config('rabbit-mq.host'),
            config('rabbit-mq.port'),
            config('rabbit-mq.login'),
            config('rabbit-mq.password'),
            config('rabbit-mq.vhost')
        );
    }
}