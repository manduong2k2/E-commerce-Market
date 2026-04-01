<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;

class Discount extends Model
{
    use HasUuids;

    protected $fillable = [
        'order_id',
        'voucher_id',
        'discount_amount'
    ];

    // Relations
    public function order()
    {
        return $this->belongsTo(Order::class);
    }
}
