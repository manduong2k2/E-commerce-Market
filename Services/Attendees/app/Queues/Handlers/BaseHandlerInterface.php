<?php
namespace App\Queues\Handlers;

use PhpAmqpLib\Message\AMQPMessage;

interface BaseHandlerInterface
{
    public function execute(AMQPMessage $msg): void;
}