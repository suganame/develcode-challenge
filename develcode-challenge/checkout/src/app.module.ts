import { Module } from '@nestjs/common';
import { CheckoutModule } from './checkout/checkout.module';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [CheckoutModule, ConfigModule.forRoot({
    isGlobal: true
  })],
  controllers: [],
  providers: [],
})
export class AppModule {}
