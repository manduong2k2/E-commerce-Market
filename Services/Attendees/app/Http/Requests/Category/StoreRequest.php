<?php

namespace App\Http\Requests\Category;

use Illuminate\Foundation\Http\FormRequest;

class StoreRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'name'          => ['string', 'max:255', 'nullable'],
            'description'   => ['string', 'max:255', 'nullable'],
            'parent_id'     => ['integer', 'exists:categories,id', 'nullable'],
        ];
    }
}
