<?php

namespace App\Http\Resources\Category;

use Illuminate\Http\Resources\Json\ResourceCollection;

class CategoryShortResourceCollection extends ResourceCollection
{
    public $collects = CategoryShortResource::class;
}
