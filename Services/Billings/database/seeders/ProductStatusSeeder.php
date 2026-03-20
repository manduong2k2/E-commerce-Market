<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;

class ProductStatusSeeder extends Seeder
{
    public function run(): void
    {
        $statuses = [
            [
                'id' => Str::uuid(),
                'name' => 'PUBLISHED',
                'description' => 'Product is visible to customers'
            ],
            [
                'id' => Str::uuid(),
                'name' => 'HIDDEN',
                'description' => 'Product is hidden from storefront'
            ],
            [
                'id' => Str::uuid(),
                'name' => 'DRAFT',
                'description' => 'Product is still being prepared'
            ],
            [
                'id' => Str::uuid(),
                'name' => 'ARCHIVED',
                'description' => 'Product is no longer sold'
            ],
        ];

        DB::table('product_statuses')->insert($statuses);
    }
}
