<?php

use App\Http\Controllers\Api\V1\OrderController;
use App\Http\Middleware\AuthenticateMiddleware;
use Illuminate\Support\Facades\Route;

Route::middleware([AuthenticateMiddleware::class])->group(function () {
    Route::apiResource('orders', OrderController::class)->except(['store', 'update']);
});
