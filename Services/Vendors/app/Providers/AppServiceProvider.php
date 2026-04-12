<?php

namespace App\Providers;

use App\Facades\HttpClient;
use App\Facades\HttpClientInterface;
use App\Providers\Auth\ApiUserProvider;
use App\Supports\Scopes\BaseScope;
use Illuminate\Support\ServiceProvider;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Query\Builder as QueryBuilder;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Str;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        $this->registerServices();
        $this->registerRepositories();
        $this->app->singleton(HttpClientInterface::class, HttpClient::class);
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(): void
    {
        Auth::provider('api_user_provider', function ($app, array $config) {
            return new ApiUserProvider();
        });

        Builder::macro('addScope', function (BaseScope $scope): Builder {
            /** @var Builder $query */
            $query = $this;

            $scope->apply($query, $query->getModel());

            return $query;
        });

        QueryBuilder::macro('addScope', function (BaseScope $scope): QueryBuilder {
            /** @var QueryBuilder $query */
            $query = $this;

            $scope->applyQueryBuilder($query);

            return $query;
        });
    }

    protected function registerServices(): void
    {
        $servicePath = app_path('Http/Services');
        $namespace = 'App\\Http\\Services\\';

        foreach (scandir($servicePath) as $file) {
            if (!Str::endsWith($file, 'Service.php')) {
                continue;
            }

            $serviceClass = $namespace . pathinfo($file, PATHINFO_FILENAME);
            $interfaceClass = $namespace . pathinfo($file, PATHINFO_FILENAME) . 'Interface';

            if (interface_exists($interfaceClass) && class_exists($serviceClass)) {
                $this->app->bind($interfaceClass, $serviceClass);
            }
        }
    }

    protected function registerRepositories(): void
    {
        $repositoryPath = app_path('Http/Repositories');
        $namespace = 'App\\Http\\Repositories\\';

        foreach (scandir($repositoryPath) as $file) {
            if (!Str::endsWith($file, 'Repository.php')) {
                continue;
            }

            $repositoryClass = $namespace . pathinfo($file, PATHINFO_FILENAME);
            $interfaceClass = $namespace . pathinfo($file, PATHINFO_FILENAME) . 'Interface';

            if (interface_exists($interfaceClass) && class_exists($repositoryClass)) {
                $this->app->bind($interfaceClass, $repositoryClass);
            }
        }
    }
}
