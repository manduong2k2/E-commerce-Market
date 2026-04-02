<?php

namespace App\Http\Services;

use App\Facades\HttpClientInterface;
use App\Http\Repositories\CartRepositoryInterface;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Arr;

class CartService implements CartServiceInterface
{
    public function __construct(
        protected CartRepositoryInterface $cartRepository,
        protected HttpClientInterface $client
    ) {}

    public function getAllCarts(array $conditions)
    {
        return $this->cartRepository->all([], $conditions);
    }

    public function getCartById($id)
    {
        return $this->cartRepository->find($id);
    }

    public function addToCart(array $data)
    {
        return DB::transaction(function () use ($data) {
            $products = [];
            foreach ($data['items'] as $item) {
                $productUrl = 'http://localhost:8000/api/v1/products/' . Arr::get($item, 'productId');

                $products[] = $this->client->get(
                    $productUrl, [], [], []
                );

                $products[] = $products[0]['data'];
            }

            dd($products);

            return $this->cartRepository->createOrUpdate($products);
        });
    }

    public function updateCart($id, array $data)
    {
        return $this->cartRepository->update($id, $data);
    }

    public function deleteCart($id)
    {
        return $this->cartRepository->delete($id);
    }
}
