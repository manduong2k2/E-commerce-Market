<?php

namespace App\Http\Resources\Brand;

use Illuminate\Http\Resources\Json\ResourceCollection;

class BrandResourceCollection extends ResourceCollection
{
    public $collects = BrandResource::class;
}
