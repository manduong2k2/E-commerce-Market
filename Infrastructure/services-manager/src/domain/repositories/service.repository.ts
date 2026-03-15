import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { IServiceRepository } from '../../interface/service-repository.interface';
import { Service } from '../models/service';
import { ServiceEntity } from '../entities/service.entity';
import { Injectable } from '@nestjs/common';
import { Endpoint } from '../models/endpoint';

@Injectable()
export class ServiceRepository implements IServiceRepository {
  constructor(
    @InjectRepository(ServiceEntity)
    private readonly repo: Repository<ServiceEntity>,
  ) { }

  async save(service: Service): Promise<void> {
    const orm = this.toEntity(service);
    await this.repo.save(orm);
  }

  async findById(id: string): Promise<Service | null> {
    const orm = await this.repo.findOne({ where: { id } });
    return orm ? orm.toModel() : null;
  }

  async findByName(name: string): Promise<Service | null> {
    const orm = await this.repo.findOne({ where: { name } });
    return orm ? orm.toModel() : null;
  }

  async existsByName(name: string): Promise<boolean> {
    return this.repo.exist({ where: { name } });
  }

  async findAll(): Promise<Service[]> {
    const list = await this.repo.find({
      relations: {
        endpoints: true,
      },
    });
    return list.map((orm) => orm.toModel());
  }

  async deleteById(id: string): Promise<void> {
    await this.repo.delete(id);
  }

  /* ===== Mapping ===== */

  private toEntity(service: Service): ServiceEntity {
    const orm = new ServiceEntity();
    orm.id = service.getId();
    orm.name = service.getName();
    orm.version = service.getVersion();
    orm.protocol = service.getProtocol();
    orm.status = service.getStatus();
    return orm;
  }
}

