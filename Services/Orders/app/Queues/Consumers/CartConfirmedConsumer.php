<?php

namespace App\Queues\Consumers;

use App\Constants\RabbitQueue;
use App\Queues\Handlers\CartConfirmedHandler;

class CartConfirmedConsumer extends BaseConsumer
{
    public function getHandler(): string
    {
        return CartConfirmedHandler::class;
    }

    public function getQueue(): string
    {
        return RabbitQueue::CARTCONFIRMED;
    }
}