<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Concerns\HasUuids;
use App\Enums\ShippingStatus;

class Shipping extends Model
{
    use HasUuids;

    protected $table = 'shippings';

    // Mass assignable fields
    protected $fillable = [
        'order_id',
        'transport_trip_id',
        'country',
        'city',
        'district',
        'ward',
        'street',
        'street_id',
        'house_number',
        'address_details',
        'phone',
        'email',
        'status',
    ];

    // Casts
    protected $casts = [
        'status' => ShippingStatus::class, 
    ];

    // Relations
    public function order()
    {
        return $this->belongsTo(Order::class);
    }
}