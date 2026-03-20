<?php

namespace App\Models\ThirdParty;

use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Support\Arr;
use Tymon\JWTAuth\Contracts\JWTSubject;
use InvalidArgumentException;

class User implements Authenticatable, JWTSubject
{
    private string $id;
    private ?string $name;
    private ?string $email;
    private ?string $phone;
    private ?string $status;
    private ?string $createdAt;
    private array $roles;

    public function __construct(array $data)
    {
        $id = Arr::get($data, 'id');

        if (!$id) {
            throw new InvalidArgumentException('User id is required');
        }

        $this->id        = $id;
        $this->name      = Arr::get($data, 'name');
        $this->email     = Arr::get($data, 'email');
        $this->phone     = Arr::get($data, 'phone');
        $this->status    = Arr::get($data, 'status');
        $this->createdAt = Arr::get($data, 'createdAt');
        $this->roles     = Arr::get($data, 'roles') ??  [];
    }

    // -----------------------------
    // Authenticatable
    // -----------------------------

    public function getAuthIdentifierName()
    {
        return 'id';
    }

    public function getAuthIdentifier()
    {
        return $this->id;
    }

    public function getAuthPassword()
    {
        return null;
    }

    public function getAuthPasswordName()
    {
        return null;
    }

    public function getRememberToken()
    {
        return null;
    }

    public function setRememberToken($value)
    {
    }

    public function getRememberTokenName()
    {
        return null;
    }

    // -----------------------------
    // Getters
    // -----------------------------

    public function getId(): string
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function getPhone(): ?string
    {
        return $this->phone;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function getCreatedAt(): ?string
    {
        return $this->createdAt;
    }

    public function getRoles(): array
    {
        return $this->roles;
    }

    public function hasRole(string $role): bool
    {
        return in_array($role, $this->roles ?? []);
    }

    // -----------------------------
    // JWTSubject
    // -----------------------------

    public function getJWTIdentifier()
    {
        return $this->id;
    }

    public function getJWTCustomClaims()
    {
        return [];
    }
}