package com.suganame.payment_gateway.modules.order.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDTO {
    @Schema(description = "Lista de produtos", example = "[{ id: 1, quantity: 10 }]")
    List<OrderProductDTO> products;
}
