<?php

namespace App\Http\Repositories;

use App\Supports\Scopes\SearchScope;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\MorphMany;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Arr;
use Illuminate\Support\Str;

abstract class EloquentRepository implements EloquentRepositoryInterface
{
    protected $model;
    protected $scopes;

    public function __construct()
    {
        $this->setModel($this->getModel());
        $this->setScopes($this->getSearchScopes());
    }

    public abstract function getModel(): string;

    public function setModel($model)
    {
        $this->model = app()->make($model);
    }

    public abstract function getSearchScopes(): array;

    public function setScopes($scopes): void
    {
        $this->scopes = $scopes;
    }

    public function all(array $relations = [], array $conditions = []): Collection
    {
        $query = $this->baseQuery($relations);

        $filters = Arr::get($conditions, 'filters');

        $query->when($filters && is_array($filters), function ($subQuery) use ($filters) {
            foreach ($filters as $filter) {
                $key      = Arr::get($filter, 'key');
                $operator = Arr::get($filter, 'operator', '=');
                $value    = Arr::get($filter, 'value');
                $subQuery->when(
                    $key && $value !== null,
                    fn($q) => (in_array($operator, ['IN', 'NOT IN']) && is_array($value)) ?
                        $q->whereIn($key, $value) :
                        $q->where($key, $operator, $value)
                );
            }
        });

        $query->when(
            Arr::get($conditions, 'search'),
            fn(Builder $query, $search) => $this->applySearchFilter($query, $this->scopes, $search)
        );

        return $query->select($this->model->getTable().'.*')->get();
    }

    public function applySearchFilter(Builder $query, array $scopes, string $search): void
    {
        $allScopes = [];

        foreach ($scopes as $scope) {
            if (str_contains($scope, '.')) {
                [$relation, $column] = explode('.', $scope, 2);
                $allScopes[] = "$relation.$column";
            } else {
                $allScopes[] = $scope;
            }
        }

        if (!empty($allScopes)) {
            $searchScope = new SearchScope($search, $allScopes);

            foreach ($scopes as $scope) {
                if (str_contains($scope, '.')) {
                    [$relation, $column] = explode('.', $scope, 2);
                    $searchScope->setJoinedTable($this->model->$relation()->getRelated()->getTable())
                        ->setJoinedField($this->model->getForeignKey($relation))
                        ->setAliasJoinedTable($relation);
                }
            }

            $query->addScope($searchScope);
        }
    }

    public function baseQuery($relations = []): Builder
    {
        $query = $this->model::query()->with($relations);

        return $query;
    }

    public function find($id): ?Model
    {
        return $this->model::find($id);
    }

    public function create(array $data): Model
    {
        return DB::transaction(function () use ($data) {

            $fillable = $this->model->getFillable();

            $mappedData = collect($fillable)
                ->mapWithKeys(fn($field) => [$field => $data[Str::camel($field)] ?? null])
                ->toArray();

            $model = $this->model::create($mappedData);

            $data = Arr::except(
                $data,
                collect($fillable)->map(fn($field) => Str::camel($field))->toArray()
            );

            $this->createRelations($model, $data);

            return $model;
        });
    }

    protected function createRelations(Model $model, array $relations)
    {
        foreach ($relations as $relation => $relatedData) {

            if (!method_exists($model, $relation)) {
                continue;
            }

            $relationObj = $model->$relation();

            //BelongsToMany
            if ($relationObj instanceof BelongsToMany) {
                $relatedKey = Str::camel($relationObj->getRelatedKeyName());
                $ids = collect($relatedData)
                    ->map(fn($item) => $item[$relatedKey] ?? null)
                    ->filter()
                    ->toArray();
                $relationObj->sync($ids);
                continue;
            }

            // HasMany or MorphMany
            if ($relationObj instanceof HasMany || $relationObj instanceof MorphMany) {

                foreach ($relatedData as $item) {

                    $nested = [];

                    if ($item instanceof UploadedFile) {
                        $model->uploadFile($item);
                        continue;
                    }

                    foreach ($item as $key => $value) {
                        if (is_array($value) && method_exists($relationObj->getRelated(), $key)) {
                            $nested[$key] = $value;
                            unset($item[$key]);
                        }
                    }

                    $attributes = collect($item)
                        ->mapWithKeys(fn($value, $key) => [Str::snake($key) => $value])
                        ->toArray();

                    $fillable = $relationObj->getRelated()->getFillable();
                    $attributes = Arr::only($attributes, $fillable);

                    $child = $relationObj->create($attributes);

                    if (!empty($nested)) {
                        $this->createRelations($child, $nested);
                    }
                }

                continue;
            }

            $child = $relationObj->create($relatedData);
        }
    }

    public function update(Model $model, array $data): Model
    {
        return DB::transaction(function () use ($model, $data) {

            $fillable = $model->getFillable();

            $attributes = collect($fillable)
                ->mapWithKeys(fn($field) => [$field => $data[Str::camel($field)] ?? null])
                ->toArray();

            $model->update($attributes);

            $data = Arr::except(
                $data,
                collect($fillable)->map(fn($field) => Str::camel($field))->toArray()
            );

            $this->updateRelations($model, $data);

            return $model;
        });
    }

    protected function updateRelations(Model $model, array $relations)
    {
        foreach ($relations as $relation => $relatedData) {
            if (!method_exists($model, $relation)) {
                continue;
            }

            $relationObj = $model->$relation();
            $relatedModel = $relationObj->getRelated();

            if ($relationObj instanceof BelongsToMany) {
                $relatedKey = Str::camel($relationObj->getRelatedKeyName());
                $ids = collect($relatedData)
                    ->map(fn($item) => $item[$relatedKey] ?? null)
                    ->filter()
                    ->toArray();
                $relationObj->sync($ids);
                continue;
            }

            if ($relationObj instanceof HasMany || $relationObj instanceof MorphMany) {
                $incomingIds = collect($relatedData)
                    ->pluck('id')
                    ->filter()
                    ->toArray();

                $existingIds = $relationObj->pluck('id')->toArray();

                $toDelete = array_diff($existingIds, $incomingIds);

                if (!empty($toDelete)) {
                    $relationObj->whereIn('id', $toDelete)->delete();
                }

                foreach ($relatedData as $item) {

                    $nested = [];

                    if ($item instanceof UploadedFile) {
                        $model->uploadAttachment($item);
                        continue;
                    }

                    foreach ($item as $key => $value) {
                        if (is_array($value) && method_exists($relatedModel, $key)) {
                            $nested[$key] = $value;
                            unset($item[$key]);
                        }
                    }

                    $attributes = collect($item)
                        ->mapWithKeys(fn($value, $key) => [Str::snake($key) => $value])
                        ->toArray();
                    $fillable = $relatedModel->getFillable();
                    $attributes = Arr::only($attributes, $fillable);

                    if (isset($item['id']) && $child = $relationObj->getRelated()->find($item['id'])) {
                        $child->update($attributes);
                    } else {
                        $child = $relationObj->create($attributes);
                    }

                    if (!empty($nested)) {
                        $this->updateRelations($child, $nested);
                    }
                }

                continue;
            }

            $relationObj->update($relatedData);
        }
    }

    public function delete(Model $model): bool
    {
        return DB::transaction(fn() => $model->delete());
    }
}
