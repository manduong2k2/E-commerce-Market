<?php

namespace App\Exceptions;

use App\Constants\ResponseMessage;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\HttpExceptionInterface;
use Throwable;

class GlobalExceptionHandler
{
    public function report(Throwable $e): void {}

    public function render(Throwable $e, $request)
    {
        if (!$request->expectsJson()) {
            return null;
        }

        if ($e instanceof ValidationException) {
            return null;
        }

        $appFrames = collect($e->getTrace())
            ->filter(
                fn($frame) =>
                isset($frame['file']) &&
                    str_contains($frame['file'], DIRECTORY_SEPARATOR . 'app' . DIRECTORY_SEPARATOR)
            )
            ->values();

        $statusCode = 500;

        if ($e instanceof HttpExceptionInterface) {
            $statusCode = $e->getStatusCode();
        }

        if ($statusCode === 401) {
            return response()->json([
                'message'   => ResponseMessage::UNAUTHENTICATED
            ], $statusCode);
        }

        if (!app()->environment('production')) {
            return response()->json([
                'success' => false,
                'message' => $e->getMessage(),
                'trace'   => $appFrames->map(fn($f) => [
                    'file' => $f['file'],
                    'line' => $f['line'] ?? null,
                ]),
            ], $statusCode);
        }

        Log::error('Exception class: ' . get_class($e));
        Log::error('Message: ' . $e->getMessage());

        foreach ($appFrames as $index => $frame) {
            Log::error(sprintf(
                'App Trace #%d | %s:%s',
                $index,
                $frame['file'],
                $frame['line'] ?? 'N/A'
            ));
        }

        return response()->json([
            'message' => 'Server Error',
        ], 500);
    }
}
