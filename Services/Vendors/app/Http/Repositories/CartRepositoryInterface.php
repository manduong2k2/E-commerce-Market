<?php

namespace App\Http\Repositories;

interface CartRepositoryInterface extends EloquentRepositoryInterface
{
    public function createOrUpdate(array $data);
}
