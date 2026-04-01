<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Order\IndexRequest;
use App\Http\Requests\Order\StoreRequest;
use App\Http\Requests\Order\UpdateRequest;
use App\Http\Resources\Order\OrderResource;
use App\Http\Services\OrderServiceInterface;
use App\Models\Order;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    public function __construct(protected OrderServiceInterface $service){}

    public function index(IndexRequest $request)
    {
        return $this->service->getAllOrders(
            $request->validated()
        );
    }

    public function show(Order $order)
    {
        return resolve_resource(OrderResource::class, $order);
    }

    public function update(UpdateRequest $request, Order $order)
    {
        return resolve_resource(OrderResource::class, 
            $this->service->updateOrder($order, $request->validated())
        );
    }
}
