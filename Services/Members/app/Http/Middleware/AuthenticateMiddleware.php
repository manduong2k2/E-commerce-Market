<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;

class AuthenticateMiddleware
{
    public function handle(Request $request, Closure $next): Response
    {
        $token = $request->cookie('ACCESS_TOKEN');
        
        if (!$token) {
            return response()->json(['message' => 'Unauthorized'], 401);
        }

        $provider = Auth::createUserProvider('users');
        
        $user = $provider->retrieveByToken(null, $token);

        if (!$user) {
            return response()->json(['message' => 'Unauthorized'], 401);
        }

        Auth::guard('api')->setUser($user);

        return $next($request);
    }
}
