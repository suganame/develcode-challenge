package com.suganame.payment_gateway.modules.payment.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suganame.payment_gateway.modules.payment.dtos.PaymentRequestDTO;
import com.suganame.payment_gateway.modules.payment.dtos.ProductDTO;
import com.suganame.payment_gateway.modules.payment.repositories.ProductRepository;

@Service
public class PaymentUseCase {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO execute(PaymentRequestDTO paymentRequestDTO) {
        var product = productRepository.getReferenceById(paymentRequestDTO.getId());
        return product.fromEntity();
    }
}