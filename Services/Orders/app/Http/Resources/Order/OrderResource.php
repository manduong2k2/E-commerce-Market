<?php

namespace App\Http\Resources\Order;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class OrderResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'id'                => $this->id,
            'customerId'        => $this->customer_id,
            'status'            => $this->status,
            'total'             => $this->total,
            'notes'             => $this->notes,

        ];
    }
}
