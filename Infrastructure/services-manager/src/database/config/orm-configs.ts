import { TypeOrmModuleOptions } from '@nestjs/typeorm';

export const ormConfig = (): TypeOrmModuleOptions => ({
  type: 'postgres', // hoặc mysql
  host: process.env.DB_HOST,
  port: Number(process.env.DB_PORT),
  username: String(process.env.DB_USER),
  password: String(process.env.DB_PASSWORD),
  database: process.env.DB_NAME,

  // Chỉ load ORM models (adapter), KHÔNG load domain entity
  entities: [
    __dirname + '/../../domain/entities/**/*.{ts,js}',
  ],

  synchronize: true,
  logging: ['error'],

  ssl: process.env.DB_SSL === 'true'
    ? { rejectUnauthorized: false }
    : false,
});