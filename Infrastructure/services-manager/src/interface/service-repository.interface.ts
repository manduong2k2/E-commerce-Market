import { Service } from '../domain/models/service';

export interface IServiceRepository {
  save(service: Service): Promise<void>;

  findById(id: string): Promise<Service | null>;

  findByName(name: string): Promise<Service | null>;

  existsByName(name: string): Promise<boolean>;

  findAll(): Promise<Service[]>;

  deleteById(id: string): Promise<void>;
}
