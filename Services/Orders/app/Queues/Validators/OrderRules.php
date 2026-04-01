<?php

namespace App\Queues\Validators;

use App\Enums\OrderStatus;
use Illuminate\Validation\Rule;

class OrderRules
{
    public static function store(): array
    {
        return [
            'customer_id'           => ['uuid', 'max:255'],
            'status'                => ['string', 'max:255', Rule::in(array_map(fn($case) => $case->value, OrderStatus::cases()))],
            'total'                 => ['numeric', 'min:0', 'nullable'],
            'notes'                 => ['string', 'max:255', 'nullable'],

            'items'                             => ['array', 'min:1'],
            'items.*.productName'               => ['numeric', 'min:0', 'nullable'],
            'items.*.productVariantId'          => ['uuid', 'max:255'],
            'items.*.productVariantName'        => ['numeric', 'min:0', 'nullable'],
            'items.*.quantity'                  => ['numeric', 'min:1', 'nullable'],
            'items.*.price'                     => ['string', 'max:255', Rule::unique('product_variants', 'code')],
        ];
    }
}
