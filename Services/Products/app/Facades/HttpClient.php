<?php

namespace App\Facades;

use Illuminate\Http\Client\Response;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Arr;

class HttpClient implements HttpClientInterface
{
    protected array $defaultHeaders = [
        'accept' => 'application/json',
        'content-type' => 'application/json',
    ];

    /**
     * Handle the response and throw exception if failed
     */
    protected function handle(Response $response): array
    {
        if ($response->failed()) {
            throw new \Exception(
                $response->body(),
                $response->status()
            );
        }

        return $response->json();
    }

    /**
     * Merge default headers with custom headers
     * @param array $headers
     * @return array
     */
    protected function mergeHeaders(array $headers = []): array
    {
        return array_merge(
            $this->defaultHeaders,  // default trước
            $headers  // caller override sau
        );
    }

    /**
     * Attach cookies from ContextHolder
     */
    protected function attachCookies(array $headers = [], array $cookies = []): array
    {
        $cookieParts = [];

        if (!empty($cookies)) {
            foreach ($cookies as $k => $v) {
                $cookieParts[] = "{$k}={$v}";
            }
        }

        $user = user();
        if ($user && $user->getToken()) {
            $cookieParts[] = "ACCESS_TOKEN={$user->getToken()}";
        }

        if (!empty($cookieParts)) {
            $existing = Arr::get($headers, 'Cookie');
            $headers['Cookie'] = $existing
                ? $existing . '; ' . implode('; ', $cookieParts)
                : implode('; ', $cookieParts);
        }

        return $headers;
    }

    public function get(string $url, array $query = [], array $headers = [], array $cookies = []): array
    {
        $headers = $this->mergeHeaders($headers);
        $headers = $this->attachCookies($headers, $cookies);

        $response = Http::withHeaders($headers)
            ->timeout(300)
            ->retry(0)
            ->get($url, $query);

        return $this->handle($response);
    }

    public function post(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        $headers = $this->mergeHeaders($headers);
        $headers = $this->attachCookies($headers, $cookies);

        $response = Http::withHeaders($headers)
            ->timeout(300)
            ->retry(0)
            ->post($url, $data);

        return $this->handle($response);
    }

    public function put(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        $headers = $this->mergeHeaders($headers);
        $headers = $this->attachCookies($headers, $cookies);

        $response = Http::withHeaders($headers)
            ->timeout(300)
            ->retry(0)
            ->put($url, $data);

        return $this->handle($response);
    }

    public function delete(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        $headers = $this->mergeHeaders($headers);
        $headers = $this->attachCookies($headers, $cookies);

        $response = Http::withHeaders($headers)
            ->timeout(300)
            ->retry(0)
            ->delete($url, $data);

        return $this->handle($response);
    }
}
