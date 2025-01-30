package com.suganame.payment_gateway.modules.order.entities;

import com.suganame.payment_gateway.modules.order.enums.OrderStatus;
import com.suganame.payment_gateway.modules.product.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private OrderStatus status = OrderStatus.IN_PROGRESS;

    @ManyToMany
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private List<ProductEntity> products;
}
