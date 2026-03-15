export type PermissionStatus =
  | 'ACTIVE'
  | 'DISABLED';

export type PermissionScope =
  | 'OWN'
  | 'ANY';

export class Permission {
  private readonly id: string;

  private resource: string; // ORDER
  private action: string;   // UPDATE
  private scope: PermissionScope;

  private code: string; // UPDATE_OWN_ORDER
  private description?: string;

  private status: PermissionStatus;

  private readonly createdAt: Date;
  private updatedAt: Date;

  constructor(params: {
    id: string;
    resource: string;
    action: string;
    scope: PermissionScope;
    description?: string;
    status?: PermissionStatus;
    createdAt?: Date;
    updatedAt?: Date;
  }) {
    this.id = params.id;
    this.resource = params.resource;
    this.action = params.action;
    this.scope = params.scope;
    this.code = `${this.action}_${this.scope}_${this.resource}`;
    this.description = params.description;
    this.status = params.status ?? 'ACTIVE';
    this.createdAt = params.createdAt ?? new Date();
    this.updatedAt = params.updatedAt ?? new Date();
  }

  /* ========= Getters ========= */

  getId(): string {
    return this.id;
  }

  getCode(): string {
    return this.code;
  }

  getScope(): PermissionScope {
    return this.scope;
  }

  isActive(): boolean {
    return this.status === 'ACTIVE';
  }

  /* ========= Domain behavior ========= */

  disable(): void {
    this.status = 'DISABLED';
    this.touch();
  }

  activate(): void {
    this.status = 'ACTIVE';
    this.touch();
  }

  private touch(): void {
    this.updatedAt = new Date();
  }
}
