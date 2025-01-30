package com.suganame.payment_gateway.modules.payment.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.payment.useCases.PaymentUseCase;

@SpringBootTest
public class PaymentGatewayControllerTest {
    @Mock
    private PaymentUseCase paymentUseCase;

    @InjectMocks
    private PaymentGatewayController paymentGatewayController;

    private OrderDTO orderDTO;

    @BeforeEach
    public void setup() {
        orderDTO = new OrderDTO();
    }

    @Test
    public void testExecute() {
        Mockito.doNothing().when(paymentUseCase).execute(Mockito.any());

        ResponseEntity<?> response = paymentGatewayController.execute(orderDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testExecuteNotFoundException() {
        Mockito.doThrow(new ProductNotFoundException("")).when(paymentUseCase).execute(Mockito.any());

        ResponseEntity<?> response = paymentGatewayController.execute(orderDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testExecuteConflictException() {
        Mockito.doThrow(new ProductOutOfStockException("")).when(paymentUseCase).execute(Mockito.any());

        ResponseEntity<?> response = paymentGatewayController.execute(orderDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testExecuteRetry() {
        Mockito.doNothing().when(paymentUseCase).execute(Mockito.any());

        ResponseEntity<?> response = paymentGatewayController.executeRetry(orderDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
