import {
  Entity,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  PrimaryGeneratedColumn,
  OneToMany,
} from 'typeorm';
import { EndpointEntity } from './endpoint.entity';
import { Service } from '../models/service';

@Entity('services')
export class ServiceEntity {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ unique: true })
  name: string;

  @Column()
  version: string;

  @Column()
  protocol: string;

  @Column()
  status: string;

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date;

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date;

  @OneToMany(() => EndpointEntity, e => e.service)
  endpoints: EndpointEntity[];

  toModel(): Service {
    return new Service({
      id: this.id,
      name: this.name,
      version: this.version,
      protocol: this.protocol as any,
      status: this.status as any,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt,
      endpoints: this.endpoints ? this.endpoints.map((e) => e.toModel()) : [],
    });
  }
}
