<?php

namespace App\Http\Services;

use App\Http\Repositories\OrderRepositoryInterface;
use Illuminate\Support\Facades\Auth;

class OrderService implements OrderServiceInterface
{
    public function __construct(protected OrderRepositoryInterface $OrderRepository) {}

    public function getAllOrders(array $conditions)
    {
        return $this->OrderRepository->all([], $conditions);
    }

    public function getOrderById($id)
    {
        return $this->OrderRepository->find($id);
    }

    public function createOrder(array $data)
    {
        return $this->OrderRepository->create($data);
    }

    public function updateOrder($order, array $data)
    {
        return $this->OrderRepository->update($order, $data);
    }

    public function deleteOrder($id)
    {
        return $this->OrderRepository->delete($id);
    }
}
