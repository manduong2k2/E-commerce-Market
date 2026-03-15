import { Module } from "@nestjs/common";
import { ServiceController } from "../controllers/service.controller";
import { ServiceRegistryService } from "../services/services-registry.service";
import { ServiceRepository } from "src/domain/repositories/service.repository";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ServiceEntity } from "src/domain/entities/service.entity";

@Module({
    imports: [
        TypeOrmModule.forFeature([ServiceEntity]),
    ],
    controllers: [ServiceController],
    providers: [ServiceRegistryService, ServiceRepository],
    exports: [ServiceRegistryService, ServiceRepository],
})
export class ServiceModule { }