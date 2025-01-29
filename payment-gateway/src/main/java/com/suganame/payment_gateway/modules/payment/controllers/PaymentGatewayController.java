package com.suganame.payment_gateway.modules.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suganame.payment_gateway.modules.payment.dtos.PaymentRequestDTO;
import com.suganame.payment_gateway.modules.payment.dtos.ProductDTO;
import com.suganame.payment_gateway.modules.payment.useCases.PaymentUseCase;

@RestController
@RequestMapping("/checkout")
public class PaymentGatewayController {

    @Autowired
    private PaymentUseCase paymentUseCase;

    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        ProductDTO productDTO = paymentUseCase.execute(paymentRequestDTO);
        return ResponseEntity.ok(productDTO);
    }
}
