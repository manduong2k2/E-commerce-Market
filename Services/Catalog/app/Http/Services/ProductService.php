<?php

namespace App\Http\Services;

use App\Http\Repositories\ProductRepositoryInterface;
use Illuminate\Support\Arr;
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
        $product = $this->ProductRepository->create($data);

        $files = Arr::get($data, 'files', []);

        if (!empty($files)) {
            foreach ($files as $file) {
                $product->uploadFile($file, 'products');
            }
        }

        return $product;
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
