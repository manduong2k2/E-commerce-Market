<?php

namespace App\Queues\Consumers;

use App\Constants\RabbitQueue;
use App\Queues\Handlers\UserActivatedHandler;

class UserActivatedConsumer extends BaseConsumer
{
    public function getHandler(): string
    {
        return UserActivatedHandler::class;
    }

    public function getQueue(): string
    {
        return RabbitQueue::USERACTIVATED;
    }
}