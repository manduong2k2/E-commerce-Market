<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;

class OrderItem extends Model
{
    use HasUuids;

    protected $fillable = [
        'order_id',
        'product_variant_id',
        'product_name',
        'product_variant_name',
        'quantity',
        'price',
        'notes'
    ];

    // Relations
    public function order()
    {
        return $this->belongsTo(Order::class);
    }
}
