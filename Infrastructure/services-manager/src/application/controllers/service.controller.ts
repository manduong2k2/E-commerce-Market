import {
  Controller,
  Post,
  Get,
  Patch,
  Delete,
  Param,
  Body,
} from '@nestjs/common';
import { ServiceRegistryService } from '../services/services-registry.service';

@Controller('services')
export class ServiceController {
  constructor(
    private readonly serviceRegistry: ServiceRegistryService,
  ) {}

  /* ===== CREATE ===== */

  @Post()
  async create(@Body() body: {
    name: string;
    version: string;
    protocol: 'HTTP' | 'GRPC';
  }) {
    return this.serviceRegistry.create(body);
  }

  /* ===== READ ===== */

  @Get()
  async findAll() {
    return this.serviceRegistry.findAll();
  }

  @Get(':id')
  async findById(@Param('id') id: string) {
    return this.serviceRegistry.findById(id);
  }

  /* ===== UPDATE ===== */

  @Patch(':id/version')
  async updateVersion(
    @Param('id') id: string,
    @Body() body: { version: string },
  ) {
    await this.serviceRegistry.updateVersion(id, body.version);
    return { success: true };
  }

  @Patch(':id/disable')
  async disable(@Param('id') id: string) {
    await this.serviceRegistry.disable(id);
    return { success: true };
  }

  @Patch(':id/activate')
  async activate(@Param('id') id: string) {
    await this.serviceRegistry.activate(id);
    return { success: true };
  }

  /* ===== DELETE ===== */

  @Delete(':id')
  async delete(@Param('id') id: string) {
    await this.serviceRegistry.delete(id);
    return { success: true };
  }
}
