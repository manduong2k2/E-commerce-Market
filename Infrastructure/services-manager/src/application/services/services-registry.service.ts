import { ServiceRepository } from 'src/domain/repositories/service.repository';
import { Service } from '../../domain/models/service';
import { randomUUID } from 'crypto';
import { Injectable } from '@nestjs/common';

@Injectable()
export class ServiceRegistryService {
  constructor(
    private readonly serviceRepository: ServiceRepository,
  ) {}

  /* ========= CREATE ========= */

  async create(params: {
    name: string;
    version: string;
    protocol: 'HTTP' | 'GRPC';
  }): Promise<Service> {
    const exists = await this.serviceRepository.existsByName(params.name);
    if (exists) {
      throw new Error(`Service ${params.name} already exists`);
    }

    const service = new Service({
      id: randomUUID(),
      name: params.name,
      version: params.version,
      protocol: params.protocol,
    });

    await this.serviceRepository.save(service);
    return service;
  }

  /* ========= READ ========= */

  async findById(id: string): Promise<Service | null> {
    return this.serviceRepository.findById(id);
  }

  async findByName(name: string): Promise<Service | null> {
    return this.serviceRepository.findByName(name);
  }

  async findAll(): Promise<Service[]> {
    return this.serviceRepository.findAll();
  }

  /* ========= UPDATE ========= */

  async updateVersion(
    id: string,
    version: string,
  ): Promise<void> {
    const service = await this.serviceRepository.findById(id);
    if (!service) {
      throw new Error('Service not found');
    }

    service.updateVersion(version);
    await this.serviceRepository.save(service);
  }

  async disable(id: string): Promise<void> {
    const service = await this.serviceRepository.findById(id);
    if (!service) {
      throw new Error('Service not found');
    }

    service.disable();
    await this.serviceRepository.save(service);
  }

  async activate(id: string): Promise<void> {
    const service = await this.serviceRepository.findById(id);
    if (!service) {
      throw new Error('Service not found');
    }

    service.activate();
    await this.serviceRepository.save(service);
  }

  /* ========= DELETE ========= */

  async delete(id: string): Promise<void> {
    await this.serviceRepository.deleteById(id);
  }
}
