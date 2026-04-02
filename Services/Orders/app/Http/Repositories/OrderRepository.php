<?php
namespace App\Http\Repositories;

use App\Models\Order;

class OrderRepository extends EloquentRepository implements OrderRepositoryInterface
{
    public function getModel(): string
    {
        return Order::class;
    }

    public function getSearchScopes(): array
    {
        return [
            'name', 'items.Order_name', 'items.Order_variant_name'
        ];
    }
}
