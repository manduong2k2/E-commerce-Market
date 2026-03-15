import { Module } from "@nestjs/common";
import { EndpointController } from "../controllers/endpoint.controller";
import { EndpointRegistryService } from "../services/endpoint-registry.service";
import { EndpointRepository } from "src/domain/repositories/endpoint.repository";
import { TypeOrmModule } from "@nestjs/typeorm";
import { EndpointEntity } from "src/domain/entities/endpoint.entity";

@Module({
    imports: [
        TypeOrmModule.forFeature([EndpointEntity]),
    ],
    controllers: [EndpointController],
    providers: [EndpointRegistryService, EndpointRepository],
    exports: [EndpointRegistryService, EndpointRepository],
})
export class EndpointModule { }