<?php

use Illuminate\Auth\AuthenticationException;
use Illuminate\Auth\Access\AuthorizationException;
use App\Models\ThirdParty\User;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Arr;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\Auth;

if (!function_exists('user')) {
    function user(): User
    {
        $user = Auth::guard('api')->user();

        if (!$user instanceof User) {
            throw new AuthenticationException();
        }

        return $user;
    }
}

if (!function_exists('policy_authorize')) {
    /**
     * @param string $policyClass
     * @param string $policyMethod
     * @param mixed ...$params
     *
     * @throws AuthorizationException
     */
    function policy_authorize(string $policyClass, string $policyMethod, ...$params): void
    {
        if (policy_check($policyClass, $policyMethod, ...$params)) {
            return;
        }

        throw new AuthorizationException();
    }
}

if (!function_exists('policy_check')) {
    /**
     * @param string $policyClass
     * @param string $policyMethod
     * @param mixed ...$params
     *
     * @return bool
     */
    function policy_check(string $policyClass, string $policyMethod, ...$params): bool
    {
        $policy = app($policyClass);

        return $policy->{$policyMethod}(...$params);
    }
}

if (!function_exists('resolve_master_data')) {
    function resolve_master_data(string $name): string
    {
        return Arr::get((json_decode($name, true)), client_locale()) ?? $name;
    }
}

if (!function_exists('client_timezone')) {
    function client_timezone(): string
    {
        try {
            return request()
                ->headers
                ->get('X-Timezone')
                ?: Carbon::now()->timezoneName;
        } catch (\Throwable $exception) {
            return Carbon::now()->timezoneName;
        }
    }
}

if (!function_exists('client_locale')) {
    function client_locale(): string
    {
        try {
            $acceptedLocales = ['vi', 'en', 'ja'];
            return request()->getPreferredLanguage($acceptedLocales) ?? 'vi';
        } catch (\Throwable $exception) {
            throw $exception;
        }
    }
}

if (!function_exists('resolve_resource')) {
    function resolve_resource(string $resourceClass, $object): ?JsonResource
    {
        if (is_null($object)) {
            return null;
        }

        return new $resourceClass($object);
    }
}


// Check intervals time
if (!function_exists('validate_intervals')) {
    function validate_intervals(array $intervals, bool $allowTouch = true)
    {
        $sorted = collect($intervals)
            ->sortBy(fn($i) => strtotime($i['from']))
            ->values();

        for ($i = 0; $i < $sorted->count() - 1; $i++) {
            $current = $sorted[$i];
            $next = $sorted[$i + 1];

            if ($current['from'] >= $current['to']) {
                throw new \InvalidArgumentException("Invalid time interval: start time {$current['from']} must be before end time {$current['to']}");
            }

            if ($current['to'] > $next['from']) {
                throw new \InvalidArgumentException("Time intervals overlap: {$current['from']}-{$current['to']} overlaps with {$next['from']}-{$next['to']}");
            }

            if (!$allowTouch && $current['to'] === $next['from']) {
                throw new \InvalidArgumentException('Time intervals cannot touch: there must be a gap between intervals');
            }
        }

        return true;
    }
}
