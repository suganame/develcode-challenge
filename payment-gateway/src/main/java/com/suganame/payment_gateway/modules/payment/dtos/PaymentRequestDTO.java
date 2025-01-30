package com.suganame.payment_gateway.modules.payment.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.suganame.payment_gateway.modules.product.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequestDTO {
    List<ProductDTO> products;
}