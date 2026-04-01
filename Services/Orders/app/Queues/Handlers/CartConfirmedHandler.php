<?php

namespace App\Queues\Handlers;

use App\Http\Services\OrderService;
use App\Queues\Validators\OrderRules;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;
use PhpAmqpLib\Message\AMQPMessage;

class CartConfirmedHandler implements CartConfirmedHandlerInterface
{
    public function __construct(protected OrderService $service){}

    public function execute(AMQPMessage $msg): void
    {
        $data = json_decode($msg->getBody(), true);

        $validator = Validator::make($data, OrderRules::store());

        if ($validator->fails()) {
            throw new ValidationException($validator->errors());
        }

        $validated = $validator->validated();

        $this->service->createOrder($validated);
    }
}