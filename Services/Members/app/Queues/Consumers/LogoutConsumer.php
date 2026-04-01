<?php
namespace App\Queues\Consumers;

use App\Constants\RabbitQueue;
use App\Queues\Handlers\LogoutHandler;

class LogoutConsumer extends BaseConsumer
{
    public function getHandler(): string
    {
        return LogoutHandler::class;
    }

    public function getQueue(): string
    {
        return RabbitQueue::LOGOUT;
    }
}