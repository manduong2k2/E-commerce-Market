import { Endpoint } from '../domain/models/endpoint';

export interface IEndpointRepository {
  save(endpoint: Endpoint): Promise<void>;

  findById(id: string): Promise<Endpoint | null>;

  findByCode(code: string): Promise<Endpoint | null>;

  findByServiceId(serviceId: string): Promise<Endpoint[]>;

  existsByCode(code: string): Promise<boolean>;

  findByMethodAndPath(
    method: string,
    path: string,
  ): Promise<Endpoint | null>;

  deleteById(id: string): Promise<void>;
}
