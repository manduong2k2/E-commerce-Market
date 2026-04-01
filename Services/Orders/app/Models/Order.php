<?php

namespace App\Models;

use App\Enums\OrderStatus;
use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Order extends Model
{
    use HasUuids;

    protected $fillable = [
        'customer_id',
        'status',
        'total',
        'notes'
    ];

    // Casts
    protected $casts = [
        'status' => OrderStatus::class, 
    ];

    public function items(): HasMany
    {
        return $this->hasMany(OrderItem::class);
    }
}
