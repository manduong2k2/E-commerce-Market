<?php

namespace App\Http\Services;

interface OrderServiceInterface
{
    public function getAllOrders(array $conditions);

    public function getOrderById($id);

    public function createOrder(array $data);

    public function updateOrder($order, array $data);

    public function deleteOrder($order);
}