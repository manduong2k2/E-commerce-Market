<?php

return [
    'base_url'  => env('GATEWAY_BASE_URL', 'http://localhost:8000'),
    'admin_url' => env('GATEWAY_ADMIN_URL', 'http://localhost:8001'),
    'timeout'   => env('GATEWAY_TIMEOUT', 30),
];