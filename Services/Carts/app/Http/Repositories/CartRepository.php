<?php
namespace App\Http\Repositories;

use App\Models\Cart;

class CartRepository extends EloquentRepository
{
    public function getModel(): string
    {
        return Cart::class;
    }

    public function getSearchScopes(): array
    {
        return [];
    }

    public function createOrUpdate(array $data)
    {
        dd($data);
    }
}
