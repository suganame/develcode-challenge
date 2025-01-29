package com.suganame.payment_gateway.modules.payment.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String description;
    BigDecimal quantity;

}
