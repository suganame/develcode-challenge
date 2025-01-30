package com.suganame.payment_gateway.modules.order.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OrderProductDTO {
    private Long id;
    private BigDecimal quantity;
}
