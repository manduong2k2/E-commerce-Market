<?php

namespace App\Traits;

use Illuminate\Http\JsonResponse;

/**
 * Trait ApiResponse.
 */
trait ApiResponse
{
    /**
     * @param mixed $data
     * @param mixed|null $message
     * @param int $code
     * @param array $headers
     * @return JsonResponse
     */
    public function success(
        mixed $data = [],
        mixed $message = null,
        int   $code = 200,
        array $headers = [],
    ): JsonResponse
    {
        return $this->processReturn(true, $data, $message, $code, $headers);
    }

    /**
     * @param mixed|null $message
     * @param int $code
     * @param array $headers
     * @return JsonResponse
     */
    public function error(mixed $message = null, int $code = 400, array $headers = []): JsonResponse
    {
        if (empty($message)) {
            $message = error_get_last()['message'] ?? 'Error';
        }

        return $this->processReturn(false, [], $message, $code, $headers);
    }

    /**
     * @param bool $isSuccess
     * @param mixed $data
     * @param mixed|null $message
     * @param int $code
     * @param array $headers
     * @return JsonResponse
     */
    private function processReturn(
        bool  $isSuccess,
        mixed $data,
        mixed $message = null,
        int   $code = 200,
        array $headers = [],
    ): JsonResponse
    {
        $response = [
            'success' => $isSuccess,
            'data'    => $data,
        ];

        if ($message) {
            $response['message'] = $message;
        }

        return response()->json($response, $code, $headers);
    }
}
