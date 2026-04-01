<?php

namespace App\Queues\Handlers;

use Illuminate\Support\Arr;
use PhpAmqpLib\Message\AMQPMessage;
use Tymon\JWTAuth\Facades\JWTAuth;

class LogoutHandler implements LogoutHandlerInterface
{
    public function execute(AMQPMessage $msg): void
    {
        $data = json_decode($msg->getBody(), true);

        $token = Arr::get($data, 'token');

        if (!$token) {
            return;
        }

        try {
            JWTAuth::setToken($token)->invalidate();
        } catch (\Exception $e) {
            return;
        }
    }
}
