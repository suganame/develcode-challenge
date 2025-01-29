import { Injectable, InternalServerErrorException, Logger } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import axios, { AxiosError } from 'axios';
import axiosRetry from 'axios-retry';
import { CreateCheckoutDto } from './dto/create-checkout.dto';

@Injectable()
export class CheckoutService {

  private readonly logger = new Logger(CheckoutService.name);

  constructor(private configService: ConfigService) { }

  async checkout(createCheckoutDto: CreateCheckoutDto) {

    let attemptCount: number = 0;

    axios.interceptors.request.use((config) => {
      attemptCount++;
      this.logger.warn(`Something went wrong, trying again: Attempt number #${attemptCount}`);
      return config;
    });

    const baseUrl = this.configService.get<string>('PAYMENT_GATEWAY_BASEURL');

    axiosRetry(axios, {
      retries: 3, retryDelay: (retryCount) => {
        return axiosRetry.exponentialDelay(retryCount) * 3;
      }
    });

    try {
      const { data } = await axios.post(`${baseUrl}/checkout/execute`, createCheckoutDto, {
        headers: {
          "Content-Type": "application/json"
        }
      })

      this.logger.verbose(`Operation done succesfully. response=${JSON.stringify(data)}`)

      return data
    } catch (error) {
      if (error instanceof AxiosError) {
        this.logger.error("After several attempts, the server returned an error. Please, Try again later.", new Error().stack)
        throw new InternalServerErrorException("After several attempts, the server returned an error. Please, Try again later.");
      }

    }
  }
}
