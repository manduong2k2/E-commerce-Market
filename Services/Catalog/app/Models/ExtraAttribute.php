<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Concerns\HasUuids;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\MorphTo;

class ExtraAttribute extends Model
{
    use HasUuids;

    protected $fillable = [
        'key',
        'value'
    ];

    public function model(): MorphTo
    {
        return $this->morphTo();
    }
}
