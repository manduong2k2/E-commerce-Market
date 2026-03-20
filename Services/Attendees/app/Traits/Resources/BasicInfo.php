<?php

declare(strict_types=1);

namespace App\Traits\Resources;

use App\Http\Resources\User\UserShortResource;

trait BasicInfo
{
    protected function basicInfo(): array
    {
        return [
            'createdAt' => $this->created_at,
            'updatedAt' => $this->updated_at,
            'createdBy' => $this->createdBy ? new UserShortResource($this->createdBy) : null,
            'updatedBy' => $this->updatedBy ? new UserShortResource($this->updatedBy) : null,
        ];
    }
}
