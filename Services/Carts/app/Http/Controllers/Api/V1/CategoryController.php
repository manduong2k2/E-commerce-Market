<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Category\StoreRequest;
use App\Http\Requests\Category\UpdateRequest;
use App\Http\Resources\Category\CategoryResource;
use App\Http\Resources\Category\CategoryResourceCollection;
use App\Http\Services\CategoryServiceInterface;
use App\Models\Category;

class CategoryController extends Controller
{
    public function __construct(protected CategoryServiceInterface $categoryService){}

    public function index()
    {
        return new CategoryResourceCollection($this->categoryService->getAllCategories());
    }

    public function store(StoreRequest $request)
    {
        return new CategoryResource($this->categoryService->createCategory($request->validated()));
    }

    public function show(Category $category)
    {
        return new CategoryResource($category);
    }

    public function update(UpdateRequest $request, Category $category)
    {
        return new CategoryResource($this->categoryService->updateCategory($category, $request->validated()));
    }

    public function destroy(Category $category)
    {
        $category->delete();
        return response()->noContent();
    }
}
