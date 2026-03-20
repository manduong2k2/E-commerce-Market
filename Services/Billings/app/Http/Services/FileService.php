<?php

namespace App\Http\Services;

use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Storage;

class FileService implements FileServiceInterface
{
    public function __construct(protected string $storageName = 'public')
    {
    }

    public function upload(UploadedFile $file, string $path): array
    {
        $path = $path . '/' . date('Y-m-d');
        $extension = $file->getClientOriginalExtension();
        $filename = pathinfo($file->getClientOriginalName(), PATHINFO_FILENAME);

        $filenameToStore = $path . '/' . $filename . '_' . time() . '.' . $extension;

        Storage::disk($this->storageName)->put($filenameToStore, file_get_contents($file->getRealPath()));

        return [
            'path'         => $filenameToStore,
            'originalName' => $filename,
            'ext'          => $extension,
        ];
    }

    public function makeFullUrl(?string $path): ?string
    {
        if (!$path) {
            return null;
        }

        return Storage::disk($this->storageName)->url($path);
    }

    public function getStoragePath(?string $path): ?string
    {
        if (!$path) {
            return null;
        }

        return Storage::disk($this->storageName)->path($path);
    }

    public function delete(string $path): bool
    {
        if (!Storage::disk($this->storageName)->exists($path)) {
            return false;
        }

        return Storage::disk($this->storageName)->delete($path);
    }
}
