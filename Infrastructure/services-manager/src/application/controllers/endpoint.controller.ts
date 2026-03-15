import {
  Controller,
  Post,
  Get,
  Patch,
  Delete,
  Param,
  Body,
} from '@nestjs/common';
import { EndpointRegistryService } from '../services/endpoint-registry.service';

@Controller('endpoints')
export class EndpointController {
  constructor(
    private readonly endpointRegistry: EndpointRegistryService,
  ) {}

  /* ===== CREATE ===== */

  @Post()
  async create(@Body() body: {
    serviceId: string;
    code: string;
    method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
    path: string;
    version: string;
    isPublic?: boolean;
  }) {
    return this.endpointRegistry.create(body);
  }

  /* ===== READ ===== */

  @Get(':id')
  async findById(@Param('id') id: string) {
    return this.endpointRegistry.findById(id);
  }

  @Get('/service/:serviceId')
  async findByService(@Param('serviceId') serviceId: string) {
    return this.endpointRegistry.findByService(serviceId);
  }

  /* ===== UPDATE ===== */

  @Patch(':id/route')
  async updateRoute(
    @Param('id') id: string,
    @Body() body: {
      method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
      path: string;
    },
  ) {
    await this.endpointRegistry.updateRoute(
      id,
      body.method,
      body.path,
    );
    return { success: true };
  }

  @Patch(':id/public')
  async makePublic(@Param('id') id: string) {
    await this.endpointRegistry.makePublic(id);
    return { success: true };
  }

  @Patch(':id/disable')
  async disable(@Param('id') id: string) {
    await this.endpointRegistry.disable(id);
    return { success: true };
  }

  /* ===== DELETE ===== */

  @Delete(':id')
  async delete(@Param('id') id: string) {
    await this.endpointRegistry.delete(id);
    return { success: true };
  }
}
