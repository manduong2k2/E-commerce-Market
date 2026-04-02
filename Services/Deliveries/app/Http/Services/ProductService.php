<?php

namespace App\Http\Services;

use App\Http\Repositories\ProductRepositoryInterface;
use Illuminate\Support\Facades\Auth;

class ProductService implements ProductServiceInterface
{
    public function __construct(protected ProductRepositoryInterface $ProductRepository) {}

    public function getAllProducts(array $conditions)
    {
        return $this->ProductRepository->all([], $conditions);
    }

    public function getProductById($id)
    {
        return $this->ProductRepository->find($id);
    }

    public function createProduct(array $data)
    {
        return $this->ProductRepository->create($data);
    }

    public function updateProduct($id, array $data)
    {
        return $this->ProductRepository->update($id, $data);
    }

    public function deleteProduct($id)
    {
        return $this->ProductRepository->delete($id);
    }
}
