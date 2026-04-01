<?php

namespace App\Providers\Auth;

use App\Models\ThirdParty\User;
use Illuminate\Contracts\Auth\UserProvider;
use Illuminate\Contracts\Auth\Authenticatable;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;
use Tymon\JWTAuth\JWT;

class ApiUserProvider implements UserProvider
{
    public function retrieveById($identifier)
    {
        return null;
    }

    public function retrieveByCredentials(array $credentials)
    {
        return null;
    }

    public function validateCredentials(Authenticatable $user, array $credentials)
    {
        return false;
    }

    public function retrieveByToken($identifier, $token)
    {
        if (!$token) {
            return null;
        }

        try {
            $payload = JWTAuth::setToken($token)->getPayload();

            $user = new User([
                'id' => $payload->get('sub'),
                'roles' => $payload->get('roles', []),
                'token' => $token
            ]);

            return $user;
        } catch (JWTException $e) {
            throw $e;
        }
    }

    public function updateRememberToken(Authenticatable $user, $token)
    {
        // không dùng remember token
    }

    public function rehashPasswordIfRequired(Authenticatable $user, array $credentials, bool $force = false)
    {
        // không dùng password
    }
}