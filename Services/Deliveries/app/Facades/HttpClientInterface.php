<?php

namespace App\Facades;

interface HttpClientInterface
{
    public function get(string $url, array $query = [], array $headers = [], array $cookies = []): array;

    public function post(string $url, array $data = [], array $headers = [], array $cookies = []): array;

    public function put(string $url, array $data = [], array $headers = [], array $cookies = []): array;

    public function delete(string $url, array $data = [], array $headers = [], array $cookies = []): array;
}