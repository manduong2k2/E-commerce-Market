<?php

namespace App\Http\Repositories;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

interface OrderRepositoryInterface
{
    public function all(array $relations = [], array $conditions = []): Collection;
    
    public function find($id): ?Model;
    
    public function create(array $data): Model;
    
    public function update($id, array $data): Model;
    
    public function delete($id): bool;
}
