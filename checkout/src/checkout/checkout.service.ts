import { HttpService } from '@nestjs/axios';
import { ConflictException, GatewayTimeoutException, Injectable, InternalServerErrorException, Logger } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { firstValueFrom } from 'rxjs';
import { CreateCheckoutDto } from './dto/create-checkout.dto';
import { AxiosError } from 'axios';

@Injectable()
export class CheckoutService {

  private readonly logger = new Logger(CheckoutService.name);
  private readonly baseUrl;

  constructor(private configService: ConfigService, private readonly httpService: HttpService) {
    if (!this.configService.get<string>('PAYMENT_GATEWAY_BASEURL') || this.configService.get<string>('PAYMENT_GATEWAY_BASEURL') === "") {
      throw new InternalServerErrorException();
    }
    this.baseUrl = this.configService.get<string>('PAYMENT_GATEWAY_BASEURL');
  }

  async checkout(createCheckoutDto: CreateCheckoutDto) {
    try {
      const { data } = await firstValueFrom(this.httpService.post(`${this.baseUrl}/checkout/execute`, createCheckoutDto, {
        headers: {
          "Content-Type": "application/json"
        }
      }));
      return data;
    } catch (error) {
      if (error.code == "ECONNREFUSED") {
        this.logger.error("Payment gateway unavaliable.", new Error().stack)
        throw new InternalServerErrorException();
      }
      if (error.status === 500) {
        this.logger.error("After several attempts, the server returned an error.", new Error().stack)
        throw new GatewayTimeoutException("After several attempts, the server returned an error. Please, contact our support.")
      } else if (error.status === 409) {
        this.logger.error(error.response.data, new Error().stack)
        throw new ConflictException(error.response.data)
      } else {
        this.logger.error("Unknow error.", new Error().stack)
        throw new InternalServerErrorException();
      }
    }
  }

  async checkoutRetry(createCheckoutDto: CreateCheckoutDto) {
    try {
      const { data } = await firstValueFrom(this.httpService.post(`${this.baseUrl}/checkout/execute/retry`, createCheckoutDto, {
        headers: {
          "Content-Type": "application/json"
        }
      }));
      return data;
    } catch (error) {
      if (error.code == "ECONNREFUSED") {
        this.logger.error("Payment gateway unavaliable.", new Error().stack)
        throw new InternalServerErrorException();
      }
      if (error.status === 500) {
        this.logger.error("After several attempts, the server returned an error.", new Error().stack)
        throw new GatewayTimeoutException("After several attempts, the server returned an error. Please, contact our support.")
      } else if (error.status === 409) {
        this.logger.error(error.response.data, new Error().stack)
        throw new ConflictException(error.response.data)
      } else {
        this.logger.error("Unknow error.", new Error().stack)
        throw new InternalServerErrorException();
      }
    }
  }
}
