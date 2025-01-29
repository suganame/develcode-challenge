import { Body, Controller, HttpCode, HttpStatus, InternalServerErrorException, Logger, Post } from '@nestjs/common';
import { CheckoutService } from './checkout.service';
import { CreateCheckoutDto } from './dto/create-checkout.dto';

@Controller('checkout')
export class CheckoutController {

  private readonly logger = new Logger(CheckoutService.name);

  constructor(private readonly checkoutService: CheckoutService) {}

  @Post()
  @HttpCode(HttpStatus.OK)
  create(@Body() createCheckoutDto: CreateCheckoutDto) {
    this.logger.log(`Route /checkout accessed with followed data: body=${JSON.stringify(createCheckoutDto)}.`)
    return this.checkoutService.checkout(createCheckoutDto);   
  }
}
