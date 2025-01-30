package com.suganame.payment_gateway.modules.payment.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.suganame.payment_gateway.exceptions.ProductNotFoundException;
import com.suganame.payment_gateway.exceptions.ProductOutOfStockException;
import com.suganame.payment_gateway.modules.order.dtos.OrderDTO;
import com.suganame.payment_gateway.modules.order.dtos.OrderProductDTO;
import com.suganame.payment_gateway.modules.order.entities.OrderEntity;
import com.suganame.payment_gateway.modules.order.repository.OrderRepository;
import com.suganame.payment_gateway.modules.payment.controllers.PaymentGatewayController;
import com.suganame.payment_gateway.modules.product.dto.ProductDTO;
import com.suganame.payment_gateway.modules.product.entities.ProductEntity;
import com.suganame.payment_gateway.modules.product.repositories.ProductRepository;

@SpringBootTest
public class PaymentUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentUseCase paymentUseCase;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private TransactionStatus transactionStatus;

    private OrderDTO orderDTO;

    private List<OrderProductDTO> listOrderProducts = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mock();
        
    }

    @Test
    public void executeTest() {
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setDescription("test");
        productEntity.setQuantity(new BigDecimal(200));
        list.add(productEntity);

        Mockito.when(productRepository.findAllById(Mockito.any())).thenReturn(list);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new OrderEntity());
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        paymentUseCase.execute(orderDTO);
        verify(transactionManager, times(1)).commit(transactionStatus);
    }

    @Test
    public void execute_WithOutOfStockException_withNoStockTest() {
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setDescription("test");
        productEntity.setQuantity(new BigDecimal(0));
        list.add(productEntity);

        Mockito.when(productRepository.findAllById(Mockito.any())).thenReturn(list);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new OrderEntity());
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        try {
            paymentUseCase.execute(orderDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ProductOutOfStockException.class);
        }        
    }

    @Test
    public void execute_WithOutOfStockException_noStockTest() {
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setDescription("test");
        productEntity.setQuantity(new BigDecimal(5));
        list.add(productEntity);

        Mockito.when(productRepository.findAllById(Mockito.any())).thenReturn(list);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new OrderEntity());
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        try {
            paymentUseCase.execute(orderDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ProductOutOfStockException.class);
        }        
    }

    @Test
    public void execute_ProductNotFoundExceptionTest() {

        OrderProductDTO productDTO =  new OrderProductDTO(999L, new BigDecimal(10));
        listOrderProducts.add(productDTO);

        orderDTO.setProducts(listOrderProducts);

        List<ProductEntity> list = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setDescription("test");
        productEntity.setQuantity(new BigDecimal(100));
        list.add(productEntity);

        Mockito.when(productRepository.findAllById(Mockito.any())).thenReturn(list);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new OrderEntity());
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        try {
            paymentUseCase.execute(orderDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ProductNotFoundException.class);
        }        
    }

    @Test
    public void execute_anotherExceptionTest() {

        List<ProductEntity> list = new ArrayList<>();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setDescription("test");
        productEntity.setQuantity(new BigDecimal(100));
        list.add(productEntity);

        Mockito.when(productRepository.findAllById(Mockito.any())).thenReturn(list);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(new OrderEntity());
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        Mockito.doThrow(new RuntimeException()).when(productRepository).save(Mockito.any());

        try {
            paymentUseCase.execute(orderDTO);
        } catch (Exception ex) {
            assertEquals(ex.getClass(), RuntimeException.class);
        }        
    }

    private void mock() {
        OrderProductDTO productDTO =  new OrderProductDTO(1L, new BigDecimal(10));
        listOrderProducts.add(productDTO);

        orderDTO = new OrderDTO();
        orderDTO.setProducts(listOrderProducts);
    }    
}
