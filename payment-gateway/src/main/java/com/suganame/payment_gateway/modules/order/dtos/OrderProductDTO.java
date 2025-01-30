package com.suganame.payment_gateway.modules.order.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class OrderProductDTO {
    private Long id;
    private BigDecimal quantity;
}
