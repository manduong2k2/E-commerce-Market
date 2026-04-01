<?php

namespace App\Http\Requests\Brand;

use Illuminate\Foundation\Http\FormRequest;

class StoreRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'name'          => ['string', 'max:255', 'nullable'],
            'description'   => ['string', 'max:255', 'nullable'],
        ];
    }
}
