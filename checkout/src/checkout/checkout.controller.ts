import { Body, Controller, HttpCode, HttpStatus, InternalServerErrorException, Logger, Post } from '@nestjs/common';
import { CheckoutService } from './checkout.service';
import { CreateCheckoutDto } from './dto/create-checkout.dto';

@Controller('checkout')
export class CheckoutController {

  private readonly logger = new Logger(CheckoutService.name);

  constructor(private readonly checkoutService: CheckoutService) { }

  @Post()
  @HttpCode(HttpStatus.OK)
  execute(@Body() createCheckoutDto: CreateCheckoutDto) {
    this.logger.log(`Rota /checkout chamada com os seguintes dados: body=${JSON.stringify(createCheckoutDto)}.`)
    return this.checkoutService.checkout(createCheckoutDto);
  }

  @Post("/retry")
  @HttpCode(HttpStatus.OK)
  executeRetry(@Body() createCheckoutDto: CreateCheckoutDto) {
    this.logger.log(`Rota /checkout/retry acessada com os seguintes dados: body=${JSON.stringify(createCheckoutDto)}.`)
    return this.checkoutService.checkoutRetry(createCheckoutDto);
  }
}
