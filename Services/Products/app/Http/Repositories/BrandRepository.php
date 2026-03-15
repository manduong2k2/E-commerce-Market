<?php
namespace App\Http\Repositories;

use App\Models\Brand;

class BrandRepository extends EloquentRepository
{
    public function getModel(): string
    {
        return Brand::class;
    }

    public function getSearchScopes(): array
    {
        return ['name'];
    }
}