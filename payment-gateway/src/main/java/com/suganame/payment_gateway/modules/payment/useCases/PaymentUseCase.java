package com.suganame.payment_gateway.modules.payment.useCases;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.payment.dtos.PaymentRequestDTO;
import com.suganame.payment_gateway.modules.payment.entities.ProductEntity;
import com.suganame.payment_gateway.modules.payment.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void execute(PaymentRequestDTO paymentRequestDTO)
            throws ProductNotFoundException, ProductOutOfStockException, RuntimeException {
        ProductEntity product = productRepository.findById(paymentRequestDTO.getId()).orElse(null);

        log.info("Starting transaction...");

        log.info("Applying validations...");

        if (product == null) {
            log.error("Product not found");
            throw new ProductNotFoundException("Product not found.");
        }

        if (product.getQuantity().compareTo(BigDecimal.ZERO) <= 0
                || product.getQuantity().compareTo(paymentRequestDTO.getQuantity()) < 0) {
            log.error("Product out of stock");
            throw new ProductOutOfStockException("Product out of stock.");
        }

        log.info("Validations done");

        try {
            log.info("Product was found, doing checkout.");
            BigDecimal sum = product.getQuantity().subtract(paymentRequestDTO.getQuantity());
            product.setQuantity(sum);
            log.info("Saving in database");
            productRepository.save(product);
            log.info("Checkout completed.");
            log.info("Transaction completed successfully.");

        } catch (Exception ex) {
            log.error("Something went wrong", ex);
            throw new RuntimeException(ex);
        }
    }
}