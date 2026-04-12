<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class BaseIndexRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'filters'               => ['nullable', 'array'],
            'filters.*.key'         => ['required', 'string', 'max:255'],
            'filters.*.operator'    => ['string', Rule::in(['>', '<', '>=', '<=', '<>', '=', 'IN', 'NOT IN'])],
            'filters.*.value'       => ['nullable'], 

            'search'                => ['nullable', 'string', 'max:255'],
            'page'                  => ['nullable', 'integer', 'min:1'],
            'perPage'               => ['nullable', 'integer', 'min:1', 'max:100'],
        ];
    }

    protected function withValidator($validator)
    {
        $validator->sometimes('filters.*.value', 'array', function ($input, $item) {
            return in_array(strtoupper($item['operator'] ?? ''), ['IN', 'NOT IN']);
        });

        $validator->sometimes('filters.*.value', 'string|max:255', function ($input, $item) {
            return !in_array(strtoupper($item['operator'] ?? ''), ['IN', 'NOT IN']);
        });
    }
}
