<?php

namespace App\Http\Resources\Product;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ProductShortResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'id'            => $this->id,
            'name'          => $this->name,
            'categories'    => $this->categories->pluck('name'),
            'brand'         => $this->brand ? $this->brand->name : null,
            'priceMin'      => $this->variants->min('price'),
            'priceMax'      => $this->variants->max('price'),
            'files'         => $this->getFiles(),
        ];
    }
}
