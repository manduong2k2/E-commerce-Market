<?php

namespace App\Http\Resources\Product;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\Category\CategoryShortResourceCollection;
use App\Http\Resources\Brand\BrandShortResource;

class ProductResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'id'            => $this->id,
            'name'          => $this->name,
            'description'   => $this->description,
            'categories'    => new CategoryShortResourceCollection($this->categories),
            'brand'         => new BrandShortResource($this->brand),
            'variants'      => ProductVariantResource::collection($this->variants),
            'files'         => $this->getFiles(),
            'createdAt'     => $this->created_at?->toDateTimeString(),
            'updatedAt'     => $this->updated_at?->toDateTimeString(),
        ];
    }
}
