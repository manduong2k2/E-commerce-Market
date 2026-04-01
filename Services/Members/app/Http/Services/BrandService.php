<?php

namespace App\Http\Services;

use App\Http\Repositories\BrandRepository;

class BrandService implements BrandServiceInterface
{
    public function __construct(protected BrandRepository $brandRepository)
    {
    }

    public function getAllBrands(array $data)
    {
        return $this->brandRepository->all([], $data);
    }

    public function getBrandById($id)
    {
        return $this->brandRepository->find($id);
    }

    public function createBrand(array $data)
    {
        return $this->brandRepository->create($data);
    }

    public function updateBrand($id, array $data)
    {
        return $this->brandRepository->update($id, $data);
    }

    public function deleteBrand($id)
    {
        return $this->brandRepository->delete($id);
    }
}