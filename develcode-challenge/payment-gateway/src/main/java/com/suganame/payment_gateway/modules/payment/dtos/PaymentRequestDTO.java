package com.suganame.payment_gateway.modules.payment.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequestDTO {
    Long id;
    BigDecimal quantity;
}