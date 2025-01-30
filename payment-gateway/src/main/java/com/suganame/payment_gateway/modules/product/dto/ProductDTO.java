package com.suganame.payment_gateway.modules.product.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    Long id;
    BigDecimal quantity;
}
