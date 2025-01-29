package com.suganame.payment_gateway.seeders;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.suganame.payment_gateway.modules.payment.entities.ProductEntity;
import com.suganame.payment_gateway.modules.payment.repositories.ProductRepository;

@Component
public class ProductSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setDescription("Cafe tres coracoes");
        product1.setQuantity(new BigDecimal(100));

        productRepository.save(product1);

        System.out.println("Seeder [Products] was executed succesfully.");
    }

}
