<?php

namespace App\Console\Commands\Core;

use Illuminate\Console\Command;
use Illuminate\Filesystem\Filesystem;

class MakeHandlerCommand extends Command
{
    protected $signature = 'make:handler {name}';
    protected $description = 'Create a queue handler and interface';

    protected Filesystem $files;

    public function __construct(Filesystem $files)
    {
        parent::__construct();
        $this->files = $files;
    }

    public function handle()
    {
        $name = $this->argument('name');
        $handlerClass = $name;
        $interfaceClass = $name . 'Interface';

        $basePath = app_path('Queues/Handlers');

        // Tạo folder nếu chưa tồn tại
        if (!$this->files->exists($basePath)) {
            $this->files->makeDirectory($basePath, 0755, true);
        }

        // 1️⃣ Tạo Interface
        $interfacePath = $basePath . '/' . $interfaceClass . '.php';
        if (!$this->files->exists($interfacePath)) {
            $interfaceContent = <<<PHP
                <?php

                namespace App\Queues\Handlers;

                interface {$interfaceClass} extends BaseHandlerInterface
                {
                }
                PHP;
            $this->files->put($interfacePath, $interfaceContent);
            $this->info("Created Interface: {$interfaceClass}");
        } else {
            $this->warn("Interface already exists: {$interfaceClass}");
        }

        // 2️⃣ Tạo Handler class
        $handlerPath = $basePath . '/' . $handlerClass . '.php';
        if (!$this->files->exists($handlerPath)) {
            $handlerContent = <<<PHP
                <?php

                namespace App\Queues\Handlers;

                use Illuminate\Support\Arr;
                use PhpAmqpLib\Message\AMQPMessage;

                class {$handlerClass} implements {$interfaceClass}
                {
                    public function execute(AMQPMessage \$msg): void
                    {
                        \$data = json_decode(\$msg->getBody(), true);
                    }
                }
                PHP;
            $this->files->put($handlerPath, $handlerContent);
            $this->info("Created Handler: {$handlerClass}");
        } else {
            $this->warn("Handler already exists: {$handlerClass}");
        }
    }
}