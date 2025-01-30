package com.suganame.payment_gateway.seeds;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.suganame.payment_gateway.modules.product.entities.ProductEntity;
import com.suganame.payment_gateway.modules.product.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Executing seed...");
        createSeedData();
        log.info("Seed [Products] was executed succesfully.");
    }

    public void createSeedData() {
        ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setDescription("Cafe tres coracoes");
        product1.setQuantity(new BigDecimal(100));

        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);
        product2.setDescription("Coca cola 2L");
        product2.setQuantity(new BigDecimal(0));

        productRepository.save(product1);
        productRepository.save(product2);
    }

}
