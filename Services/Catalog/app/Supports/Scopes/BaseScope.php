<?php

namespace App\Supports\Scopes;

use Exception;
use Illuminate\Database\Eloquent\Scope;
use Illuminate\Database\Query\Builder as QueryBuilder;

/**
 * Class BaseScope.
 */
abstract class BaseScope implements Scope
{
    /**
     * @param string $table
     * @param string $column
     *
     * @return string
     */
    protected function alias(string $table, string $column): string
    {
        return sprintf('%s.%s', $table, $column);
    }

    public function applyQueryBuilder(QueryBuilder $builder): void
    {
        throw new Exception('method applyQueryBuilder need to be override!');
    }

    public function likeOperator(): string
    {
        return config('database.default') == 'pgsql' ? 'ilike' : 'like';
    }
}
