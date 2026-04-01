<?php

use App\Http\Controllers\Api\V1\CartController;
use App\Http\Middleware\AuthenticateMiddleware;
use Illuminate\Support\Facades\Route;


Route::middleware([AuthenticateMiddleware::class])->group(function () {
    Route::apiResource('carts', CartController::class);
});
