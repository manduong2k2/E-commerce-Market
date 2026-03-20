<?php

namespace App\Http\Services;

interface ProductServiceInterface
{
    public function getAllProducts(array $conditions);

    public function getProductById($id);

    public function createProduct(array $data);

    public function updateProduct($id, array $data);

    public function deleteProduct($id);
}