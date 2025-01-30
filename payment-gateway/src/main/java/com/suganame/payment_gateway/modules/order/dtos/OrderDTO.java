package com.suganame.payment_gateway.modules.order.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderDTO {
    List<OrderProductDTO> products;
}
