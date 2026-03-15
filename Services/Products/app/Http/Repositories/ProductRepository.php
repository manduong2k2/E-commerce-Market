<?php
namespace App\Http\Repositories;

use App\Models\Product;

class ProductRepository extends EloquentRepository
{
    public function getModel(): string
    {
        return Product::class;
    }

    public function getSearchScopes(): array
    {
        return [
            'name', 'variants.name', 'variants.code'
        ];
    }
}
