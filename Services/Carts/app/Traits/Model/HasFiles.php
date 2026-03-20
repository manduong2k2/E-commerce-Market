<?php

namespace App\Traits\Model;

use App\Models\File;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Storage;

trait HasFiles
{
    public function files()
    {
        return $this->morphMany(File::class, 'model');
    }

    public function uploadFile(UploadedFile $file, array $extra = []): File
    {
        $originalName = $file->getClientOriginalName();
        $generatedName = now()->timestamp . '_' . $originalName;

        $path = $file->storeAs('attachments', $generatedName, 'public');

        return $this->files()->create([
            'filename' => $originalName,
            'path' => $path,
            'mime_type' => $file->getClientMimeType(),
            'size' => $file->getSize(),
        ] + $extra);
    }

    public function getFiles(): array
    {
        return $this->files->map(function ($file) {
            return [
                'id' => $file->id,
                'filename' => $file->filename,
                'url' => Storage::disk('public')->url($file->path),
                'mime_type' => $file->mime_type,
                'size' => $file->size,
            ];
        })->toArray();
    }

    /**
     * Delete an attachment, including the physical file.
     */
    public function deleteAttachment(string $attachmentId, bool $deleteFile = true): bool
    {
        $attachment = $this->files()->findOrFail($attachmentId);

        if ($deleteFile && Storage::disk('public')->exists($attachment->path)) {
            Storage::disk('public')->delete($attachment->path);
        }

        return $attachment->delete();
    }

    /**
     * Download an attachment file.
     */
    public function downloadAttachment(string $attachmentId)
    {
        $attachment = $this->files()->findOrFail($attachmentId);

        if (Storage::disk('public')->exists($attachment->path)) {
            return Storage::disk('public')->download($attachment->path, $attachment->filename);
        }

        throw new \Exception('File not found');
    }
}