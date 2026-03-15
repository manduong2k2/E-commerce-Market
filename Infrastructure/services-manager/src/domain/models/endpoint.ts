export class Endpoint {
  private readonly id: string;
  private readonly serviceId: string;

  private code: string;
  private method: string;
  private path: string;
  private version: string;

  private isPublic: boolean;
  private status: string;

  private readonly createdAt: Date;
  private updatedAt: Date;

  constructor(params: {
    id: string;
    serviceId: string;
    code: string;
    method: string;
    path: string;
    version: string;
    isPublic?: boolean;
    status?: string;
    createdAt?: Date;
    updatedAt?: Date;
  }) {
    this.id = params.id;
    this.serviceId = params.serviceId;
    this.code = params.code;
    this.method = params.method;
    this.path = params.path;
    this.version = params.version;
    this.isPublic = params.isPublic ?? false;
    this.status = params.status ?? 'ACTIVE';
    this.createdAt = params.createdAt ?? new Date();
    this.updatedAt = params.updatedAt ?? new Date();
  }

  /* ======================
     Getters
     ====================== */

  getId(): string {
    return this.id;
  }

  getServiceId(): string {
    return this.serviceId;
  }

  getCode(): string {
    return this.code;
  }

  getMethod(): string {
    return this.method;
  }

  getPath(): string {
    return this.path;
  }

  getVersion(): string {
    return this.version;
  }

  isEndpointPublic(): boolean {
    return this.isPublic;
  }

  getStatus(): string {
    return this.status;
  }

  /* ======================
     Domain behaviors
     ====================== */

  makePublic(): void {
    this.isPublic = true;
    this.touch();
  }

  makePrivate(): void {
    this.isPublic = false;
    this.touch();
  }

  disable(): void {
    this.status = 'DISABLED';
    this.touch();
  }

  activate(): void {
    this.status = 'ACTIVE';
    this.touch();
  }

  updateRoute(method: string, path: string): void {
    this.method = method;
    this.path = path;
    this.touch();
  }

  /* ======================
     Internal
     ====================== */

  private touch(): void {
    this.updatedAt = new Date();
  }

  public static fromEntity(entity: {
    id: string;
    serviceId: string;
    code: string;
    method: string;
    path: string;
    version: string;
    isPublic: boolean;
    status: string;
    createdAt: Date;
    updatedAt: Date;
  }): Endpoint {
    return new Endpoint({
      id: entity.id,
      serviceId: entity.serviceId,
      code: entity.code,
      method: entity.method,
      path: entity.path,
      version: entity.version,
      isPublic: entity.isPublic,
      status: entity.status,
      createdAt: entity.createdAt,
      updatedAt: entity.updatedAt,
    });
  }
}
