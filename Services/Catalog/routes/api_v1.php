<?php

use App\Http\Controllers\Api\V1\ProductController;
use App\Http\Controllers\Api\V1\BrandController;
use App\Http\Controllers\Api\V1\CategoryController;
use App\Http\Middleware\AuthenticateMiddleware;
use App\Http\Middleware\AuthorizeMiddleware;
use Illuminate\Support\Facades\Route;


Route::middleware([AuthenticateMiddleware::class])->group(function () {

    Route::apiResource('products', ProductController::class)->except(['index']);
});

Route::get('products', [ProductController::class,'index'])->name('products.index');

Route::apiResource('brands', BrandController::class);
Route::apiResource('categories', CategoryController::class);
