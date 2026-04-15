<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Product\IndexRequest;
use App\Http\Requests\Product\StoreRequest;
use App\Http\Requests\Product\UpdateRequest;
use App\Http\Resources\Product\ProductResource;
use App\Http\Resources\Product\ProductShortResourceCollection;
use App\Http\Services\ProductServiceInterface;
use App\Models\Product;
use App\Policies\ProductProlicy;

class ProductController extends Controller
{
    public function __construct(protected ProductServiceInterface $productService){}

    public function index(IndexRequest $request)
    {
        $products = $this->productService->getAllProducts($request->validated());

        return new ProductShortResourceCollection($products);
    }

    public function store(StoreRequest $request)
    {
        policy_authorize(ProductProlicy::class, 'create', user());

        return new ProductResource($this->productService->createProduct($request->validated()));
    }

    public function show(Product $product)
    {
        policy_authorize(ProductProlicy::class, 'details', user(), $product);

        return new ProductResource($product);
    }

    public function update(UpdateRequest $request, Product $product)
    {
        policy_authorize(ProductProlicy::class, 'update', user(), $product);

        return new ProductResource($this->productService->updateProduct($product, $request->validated()));
    }

    public function destroy(Product $product)
    {
        policy_authorize(ProductProlicy::class, 'delete', user(), $product);

        $product->delete();
        return response()->noContent();
    }
}
