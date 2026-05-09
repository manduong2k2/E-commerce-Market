<?php

namespace App\Traits\Model;

use App\Facades\HttpClientInterface;
use Illuminate\Http\UploadedFile;

trait HasFiles
{
    protected string $storageApiUrl;    

    public function __construct()
    {
        $this->storageApiUrl = config('gateway.base_url') . '/' . config('services.service-list.storage') . '/api/files';
        parent::__construct();
    }

    protected function getHttpClient(): HttpClientInterface
    {
        return app(HttpClientInterface::class);
    }

    public function uploadFile(UploadedFile $file): array
    {
        $entityType = strtolower(class_basename($this));
        $response = $this->getHttpClient()->post(
            $this->storageApiUrl,
            [
                'multipart' => [
                    [
                        'name' => 'file',
                        'contents' => fopen($file->getPathname(), 'r'),
                        'filename' => $file->getClientOriginalName(),
                    ],
                    [
                        'name' => 'suffix',
                        'contents' => $entityType,
                    ],
                    [
                        'name' => 'entityType',
                        'contents' => $entityType,
                    ],
                    [
                        'name' => 'entityId',
                        'contents' => $this->id,
                    ],
                ],
            ]
        );

        return [
            'url' => $response['path']
        ];
    }

    public function getFiles(): array
    {
        $entityType = strtolower(class_basename($this));
        $entityId = $this->id;

        $response = $this->getHttpClient()->get($this->storageApiUrl, [
            'entityType' => $entityType,
            'entityId' => $entityId,
        ]);

        return $response;
    }

    /**
     * Delete an attachment from remote storage.
     */
    public function deleteAttachment(string $fileUrl): bool
    {
        $this->deleteRemoteFile($fileUrl);
        return true;
    }

    /**
     * Download an attachment file.
     */
    public function downloadAttachment(string $fileUrl)
    {
        $response = $this->getHttpClient()->get($this->getFileUrl($fileUrl));

        if ($response->getStatusCode() !== 200) {
            throw new \Exception('File not found');
        }

        return $response->getBody()->getContents();
    }

    /**
     * Get file URL from storage API.
     */
    protected function getFileUrl(string $path): string
    {
        // Extract the file identifier from the path if needed
        // Assuming path contains the full URL or identifier
        if (str_starts_with($path, 'http')) {
            return $path;
        }

        return $this->storageApiUrl . '/' . ltrim($path, '/');
    }

    /**
     * Delete file from remote storage.
     */
    protected function deleteRemoteFile(string $path): void
    {
        $url = $this->getFileUrl($path);

        $response = $this->getHttpClient()->delete($this->storageApiUrl, [
            'query' => [
                'url' => urlencode($path),
            ],
        ]);

        if ($response->getStatusCode() !== 200) {
            // Log error but don't throw exception to avoid breaking database deletion
            \Log::error('Failed to delete remote file: ' . $path);
        }
    }
}
