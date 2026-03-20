<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;

class CategorySeeder extends Seeder
{
    public function run(): void
    {
        $electronicsId = Str::uuid();
        $homeAppliancesId = Str::uuid();

        $categories = [
            [
                'id' => $electronicsId,
                'parent_id' => null,
                'name' => 'Electronics',
                'description' => 'All electronic products',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'id' => Str::uuid(),
                'parent_id' => $electronicsId,
                'name' => 'Laptops',
                'description' => 'Portable computers',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'id' => Str::uuid(),
                'parent_id' => $electronicsId,
                'name' => 'Smartphones',
                'description' => 'Mobile phones and accessories',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'id' => $homeAppliancesId,
                'parent_id' => null,
                'name' => 'Home Appliances',
                'description' => 'Appliances for home',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'id' => Str::uuid(),
                'parent_id' => $homeAppliancesId,
                'name' => 'Kitchen',
                'description' => 'Kitchen appliances',
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ];

        DB::table('categories')->insert($categories);
    }
}
