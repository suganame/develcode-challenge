package com.suganame.payment_gateway.modules.product.entities;

import java.math.BigDecimal;
import java.util.List;

import com.suganame.payment_gateway.modules.order.entities.OrderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
@ToString
public class ProductEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 400)
    private String description;

    @Column(nullable = false)
    private BigDecimal quantity;

    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;
}
