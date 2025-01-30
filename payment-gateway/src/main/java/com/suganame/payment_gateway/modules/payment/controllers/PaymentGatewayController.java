package com.suganame.payment_gateway.modules.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.payment.dtos.ApiResponseDTO;
import com.suganame.payment_gateway.modules.payment.useCases.PaymentUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/checkout")
@Slf4j
public class PaymentGatewayController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    @PostMapping("/execute")
    @Operation(
        summary = "Executa a transação do pedido, recebido previamente do microsserviço de checkout",
        requestBody = @RequestBody(
            description = "Dados de entrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OrderDTO.class),
                examples = @ExampleObject(value = "[{\"id\": 1, \"quantity\": 10}]")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Transação efetuada com sucesso."),
            @ApiResponse(responseCode = "409", description = "Produto com estoque insuficiente."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado da base de dados.")
        }
    )
    public ResponseEntity<ApiResponseDTO<String>> execute(@RequestBody OrderDTO orderDTO)
            throws ProductNotFoundException, ProductOutOfStockException, RuntimeException {
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
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/execute/retry")
    public ResponseEntity<?> executeRetry(@RequestBody OrderDTO orderDTO) {
        log.info("Route /checkout/execute was called.");
        log.info("This is a retry-routing test.");
        log.info("Returning status 500 with message.");
        return new ResponseEntity<>("Test retry.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
