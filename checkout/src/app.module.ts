import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { AxiosModule } from './axios/axios.module';
import { CheckoutModule } from './checkout/checkout.module';

@Module({
  imports: [CheckoutModule, AxiosModule, ConfigModule.forRoot({
    isGlobal: true
  })],
  controllers: [],
  providers: [],
})
export class AppModule { }
