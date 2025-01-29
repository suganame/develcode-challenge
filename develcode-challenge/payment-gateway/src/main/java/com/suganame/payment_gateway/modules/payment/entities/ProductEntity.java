package com.suganame.payment_gateway.modules.payment.entities;

import java.math.BigDecimal;

import com.suganame.payment_gateway.modules.payment.dtos.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "products")
public class ProductEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 400)
    private String description;

    @Column(nullable = false)
    private BigDecimal quantity;

    public ProductDTO fromEntity() {
        return new ProductDTO(id, description, quantity);
    }
}
