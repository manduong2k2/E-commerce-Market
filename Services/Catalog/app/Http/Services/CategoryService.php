<?php

namespace App\Http\Services;

use App\Http\Repositories\CategoryRepositoryInterface;

class CategoryService implements CategoryServiceInterface
{
    public function __construct(protected CategoryRepositoryInterface $CategoryRepository, protected FileServiceInterface $fileService)
    {
    }

    public function getAllCategories()
    {
        return $this->CategoryRepository->all();
    }

    public function getCategoryById($id)
    {
        return $this->CategoryRepository->find($id);
    }

    public function createCategory(array $data)
    {
        if (isset($data['image'])) {
            $data['image'] = $this->fileService->upload($data['image'], 'categories')['path'];
        }
        
        return $this->CategoryRepository->create($data);
    }

    public function updateCategory($id, array $data)
    {
        return $this->CategoryRepository->update($id, $data);
    }

    public function deleteCategory($id)
    {
        return $this->CategoryRepository->delete($id);
    }
}