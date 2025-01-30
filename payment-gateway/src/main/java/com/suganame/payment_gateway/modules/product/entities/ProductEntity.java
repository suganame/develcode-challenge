package com.suganame.payment_gateway.modules.product.entities;

import java.math.BigDecimal;
import java.util.List;

import com.suganame.payment_gateway.modules.order.entities.OrderEntity;
import com.suganame.payment_gateway.modules.product.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;
}
