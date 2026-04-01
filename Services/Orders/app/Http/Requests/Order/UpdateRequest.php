<?php

namespace App\Http\Requests\Order;

use App\Enums\OrderStatus;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class UpdateRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'customer_id'           => ['sometimes', 'uuid', 'max:255'],
            'status'                => ['sometimes', 'string', 'max:255', Rule::in(array_map(fn($case) => $case->value, OrderStatus::cases()))],
            'total'                 => ['sometimes', 'numeric', 'min:0', 'nullable'],
            'notes'                 => ['sometimes', 'string', 'max:255', 'nullable'],

            'items'         => ['array', 'nullable'],
            'items.*.id'                        => ['sometimes', 'sometimes', 'uuid', Rule::exists('order_items', 'id')],
            'items.*.productName'               => ['sometimes', 'numeric', 'min:0', 'nullable'],
            'items.*.productVariantId'          => ['sometimes', 'uuid', 'max:255'],
            'items.*.productVariantName'        => ['sometimes', 'numeric', 'min:0', 'nullable'],
            'items.*.quantity'                  => ['sometimes', 'numeric', 'min:1', 'nullable'],
            'items.*.price'                     => ['sometimes', 'string', 'max:255', Rule::unique('product_variants', 'code')],
        ];
    }
}
