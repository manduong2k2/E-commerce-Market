<?php

namespace App\Policies;

use App\Constants\ProductStatus;
use App\Models\Product;
use App\Models\ThirdParty\User;

class ProductProlicy
{
    public function list(User $context): bool
    {
        return true;
    }

    public function create(User $context): bool
    {
        return true;
    }

    public function details(User $context, Product $product): bool
    {
        return $product->created_by == $context->getAuthIdentifier() 
            || $product->status->name == ProductStatus::PUBLISHED;
    }

    public function update(User $context, Product $product): bool
    {
        return $product->created_by == $context->getAuthIdentifier();
    }

    public function delete(User $context, Product $product): bool
    {
        return $product->created_by == $context->getAuthIdentifier();
    }
}
