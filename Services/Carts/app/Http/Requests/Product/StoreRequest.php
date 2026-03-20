<?php

namespace App\Http\Requests\Product;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class StoreRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'name'          => ['string', 'max:255', 'nullable'],
            'description'   => ['string', 'max:255', 'nullable'],
            'code'          => ['string', 'max:255', 'nullable', Rule::unique('products', 'code')],
            'price'         => ['numeric', 'min:0', 'nullable'],
            'brandId'       => ['uuid', 'exists:brands,id'],
            'statusId'      => ['uuid', 'exists:product_statuses,id'],

            'categories'        => ['array', 'nullable'],
            'categories.*.id'   => ['uuid', 'exists:categories,id'],

            'files'        => ['array', 'nullable'],
            'files.*'      => ['file', 'max:10240'],

            'variants'          => ['array', 'nullable'],
            'variants.*.name'       => ['string', 'max:255', 'nullable'],
            'variants.*.price'      => ['numeric', 'min:0', 'nullable'],
            'variants.*.description'                => ['string', 'max:255', 'nullable'],
            'variants.*.code'       => ['string', 'max:255', Rule::unique('product_variants', 'code')],

            'variants.*.extraAttributes'       => ['array', 'nullable'],
            'variants.*.extraAttributes.*.key'     => ['string', 'max:255'],
            'variants.*.extraAttributes.*.value'   => ['string', 'max:255'],
        ];
    }
}
