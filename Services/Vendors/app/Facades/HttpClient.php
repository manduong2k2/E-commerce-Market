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

    protected ?string $baseUrl = null;
    protected array $headers = [];
    protected array $cookies = [];

    // ========================
    // CORE PIPELINE
    // ========================

    protected function request(string $method, string $url, array $options = []): array
    {
        $headers = $this->mergeHeaders($options['headers'] ?? []);
        $headers = $this->attachCookies($headers, $options['cookies'] ?? []);

        $client = Http::withHeaders($headers)
            ->timeout(300)
            ->retry(0);

        if ($this->baseUrl) {
            $client = $client->baseUrl($this->baseUrl);
        }

        $response = match ($method) {
            'GET'    => $client->get($url, $options['query'] ?? []),
            'POST'   => $client->post($url, $options['data'] ?? []),
            'PUT'    => $client->put($url, $options['data'] ?? []),
            'DELETE' => $client->delete($url, $options['data'] ?? []),
        };

        return $this->handle($response);
    }

    // ========================
    // PUBLIC METHODS
    // ========================

    public function get(string $url, array $query = [], array $headers = [], array $cookies = []): array
    {
        return $this->request('GET', $url, compact('query', 'headers', 'cookies'));
    }

    public function post(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        return $this->request('POST', $url, compact('data', 'headers', 'cookies'));
    }

    public function put(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        return $this->request('PUT', $url, compact('data', 'headers', 'cookies'));
    }

    public function delete(string $url, array $data = [], array $headers = [], array $cookies = []): array
    {
        return $this->request('DELETE', $url, compact('data', 'headers', 'cookies'));
    }

    // ========================
    // EXTENSIONS (FLUENT)
    // ========================

    public function withBaseUrl(string $baseUrl): self
    {
        $clone = clone $this;
        $clone->baseUrl = $baseUrl;

        return $clone;
    }

    public function withHeaders(array $headers): self
    {
        $clone = clone $this;
        $clone->headers = array_merge($this->headers, $headers);

        return $clone;
    }

    public function withCookies(array $cookies): self
    {
        $clone = clone $this;
        $clone->cookies = array_merge($this->cookies, $cookies);

        return $clone;
    }

    public function withToken(string $token): self
    {
        return $this->withHeaders([
            'Authorization' => "Bearer {$token}"
        ]);
    }

    // ========================
    // INTERNAL HELPERS
    // ========================

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

    protected function mergeHeaders(array $headers = []): array
    {
        return array_merge(
            $this->defaultHeaders,
            $this->headers,   // extension headers
            $headers          // runtime override
        );
    }

    protected function attachCookies(array $headers = [], array $cookies = []): array
    {
        $cookieParts = [];

        $cookies = array_merge($this->cookies, $cookies);

        foreach ($cookies as $k => $v) {
            $cookieParts[] = "{$k}={$v}";
        }

        $user = null;

        try {
            $user = user();
        } catch (\Exception $e) {
            // User not authenticated, continue without token
        }

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
}
