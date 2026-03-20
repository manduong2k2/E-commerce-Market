<?php
namespace App\Traits\Model;

use App\Models\User;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

trait AuthorTracking
{
    public function createdBy()
    {
        
    }

    public function updatedBy()
    {
    
    }

    public static function bootAuthorTracking()
    {
        static::creating(function ($model) {
            if (user()) {
                $model->created_by = user()->getAuthIdentifier();
                $model->updated_by = user()->getAuthIdentifier();
            }
        });

        static::updating(function ($model) {
            if (user()) {
                $model->updated_by = user()->getAuthIdentifier();
            }
        });
    }
}
