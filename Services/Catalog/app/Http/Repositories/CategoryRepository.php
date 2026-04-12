<?php
namespace App\Http\Repositories;

use App\Models\Category;

class CategoryRepository extends EloquentRepository implements CategoryRepositoryInterface
{
    public function getModel(): string
    {
        return Category::class;
    }

    public function getSearchScopes(): array
    {
        return ['name'];
    }
}