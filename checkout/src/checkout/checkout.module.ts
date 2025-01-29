import { Module } from '@nestjs/common';
import { AxiosModule } from 'src/axios/axios.module';
import { CheckoutController } from './checkout.controller';
import { CheckoutService } from './checkout.service';

@Module({
  controllers: [CheckoutController],
  providers: [CheckoutService],
  imports: [AxiosModule]
})
export class CheckoutModule {}
