import { Test, TestingModule } from '@nestjs/testing';
import { CheckoutController } from './checkout.controller';
import { CheckoutService } from './checkout.service';
import { CreateCheckoutDto } from './dto/create-checkout.dto';


describe('CheckoutController', () => {
  let controller: CheckoutController;
  let service: CheckoutService;

  const mockCheckoutService = {
    checkout: jest.fn()
  }

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CheckoutController],
      providers: [{
        provide: CheckoutService,
        useValue: mockCheckoutService
      }],
    }).compile();

    controller = module.get<CheckoutController>(CheckoutController);
    service = module.get<CheckoutService>(CheckoutService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  })

  it('should return status 200 when service is called', () => {
    //arrange
    const request: CreateCheckoutDto = {
      id: 1,
      quantity: 10
    }
    const mockResponse = "Operation done succesfully.";
    service.checkout = jest.fn().mockReturnValue(mockResponse)

    const spy = jest.spyOn(service, "checkout");

    //act
    const response = controller.create(request);
    //assert
    expect(spy).toHaveBeenCalled();    
    expect(response).toBe(mockResponse);
  });
});
