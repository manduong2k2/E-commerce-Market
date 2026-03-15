import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { IEndpointRepository } from '../../interface/enpoint-repository.interface';
import { Endpoint } from '../models/endpoint';
import { EndpointEntity } from '../entities/endpoint.entity';
import { Injectable } from '@nestjs/common';

@Injectable()
export class EndpointRepository implements IEndpointRepository {
  constructor(
    @InjectRepository(EndpointEntity)
    private readonly repo: Repository<EndpointEntity>,
  ) {}

  async save(endpoint: Endpoint): Promise<void> {
    await this.repo.save(this.toEntity(endpoint));
  }

  async findById(id: string): Promise<Endpoint | null> {
    const orm = await this.repo.findOne({ where: { id } });
    return orm ? orm.toModel() : null;
  }

  async findByCode(code: string): Promise<Endpoint | null> {
    const orm = await this.repo.findOne({ where: { code } });
    return orm ? orm.toModel() : null;
  }

  async findByServiceId(serviceId: string): Promise<Endpoint[]> {
    const list = await this.repo.find({ where: { serviceId } });
    return list.map((orm) => orm.toModel());
  }

  async existsByCode(code: string): Promise<boolean> {
    const result = await this.repo.find({ where: { code } });
    return result !== null && result.length > 0;
  }

  async findByMethodAndPath(
    method: string,
    path: string,
  ): Promise<Endpoint | null> {
    const orm = await this.repo.findOne({ where: { method, path } });
    return orm ? orm.toModel() : null;
  }

  async deleteById(id: string): Promise<void> {
    await this.repo.delete(id);
  }

  /* ===== Mapping ===== */

  private toEntity(endpoint: Endpoint): EndpointEntity {
    const orm = new EndpointEntity();
    orm.id = endpoint.getId();
    orm.serviceId = endpoint.getServiceId();
    orm.code = endpoint.getCode();
    orm.method = endpoint.getMethod();
    orm.path = endpoint.getPath();
    orm.version = endpoint.getVersion();
    orm.isPublic = endpoint.isEndpointPublic();
    orm.status = endpoint.getStatus();
    return orm;
  }
}