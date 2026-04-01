<?php

return [
    'driver' => 'rabbitmq',
    'host' => env('RABBITMQ_HOST', '127.0.0.1'),
    'port' => env('RABBITMQ_PORT', 5673),
    'vhost' => env('RABBITMQ_VHOST', '/'),
    'login' => env('RABBITMQ_LOGIN', 'admin'),
    'password' => env('RABBITMQ_PASSWORD', 'admin'),
    'queue' => env('RABBITMQ_QUEUE', 'default'),
    'options' => [
        'exchange' => [
            'name' => env('RABBITMQ_EXCHANGE_NAME'),
            'type' => env('RABBITMQ_EXCHANGE_TYPE', 'direct'),
            'durable' => true,
        ],
    ],
];