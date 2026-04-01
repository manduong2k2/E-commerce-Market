<?php

namespace App\Queues\Handlers;

use Illuminate\Support\Arr;
use PhpAmqpLib\Message\AMQPMessage;

class UserActivatedHandler implements UserActivatedHandlerInterface
{
    public function execute(AMQPMessage $msg): void
    {
        $data = json_decode($msg->getBody(), true);
    }
}