import { Endpoint } from "./endpoint";

export type ServiceStatus =
    | 'ACTIVE'
    | 'DISABLED'
    | 'DEPRECATED';

export type ServiceProtocol =
    | 'HTTP'
    | 'GRPC';

export class Service {
    private readonly id: string;
    private name: string;
    private version: string;
    private protocol: ServiceProtocol;
    private status: ServiceStatus;
    private readonly createdAt: Date;
    private updatedAt: Date;
    private endpoints: Endpoint[] = [];

    constructor(params: {
        id: string;
        name: string;
        version: string;
        protocol: ServiceProtocol;
        status?: ServiceStatus;
        createdAt?: Date;
        updatedAt?: Date;
        endpoints?: Endpoint[];
    }) {
        this.id = params.id;
        this.name = params.name;
        this.version = params.version;
        this.protocol = params.protocol;
        this.status = params.status ?? 'ACTIVE';
        this.createdAt = params.createdAt ?? new Date();
        this.updatedAt = params.updatedAt ?? new Date();
        this.endpoints = params.endpoints ?? [];
    }

    /* ======================
       Getters
       ====================== */

    getId(): string {
        return this.id;
    }

    getName(): string {
        return this.name;
    }

    getVersion(): string {
        return this.version;
    }

    getProtocol(): ServiceProtocol {
        return this.protocol;
    }

    getStatus(): ServiceStatus {
        return this.status;
    }

    getCreatedAt(): Date {
        return this.createdAt;
    }

    getUpdatedAt(): Date {
        return this.updatedAt;
    }

    /* ======================
       Domain behaviors
       ====================== */

    disable(): void {
        this.status = 'DISABLED';
        this.touch();
    }

    deprecate(): void {
        this.status = 'DEPRECATED';
        this.touch();
    }

    activate(): void {
        this.status = 'ACTIVE';
        this.touch();
    }

    updateVersion(version: string): void {
        this.version = version;
        this.touch();
    }

    /* ======================
       Internal
       ====================== */

    private touch(): void {
        this.updatedAt = new Date();
    }
}
