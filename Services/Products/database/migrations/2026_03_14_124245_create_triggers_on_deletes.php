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
        DB::unprepared('
            CREATE OR REPLACE FUNCTION delete_extra_attributes_product_variant()
                RETURNS TRIGGER AS $$
                    BEGIN
                        DELETE FROM extra_attributes
                        WHERE entity_id = OLD.id;
                        RETURN OLD;
                    END;
                $$ LANGUAGE plpgsql;
            ');

            DB::unprepared('
            CREATE TRIGGER trigger_delete_extra_attributes_product_variant
                AFTER DELETE ON product_variants
                FOR EACH ROW
            EXECUTE FUNCTION delete_extra_attributes_product_variant();
        ');
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        DB::unprepared("
            DROP TRIGGER IF EXISTS trigger_delete_extra_attributes_product_variant 
            ON product_variants;
        ");
    
        DB::unprepared("
            DROP FUNCTION IF EXISTS delete_extra_attributes_product_variant();
        ");
    }
};
