package com.suganame.payment_gateway.modules.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/checkout")
@Slf4j
public class PaymentGatewayController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    private
    ApiResponseDTO<String> apiResponseDTO;

    @PostMapping("/execute")
    @Operation(
        summary = "Executa a transação do pedido, recebido previamente do microsserviço de checkout",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados de entrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OrderDTO.class),
                examples = @ExampleObject(value = "{\"products\": [{\"id\": 1, \"quantity\": 10}]}")
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
        
        try {
            log.info("Rota /checkout/execute chamada.");
            paymentUseCase.execute(orderDTO);
            apiResponseDTO = new ApiResponseDTO<>("Processo concluido", "200");
            log.info("Retornando status 200 com mensagem.");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        } catch (ProductOutOfStockException ex) {
            log.info("Retornando status 409 com messagem.");
            apiResponseDTO = new ApiResponseDTO<>(ex.getMessage(), "409");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CONFLICT);
        } catch (ProductNotFoundException ex) {
            log.info("Retornando status 404 com mensagem.");
            apiResponseDTO = new ApiResponseDTO<>(ex.getMessage(), "404");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.info("Retornando status 500 com mensagem");
            apiResponseDTO = new ApiResponseDTO<>(ex.getMessage(), "500");
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(
        summary = "Endpoint criado para simular o retry. Sempre retornará status 500",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados de entrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OrderDTO.class),
                examples = @ExampleObject(value = "{\"products\": [{\"id\": 1, \"quantity\": 10}]}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
        }
    )
    @PostMapping("/execute/retry")
    public ResponseEntity<ApiResponseDTO<String>> executeRetry(@RequestBody OrderDTO orderDTO) {
        log.info("Rota /checkout/execute chamada.");
        log.info("Esta e uma rota para teste de retry.");
        log.info("Retornando status 500 com mensagem");
        apiResponseDTO = new ApiResponseDTO<>("Test retry.", "500");
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
