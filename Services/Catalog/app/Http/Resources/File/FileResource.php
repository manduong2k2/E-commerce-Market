<?php

namespace App\Http\Resources\File;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Arr;

class FileResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $prefix = config('gateway.base_url') . '/' . config('services.service-list.storage') . '/api/files';
    
        return [
            'id'            => Arr::get($this->resource, 'id'),
            'name'          => Arr::get($this->resource, 'name'),
            'url'           => "{$prefix}/" . Arr::get($this->resource, 'suffix') . "/" . Arr::get($this->resource, 'name'),
        ];
    }
}
