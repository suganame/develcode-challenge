package com.suganame.payment_gateway.modules.payment.useCases;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.order.dtos.OrderProductDTO;
import com.suganame.payment_gateway.modules.order.entities.OrderEntity;
import com.suganame.payment_gateway.modules.order.enums.OrderStatus;
import com.suganame.payment_gateway.modules.order.repository.OrderRepository;
import com.suganame.payment_gateway.modules.product.entities.ProductEntity;
import com.suganame.payment_gateway.modules.product.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private OrderRepository orderRepository;

    public void execute(OrderDTO orderDTO)
            throws RuntimeException {

        OrderEntity order = new OrderEntity();

        log.info("Processando pedido.");

        List<Long> productsIds = orderDTO.getProducts().stream().map(OrderProductDTO::getId).toList();

        List<ProductEntity> products = productRepository.findAllById(productsIds);

        try {
            validateProducts(orderDTO, products);
            order.setProducts(products);
        } catch (Exception ex) {
            cancelOrder(order);
            throw ex;
        }

        TransactionStatus status = transactionManager.getTransaction(null);

        try {            
            for (ProductEntity product : products) {
                BigDecimal sum;
                OrderProductDTO orderProduct = orderDTO.getProducts().stream()
                        .filter(p -> p.getId().equals(product.getId())).findFirst().orElseThrow(() -> new Error("Produto não encontrado."));
                sum = product.getQuantity().subtract(orderProduct.getQuantity());
                product.setQuantity(sum);
                productRepository.save(product);
            }

            order.setStatus(OrderStatus.CONCLUIDO);
            orderRepository.save(order);

            log.info("Pedido concluido com sucesso.");

            transactionManager.commit(status);
        } catch(Exception ex) {
            cancelOrder(order);
            transactionManager.rollback(status);
            throw ex;
        }
    }

    private void cancelOrder(OrderEntity order) {
        order.setStatus(OrderStatus.CANCELADO);
        orderRepository.save(order);
    }

    private void validateProducts(OrderDTO orderDTO, List<ProductEntity> products) {

        validateProductsNotFound(orderDTO, products);

        for (ProductEntity product : products) {
            validateOutOfStock(orderDTO, product);
        }
    }

    private void validateOutOfStock(OrderDTO orderDTO, ProductEntity product) {

        OrderProductDTO orderProduct = orderDTO.getProducts().stream().filter(p -> p.getId().equals(product.getId()))
                .findFirst().orElseThrow(() -> new Error("Produto não encontrado."));

        if (product.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Produto sem estoque disponivel. Produto=" + product);
            throw new ProductOutOfStockException("Produto sem estoque disponivel: " + product.getDescription());
        }

        if (product.getQuantity().compareTo(orderProduct.getQuantity()) < 0) {
            log.error("Produto sem estoque para concluir o pedido. Produto=" + product);
            throw new ProductOutOfStockException(
                    "Produto sem estoque para concluir o pedido: " + product.getDescription());
        }
    }

    private void validateProductsNotFound(OrderDTO orderDTO, List<ProductEntity> products) {
        if (products.size() != orderDTO.getProducts().size()) {
            List<Long> productsIdsNotFound = orderDTO.getProducts().stream()
                    .map(OrderProductDTO::getId)
                    .filter(id -> products.stream().noneMatch(p -> p.getId().equals(id)))
                    .toList();

            log.error("Produto(s) nao encontrados na base da dados. Entrada=" + orderDTO, productsIdsNotFound);
            throw new ProductNotFoundException("Produto(s) nao encontrados na base da dados: " + productsIdsNotFound);
        }
    }
}