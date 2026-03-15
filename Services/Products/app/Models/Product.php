<?php

namespace App\Models;

use App\Traits\Model\AuthorTracking;
use App\Traits\Model\HasFiles;
use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\SoftDeletes;

class Product extends Model
{
    use HasUuids, SoftDeletes, HasFiles, AuthorTracking;

    protected $fillable = [
        'name',
        'code',
        'description',
        'brand_id',
        'status_id',
        'created_by',
        'updated_by'
    ];

    public function brand(): BelongsTo
    {
        return $this->belongsTo(Brand::class);
    }

    public function categories(): BelongsToMany
    {
        return $this->belongsToMany(Category::class, 'category_product', 'product_id', 'category_id');
    }

    public function variants(): HasMany
    {
        return $this->hasMany(ProductVariant::class);
    }

    public function status(): BelongsTo
    {
        return $this->belongsTo(ProductStatus::class, 'status_id');
    }
}
