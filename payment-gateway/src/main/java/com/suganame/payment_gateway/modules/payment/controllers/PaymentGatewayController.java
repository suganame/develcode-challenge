package com.suganame.payment_gateway.modules.payment.controllers;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.payment.dtos.ApiResponseDTO;
import com.suganame.payment_gateway.modules.payment.dtos.PaymentRequestDTO;
import com.suganame.payment_gateway.modules.payment.useCases.PaymentUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
@Slf4j
public class PaymentGatewayController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    @PostMapping("/execute")
    public ResponseEntity<ApiResponseDTO<String>> execute(@RequestBody OrderDTO orderDTO) throws ProductNotFoundException, ProductOutOfStockException, RuntimeException {
        ApiResponseDTO<String> apiResponseDTO;
        try {
            log.info("Route /checkout/execute was called.");
            paymentUseCase.execute(orderDTO);
            apiResponseDTO = new ApiResponseDTO<>("Transaction done.", "200");
            log.info("Returning status 200 with message.");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        } catch (ProductOutOfStockException ex) {
            log.info("Returning status 409 with message.");
            apiResponseDTO = new ApiResponseDTO<>(ex.getMessage(), "409");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CONFLICT);
        } catch (ProductNotFoundException ex) {
            log.info("Returning status 404 with message.");
            apiResponseDTO = new ApiResponseDTO<>(ex.getMessage(), "404");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/execute/retry")
    public ResponseEntity<?> executeRetry(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        log.info("Route /checkout/execute was called.");
        log.info("This is a retry-routing test.");
        log.info("Returning status 500 with message.");
        return new ResponseEntity<>("Test retry.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
