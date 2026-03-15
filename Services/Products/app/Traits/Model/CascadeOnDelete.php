<?php

namespace App\Traits\Model;


trait CascadeOnDelete
{
    public static function bootCascadeOnDelete()
    {
        static::deleting(function ($model) {
            if (property_exists($model, 'cascadeRelations') && is_array($model->cascadeRelations)) {
                foreach ($model->cascadeRelations as $relationName) {
                    try {
                        $relation = $model->{$relationName}();

                        if ($relation instanceof \Illuminate\Database\Eloquent\Relations\Relation) {
                            $relation->get()->each(function ($child) {
                                try {
                                    $child->delete();
                                } catch (\Throwable $e) {
                                    logger()->error("Failed to delete relation: " . $e->getMessage());
                                }
                            });
                        }
                    } catch (\Throwable $e) {
                        logger()->error("Failed to process relation {$relationName}: " . $e->getMessage());
                    }
                }
            }
        });
    }
}
