export type RoleStatus =
  | 'ACTIVE'
  | 'DISABLED';

export class Role {
  private readonly id: string;

  private name: string;
  private description?: string;
  private status: RoleStatus;

  private permissionIds: Set<string>;

  private readonly createdAt: Date;
  private updatedAt: Date;

  constructor(params: {
    id: string;
    name: string;
    description?: string;
    permissionIds?: string[];
    status?: RoleStatus;
    createdAt?: Date;
    updatedAt?: Date;
  }) {
    this.id = params.id;
    this.name = params.name;
    this.description = params.description;
    this.permissionIds = new Set(params.permissionIds ?? []);
    this.status = params.status ?? 'ACTIVE';
    this.createdAt = params.createdAt ?? new Date();
    this.updatedAt = params.updatedAt ?? new Date();
  }

  /* ========= Getters ========= */

  getId(): string {
    return this.id;
  }

  getName(): string {
    return this.name;
  }

  getPermissionIds(): string[] {
    return Array.from(this.permissionIds);
  }

  getStatus(): RoleStatus {
    return this.status;
  }

  /* ========= Domain behavior ========= */

  addPermission(permissionId: string): void {
    this.permissionIds.add(permissionId);
    this.touch();
  }

  removePermission(permissionId: string): void {
    this.permissionIds.delete(permissionId);
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

  private touch(): void {
    this.updatedAt = new Date();
  }
}