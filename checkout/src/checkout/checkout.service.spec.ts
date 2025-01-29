import { HttpService } from '@nestjs/axios';
import { GatewayTimeoutException, InternalServerErrorException } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { Test, TestingModule } from '@nestjs/testing';
import { AxiosError } from 'axios';
import { of } from 'rxjs';
import { CheckoutService } from './checkout.service';
import { CreateCheckoutDto } from './dto/create-checkout.dto';

describe('CheckoutService', () => {
  let service: CheckoutService;
  let configService: ConfigService;
  let httpService: HttpService;


  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [CheckoutService, {
        provide: HttpService,
        useValue: {
          post: jest.fn()
        }
      }],
      imports: [ConfigModule.forRoot()]
    }).compile();

    service = module.get<CheckoutService>(CheckoutService);
    configService = module.get<ConfigService>(ConfigService);
    httpService = module.get<HttpService>(HttpService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it("should calls payment gateway succesfully.", async () => {
    //arrange
    const request: CreateCheckoutDto = {
      id: 1,
      quantity: 10
    }

    const mockResponse = {
      data: {
        status: 200,
        data: "Operation done."
      }
    }

    httpService.post = jest.fn().mockReturnValue(of(mockResponse))

    //act
    const response = await service.checkout(request);

    //assert
    expect(response).toBe(mockResponse.data)
  })

  it("should throws GatewayTimeoutException payment gateway is not available.", async () => {
    //arrange
    const request: CreateCheckoutDto = {
      id: 1,
      quantity: 10
    }
    httpService.post = jest.fn().mockImplementation(() => {
      throw new AxiosError()
    })

    //act
    try {
      await service.checkout(request);
    } catch (error) {
      //assert
      expect(error).toBeInstanceOf(GatewayTimeoutException);
    }
  })

  it("should throws InternalServerErrorException the enviroment variable was not found.", async () => {
    //arrange
    configService.set("PAYMENT_GATEWAY_BASEURL", "");

    //act
    try {
      new CheckoutService(configService, httpService);
    } catch (error) {
      //assert
      expect(error).toBeInstanceOf(InternalServerErrorException);
    }
  })
});
