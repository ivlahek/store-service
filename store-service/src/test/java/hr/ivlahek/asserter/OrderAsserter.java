package hr.ivlahek.asserter;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.OrderItem;
import hr.ivlahek.sample.store.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

@Service
public class OrderAsserter {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void assertOrders(long placedOrderId, CreateOrderDto createOrderDto) {
        Order order = orderRepository.findById(placedOrderId).get();
        assertThat(order.getDateCreated()).isInSameMinuteAs(Date.from(Instant.now()));
        assertThat(order.getEmail()).isEqualTo(createOrderDto.getEmail());
        assertThat(order.getTotalPrice()).isEqualTo(16.7);
        List<OrderItem> orderItems = order.getOrderItems();
        assertThat(orderItems).hasSize(2);
        assertThat(extractProperty("product.id").from(orderItems)).containsOnly(
                createOrderDto.getOrderItemDtos().get(0).getProductId(),
                createOrderDto.getOrderItemDtos().get(1).getProductId());

    }

    @Transactional
    public void assertAgainst(OrderDto orderDto, long placedOrderId) {
        Order order = orderRepository.findById(placedOrderId).get();
        assertThat(orderDto.getId()).isEqualTo(order.getId());
        assertThat(orderDto.getTotalPrice().doubleValue()).isEqualTo(order.getTotalPrice());
        assertThat(orderDto.getEmail()).isEqualTo(order.getEmail());
        assertThat(orderDto.getDateCreated()).isEqualTo(order.getDateCreated());
        assertThat(orderDto.getId()).isEqualTo(order.getId());

        assertThat(orderDto.getOrderItemDtos()).hasSameSizeAs(order.getOrderItems());
        assertPlacedOrderProduct(orderDto.getOrderItemDtos().get(0), order.getOrderItems().get(0));
    }

    private void assertPlacedOrderProduct(OrderItemDto orderItemDto, OrderItem orderItem) {
        assertThat(orderItemDto.getProductId()).isEqualTo(orderItem.getProduct().getId());
        assertThat(orderItemDto.getQuantity()).isEqualTo(orderItem.getQuantity());

    }
}
