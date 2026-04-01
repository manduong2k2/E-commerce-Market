<?php

namespace App\Http\Resources\Product;

use Illuminate\Http\Resources\Json\ResourceCollection;

class ProductShortResourceCollection extends ResourceCollection
{
    public $collects = ProductShortResource::class;
}
