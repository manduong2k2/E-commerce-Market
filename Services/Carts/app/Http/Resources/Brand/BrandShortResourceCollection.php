<?php

namespace App\Http\Resources\Brand;

use Illuminate\Http\Resources\Json\ResourceCollection;

class BrandShortResourceCollection extends ResourceCollection
{
    public $collects = BrandShortResource::class;
}
