<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Brand\IndexRequest;
use App\Http\Requests\Brand\StoreRequest;
use App\Http\Requests\Brand\UpdateRequest;
use App\Http\Resources\Brand\BrandResource;
use App\Http\Resources\Brand\BrandResourceCollection;
use App\Http\Services\BrandServiceInterface;
use App\Models\Brand;

class BrandController extends Controller
{
    public function __construct(protected BrandServiceInterface $brandService){}

    public function index(IndexRequest $request)
    {
        return new BrandResourceCollection($this->brandService->getAllBrands($request->validated()));
    }

    public function store(StoreRequest $request)
    {
        return new BrandResource($this->brandService->createBrand($request->validated()));
    }

    public function show(Brand $brand)
    {
        return new BrandResource($brand);
    }

    public function update(UpdateRequest $request, Brand $brand)
    {
        return new BrandResource($this->brandService->updateBrand($brand, $request->validated()));
    }

    public function destroy(Brand $brand)
    {
        $brand->delete();
        return response()->noContent();
    }
}
