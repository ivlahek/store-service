package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.CreateOrderDtoBuilder;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.persistence.entity.*;
import hr.ivlahek.sample.store.service.mapper.OrderMapper;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapperTest {

    private OrderMapper orderMapper = new OrderMapper();
    private Product product1;
    private Product product2;

    @Before
    public void setUp() throws Exception {
        product1 = ProductBuilder.aProduct1().withId(1L).build();
        product2 = ProductBuilder.aProduct2().withId(2L).build();
    }

    @Test
    public void should_map_to_entity() {
        CreateOrderDto createOrder = CreateOrderDtoBuilder.aCreateOrderDto().build();

        Order order = orderMapper.map(createOrder, 10d);

        assertThat(order.getDateCreated()).isInSameMinuteAs(Date.from(Instant.now()));
        assertThat(order.getEmail()).isEqualTo(createOrder.getEmail());
        assertThat(order.getId()).isNull();
        assertThat(order.getTotalPrice()).isEqualTo(10d);
    }

    @Test
    public void should_map_to_dto() {
        Order order = PlacedOrderBuilder.anOrder().withId(1L)
                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product1).withQuantity(1).build())
                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product2).withQuantity(2).build())
                .build();

        OrderDto dto = orderMapper.map(order);

        assertProductDto(dto, order);
        assertThat(dto.getOrderItemDtos()).hasSize(2);
    }

    private void assertProductDto(OrderDto dto, Order order) {
        assertThat(dto.getDateCreated()).isEqualTo(order.getDateCreated());
        assertThat(dto.getEmail()).isEqualTo(order.getEmail());
        assertThat(dto.getId()).isEqualTo(order.getId()).isNotNull();
        assertThat(dto.getTotalPrice().doubleValue()).isEqualTo(order.getTotalPrice());
    }

}