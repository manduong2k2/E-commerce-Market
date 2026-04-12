<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Cart\IndexRequest;
use App\Http\Requests\Cart\StoreRequest;
use App\Http\Services\CartServiceInterface;
use App\Models\Cart;
use Illuminate\Http\Request;

class CartController extends Controller
{
    public function __construct(protected CartServiceInterface $service){}

    public function index(IndexRequest $request)
    {
        return $this->service->getAllCarts($request->validated());
    }

    public function store(StoreRequest $request)
    {
        return $this->service->addToCart($request->validated());
    }

    public function show(Cart $cart)
    {
        //
    }

    public function update(Request $request, Cart $cart)
    {
        //
    }

    public function destroy(Cart $cart)
    {
        //
    }
}
