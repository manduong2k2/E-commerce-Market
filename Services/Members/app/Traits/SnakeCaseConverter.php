<?php

declare(strict_types=1);

namespace App\Traits;

use Illuminate\Support\Str;

trait SnakeCaseConverter
{
    public function skipKeys(): array
    {
        return [];
    }

    protected function convertKeysToSnakeCase(array $data): array
    {
        $result = [];

        foreach ($data as $key => $value) {
            if (!empty($this->skipKeys()) && in_array($key, $this->skipKeys(), true)) {
                $result[$key] = $value;
                continue;
            }

            $snakeKey = Str::snake($key);

            $result[$snakeKey] = is_array($value) ? $this->convertKeysToSnakeCase($value) : $value;
        }

        return $result;
    }

    public function validated($key = null, $default = null): array
    {
        return $this->convertKeysToSnakeCase(parent::validated($key, $default));
    }
}
