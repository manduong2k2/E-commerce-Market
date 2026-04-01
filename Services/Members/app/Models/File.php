<?php

namespace App\Models;

use App\Traits\Model\AuthorTracking;
use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\MorphTo;

class File extends Model
{
    use HasUuids, AuthorTracking;

    protected $fillable = [
        'filename',
        'path',
        'mime_type',
        'size',
        'created_by',
        'updated_by'
    ];

    public function model(): MorphTo
    {
        return $this->morphTo();
    }
}
