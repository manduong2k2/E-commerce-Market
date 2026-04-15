<?php

namespace App\Console\Commands;

use App\Facades\HttpClientInterface;
use Illuminate\Console\Attributes\Description;
use Illuminate\Console\Attributes\Signature;
use Illuminate\Console\Command;

#[Signature('gateway:register')]
#[Description('Register to gateway')]
class GatewayRegisterCommand extends Command
{
    public function __construct(
        protected HttpClientInterface $httpClient
    ) {
        parent::__construct();
    }

    /**
     * Execute the console command.
     */
    public function handle()
    {
        $this->info('Registering to gateway...');

        try {
            $response = $this->httpClient->get(config('gateway.admin_url') . '/services/' . config('app.name'));
        } catch (\Exception $e) {
            $this->httpClient->post(config('gateway.admin_url') . '/services', [
                'name' => config('app.name'),
                'url' => config('app.url'),
            ]);
        }

        $this->httpClient->put(config('gateway.admin_url') . '/services/' . config('app.name'), [
            'name' => config('app.name'),
            'url' => config('app.url'),
        ]);

        $this->info('Successfully registered to gateway!');
    }
}
