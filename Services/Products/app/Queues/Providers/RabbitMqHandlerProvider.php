<?php

namespace App\Queues\Providers;

use Illuminate\Support\ServiceProvider;
use Illuminate\Support\Str;

class RabbitMqHandlerProvider extends ServiceProvider
{
    public function register(): void
    {
        $this->registerHandlers();
    }

    public function boot(): void
    {
        //
    }

    protected function registerHandlers(): void
    {
        $handlerPath = app_path('Rabbit/Handler');
        $namespace = 'App\\Rabbit\\Handler\\';

        foreach (scandir($handlerPath) as $file) {
            if (!Str::endsWith($file, 'Handler.php')) {
                continue;
            }

            $handlerClass = $namespace . pathinfo($file, PATHINFO_FILENAME);
            $interfaceClass = $namespace . pathinfo($file, PATHINFO_FILENAME) . 'Interface';

            if (interface_exists($interfaceClass) && class_exists($handlerClass)) {
                $this->app->bind($interfaceClass, $handlerClass);
            }
        }
    }
}
