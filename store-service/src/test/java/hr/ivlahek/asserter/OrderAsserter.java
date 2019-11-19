package hr.ivlahek.asserter;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrderItem;
import hr.ivlahek.sample.store.persistence.repository.PlacedOrderRepository;
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
    private PlacedOrderRepository placedOrderRepository;

    @Transactional
    public void assertOrders(long placedOrderId, CreateOrderDto createOrderDto) {
        PlacedOrder placedOrder = placedOrderRepository.findById(placedOrderId).get();
        assertThat(placedOrder.getDateCreated()).isInSameMinuteAs(Date.from(Instant.now()));
        assertThat(placedOrder.getEmail()).isEqualTo(createOrderDto.getEmail());
        assertThat(placedOrder.getTotalPrice()).isEqualTo(16.7);
        List<PlacedOrderItem> placedOrderItems = placedOrder.getPlacedOrderItems();
        assertThat(placedOrderItems).hasSize(2);
        assertThat(extractProperty("product.id").from(placedOrderItems)).containsOnly(
                createOrderDto.getOrderItemDtos().get(0).getProductId(),
                createOrderDto.getOrderItemDtos().get(1).getProductId());

    }

    @Transactional
    public void assertAgainst(OrderDto orderDto, long placedOrderId) {
        PlacedOrder placedOrder = placedOrderRepository.findById(placedOrderId).get();
        assertThat(orderDto.getId()).isEqualTo(placedOrder.getId());
        assertThat(orderDto.getTotalPrice().doubleValue()).isEqualTo(placedOrder.getTotalPrice());
        assertThat(orderDto.getEmail()).isEqualTo(placedOrder.getEmail());
        assertThat(orderDto.getDateCreated()).isEqualTo(placedOrder.getDateCreated());
        assertThat(orderDto.getId()).isEqualTo(placedOrder.getId());

        assertThat(orderDto.getOrderItemDtos()).hasSameSizeAs(placedOrder.getPlacedOrderItems());
        assertPlacedOrderProduct(orderDto.getOrderItemDtos().get(0), placedOrder.getPlacedOrderItems().get(0));
    }

    private void assertPlacedOrderProduct(OrderItemDto orderItemDto, PlacedOrderItem placedOrderItem) {
        assertThat(orderItemDto.getProductId()).isEqualTo(placedOrderItem.getProduct().getId());
        assertThat(orderItemDto.getQuantity()).isEqualTo(placedOrderItem.getQuantity());

    }
}
