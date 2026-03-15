<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('product_statuses', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->string('name')->unique();
            $table->text('description')->nullable();
        });

        Schema::create('products', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->string('name');
            $table->string('code')->unique();
            $table->text('description')->nullable();
            $table->foreignUuid('brand_id')->index()->constrained('brands')->cascadeOnDelete();
            $table->integer('stock')->default(0);
            $table->foreignUuid('status_id')->index()->constrained('product_statuses')->cascadeOnDelete();
            
            $table->uuid('created_by');
            $table->uuid('updated_by');
            $table->softDeletes();
            $table->timestamps();

            $table->index(['name', 'deleted_at']);
        });

        DB::statement("
            CREATE INDEX products_brand_idx
            ON products (brand_id)
            WHERE deleted_at IS NULL
        ");

        DB::statement("
            CREATE INDEX products_name_idx
            ON products (name)
            WHERE deleted_at IS NULL
        ");

        Schema::create('product_variants', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->foreignUuid('product_id')->constrained('products')->cascadeOnDelete();
            $table->string('code')->unique();
            $table->string('name')->unique();
            $table->text('description')->nullable();
            $table->decimal('price', 10, 2)->default(0);

            
            $table->uuid('created_by');
            $table->uuid('updated_by');
            $table->timestamps();
            $table->softDeletes();
        });

        DB::statement("
            CREATE INDEX product_variants_name_idx
            ON product_variants (name)
            WHERE deleted_at IS NULL
        ");

        DB::statement("
            CREATE INDEX product_variants_code_idx
            ON product_variants (code)
            WHERE deleted_at IS NULL
        ");

        DB::statement("
            CREATE INDEX product_variants_product_id_idx
            ON product_variants (product_id)
            WHERE deleted_at IS NULL
        ");

        Schema::create('categories', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->uuid('parent_id')->nullable()->constrained('categories')->cascadeOnDelete();
            $table->string('name')->unique();
            $table->text('description')->nullable();

            $table->uuid('created_by')->nullable();
            $table->uuid('updated_by')->nullable();
            $table->timestamps();
            $table->softDeletes();
        });

        DB::unprepared("
            CREATE OR REPLACE FUNCTION check_category_cycle()
            RETURNS TRIGGER AS \$\$
            DECLARE
                current_parent UUID;
            BEGIN
                IF NEW.parent_id IS NOT NULL AND NEW.parent_id = NEW.id THEN
                    RAISE EXCEPTION 'Cannot set parent_id = id (self-reference)';
                END IF;
        
                current_parent := NEW.parent_id;
                WHILE current_parent IS NOT NULL LOOP
                    IF current_parent = NEW.id THEN
                        RAISE EXCEPTION 'Circular reference detected';
                    END IF;
                    SELECT parent_id INTO current_parent FROM categories WHERE id = current_parent;
                END LOOP;
        
                RETURN NEW;
            END;
            \$\$ LANGUAGE plpgsql;
        ");
        
        DB::unprepared("
            CREATE TRIGGER categories_before_insert
            BEFORE INSERT ON categories
            FOR EACH ROW
            EXECUTE FUNCTION check_category_cycle();
        ");
        
        DB::unprepared("
            CREATE TRIGGER categories_before_update
            BEFORE UPDATE ON categories
            FOR EACH ROW
            EXECUTE FUNCTION check_category_cycle();
        ");

        Schema::create('category_product', function (Blueprint $table) {
            $table->foreignUuid('product_id')->constrained('products')->cascadeOnDelete();
            $table->foreignUuid('category_id')->constrained('categories')->cascadeOnDelete();
            $table->primary(['product_id', 'category_id']);

            $table->timestamps();
            $table->unique(['product_id','category_id']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('category_product');
        Schema::dropIfExists('product_variants');
        Schema::dropIfExists('products');
        Schema::dropIfExists('categories');
    }
};
