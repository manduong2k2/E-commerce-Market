<?php

namespace App\Http\Requests\Category;

use Illuminate\Foundation\Http\FormRequest;

class UpdateRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'name'          => ['string', 'max:255', 'nullable'],
            'description'   => ['string', 'max:255', 'nullable'],
            'image'         => ['image', 'mimes:jpeg,png,jpg,gif,svg', 'max:2048', 'nullable'],
            'parent_id'     => ['integer', 'exists:categories,id', 'nullable'],
        ];
    }
}
