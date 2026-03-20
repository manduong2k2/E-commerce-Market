<?php

namespace App\Models;

use App\Traits\Model\AuthorTracking;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\MorphMany;
use Illuminate\Database\Eloquent\SoftDeletes;

class ProductVariant extends Model
{
    use HasUuids, SoftDeletes, AuthorTracking;

    protected $fillable = [
        'product_id',
        'name',
        'code',
        'price',
        'description',
        'created_by',
        'updated_by'
    ];

    public function product(): BelongsTo
    {
        return $this->belongsTo(Product::class);
    }

    public function extraAttributes(): MorphMany
    {
        return $this->morphMany(ExtraAttribute::class, 'entity');
    }
}
