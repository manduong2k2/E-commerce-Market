import { Inject, Injectable } from '@nestjs/common';
import { Endpoint } from '../../domain/models/endpoint';
import { randomUUID } from 'crypto';
import { EndpointRepository } from 'src/domain/repositories/endpoint.repository';

@Injectable()
export class EndpointRegistryService {
  constructor(
    private readonly endpointRepository: EndpointRepository,
  ) {}

  /* ========= CREATE ========= */

  async create(params: {
    serviceId: string;
    code: string;
    method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
    path: string;
    version: string;
    isPublic?: boolean;
  }): Promise<Endpoint> {
    const exists = await this.endpointRepository.existsByCode(params.code);
    if (exists) {
      throw new Error(`Endpoint code ${params.code} already exists`);
    }

    const endpoint = new Endpoint({
      id: randomUUID(),
      serviceId: params.serviceId,
      code: params.code,
      method: params.method,
      path: params.path,
      version: params.version,
      isPublic: params.isPublic,
    });

    await this.endpointRepository.save(endpoint);
    return endpoint;
  }

  /* ========= READ ========= */

  async findById(id: string): Promise<Endpoint | null> {
    return this.endpointRepository.findById(id);
  }

  async findByCode(code: string): Promise<Endpoint | null> {
    return this.endpointRepository.findByCode(code);
  }

  async findByService(serviceId: string): Promise<Endpoint[]> {
    return this.endpointRepository.findByServiceId(serviceId);
  }

  /* ========= UPDATE ========= */

  async updateRoute(
    id: string,
    method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE',
    path: string,
  ): Promise<void> {
    const endpoint = await this.endpointRepository.findById(id);
    if (!endpoint) {
      throw new Error('Endpoint not found');
    }

    endpoint.updateRoute(method, path);
    await this.endpointRepository.save(endpoint);
  }

  async makePublic(id: string): Promise<void> {
    const endpoint = await this.endpointRepository.findById(id);
    if (!endpoint) {
      throw new Error('Endpoint not found');
    }

    endpoint.makePublic();
    await this.endpointRepository.save(endpoint);
  }

  async disable(id: string): Promise<void> {
    const endpoint = await this.endpointRepository.findById(id);
    if (!endpoint) {
      throw new Error('Endpoint not found');
    }

    endpoint.disable();
    await this.endpointRepository.save(endpoint);
  }

  /* ========= DELETE ========= */

  async delete(id: string): Promise<void> {
    await this.endpointRepository.deleteById(id);
  }
}
