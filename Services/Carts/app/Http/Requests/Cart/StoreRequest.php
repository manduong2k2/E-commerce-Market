<?php

namespace App\Http\Requests\Cart;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class StoreRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'items'                             => ['array', 'min:1'],
            'items.*.productId'                 => ['uuid', 'max:255'],
            'items.*.productVariantId'          => ['uuid', 'max:255'],
            'items.*.quantity'                  => ['numeric', 'min:1', 'nullable']
        ];
    }
}
