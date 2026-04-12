<?php

namespace App\Http\Services;

use Illuminate\Http\UploadedFile;

interface FileServiceInterface
{
    /**
     * @param UploadedFile $file
     * @param string $path
     * @return array
     */
    public function upload(UploadedFile $file, string $path): array;

    /**
     * @param string|null $path
     * @return string|null
     */
    public function makeFullUrl(?string $path): ?string;

    /**
     * @param string|null $path
     * @return string|null
     */
    public function getStoragePath(?string $path): ?string;

    /**
     * @param string $path
     * @return bool
     */
    public function delete(string $path): bool;
}