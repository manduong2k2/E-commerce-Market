import {
  Entity,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  Index,
  PrimaryGeneratedColumn,
  ManyToOne,
  JoinColumn,
} from 'typeorm';
import { ServiceEntity } from './service.entity';
import { Endpoint } from '../models/endpoint';

@Entity('endpoints')
@Index(['method', 'path'], { unique: true })
@Index(['code'], { unique: true })
export class EndpointEntity {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @ManyToOne(() => ServiceEntity, service => service.endpoints, {
    onDelete: 'CASCADE',
  })
  @JoinColumn({ name: 'service_id' })
  service: ServiceEntity;

  @Column({ name: 'service_id' })
  serviceId: string;

  @Column()
  code: string;

  @Column()
  method: string;

  @Column()
  path: string;

  @Column()
  version: string;

  @Column({ name: 'is_public', default: false })
  isPublic: boolean;

  @Column()
  status: string;

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date;

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date;

  toModel(): Endpoint {
    return new Endpoint({
      id: this.id,
      serviceId: this.serviceId,
      code: this.code,
      method: this.method,
      path: this.path,
      version: this.version,
      isPublic: this.isPublic,
      status: this.status as any,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt,
    });
  }
}
