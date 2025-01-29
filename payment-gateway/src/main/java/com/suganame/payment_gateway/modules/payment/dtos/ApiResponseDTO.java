package com.suganame.payment_gateway.modules.payment.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponseDTO<T> {
    private T message;
    private String statusCode;
}
