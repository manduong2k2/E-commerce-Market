<?php

namespace App\Http\Services;

interface CartServiceInterface
{
    public function getAllCarts(array $conditions);

    public function getCartById($id);

    public function addToCart(array $data);

    public function updateCart($id, array $data);

    public function deleteCart($id);
}