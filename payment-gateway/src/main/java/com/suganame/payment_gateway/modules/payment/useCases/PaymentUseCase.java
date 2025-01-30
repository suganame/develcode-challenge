package com.suganame.payment_gateway.modules.payment.useCases;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.order.dtos.OrderProductDTO;
import com.suganame.payment_gateway.modules.order.entities.OrderEntity;
import com.suganame.payment_gateway.modules.order.enums.OrderStatus;
import com.suganame.payment_gateway.modules.order.repository.OrderRepository;
import com.suganame.payment_gateway.modules.product.entities.ProductEntity;
import com.suganame.payment_gateway.modules.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void execute(OrderDTO orderDTO)
            throws RuntimeException {

        OrderEntity order = new OrderEntity();

        try {
            log.info("Iniciando processamento do pedido.");

            List<Long> productsIds = orderDTO.getProducts().stream().map(OrderProductDTO::getId).toList();

            List<ProductEntity> products = productRepository.findAllById(productsIds);

            validateProducts(orderDTO, products);

            for (ProductEntity product : products) {
                BigDecimal sum;
                OrderProductDTO orderProduct = orderDTO.getProducts().stream().filter(p -> p.getId().equals(product.getId())).findFirst().orElseThrow(() -> new Error(""));
                sum = product.getQuantity().subtract(orderProduct.getQuantity());
                product.setQuantity(sum);
                productRepository.save(product);
            }

            order.setStatus(OrderStatus.DONE);
            orderRepository.save(order);

            log.info("Pedido concluído com sucesso!");

        } catch (Exception ex) {
            cancelOrder(order);
        } finally {
            log.info("Processo finalizado.");
        }
    }

    @Transactional
    private void cancelOrder(OrderEntity order) {
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private void validateProducts(OrderDTO orderDTO, List<ProductEntity> products) {

        validateProductsNotFound(orderDTO, products);

        for (ProductEntity product : products) {
            validateOutOfStock(orderDTO, product);
        }
    }

    private void validateOutOfStock(OrderDTO orderDTO, ProductEntity product) {

        OrderProductDTO orderProduct = orderDTO.getProducts().stream().filter(p -> p.getId().equals(product.getId())).findFirst().orElseThrow(() -> new Error(""));

        if (product.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductOutOfStockException("Produto sem estoque: " + product);
        }

        if (product.getQuantity().compareTo(orderProduct.getQuantity()) < 0) {
            throw new ProductOutOfStockException("Produto sem estoque disponível: " + product);
        }
    }

    private void validateProductsNotFound(OrderDTO orderDTO, List<ProductEntity> products) {
        if (products.size() != orderDTO.getProducts().size()) {
            List<Long> productsIdsNotFound = orderDTO.getProducts().stream()
                    .map(OrderProductDTO::getId)
                    .filter(id -> products.stream().noneMatch(p -> p.getId().equals(id)))
                    .toList();

            throw new ProductNotFoundException("Produtos não encontrados na base da dados: " + productsIdsNotFound);
        }
    }
}