<?php

namespace App\Http\Services;

interface BrandServiceInterface
{
    public function getAllBrands(array $data);

    public function getBrandById($id);

    public function createBrand(array $data);

    public function updateBrand($id, array $data);

    public function deleteBrand($id);
}