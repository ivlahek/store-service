package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.CreateOrderDtoBuilder;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.persistence.entity.*;
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

        PlacedOrder placedOrder = orderMapper.map(createOrder, 10d);

        assertThat(placedOrder.getDateCreated()).isInSameMinuteAs(Date.from(Instant.now()));
        assertThat(placedOrder.getEmail()).isEqualTo(createOrder.getEmail());
        assertThat(placedOrder.getId()).isNull();
        assertThat(placedOrder.getTotalPrice()).isEqualTo(10d);
    }

    @Test
    public void should_map_to_dto() {
        PlacedOrder placedOrder = PlacedOrderBuilder.anOrder().withId(1L)
                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product1).withQuantity(1).build())
                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product2).withQuantity(2).build())
                .build();

        OrderDto dto = orderMapper.map(placedOrder);

        assertProductDto(dto, placedOrder);
        assertThat(dto.getOrderItemDtos()).hasSize(2);
    }

    private void assertProductDto(OrderDto dto, PlacedOrder placedOrder) {
        assertThat(dto.getDateCreated()).isEqualTo(placedOrder.getDateCreated());
        assertThat(dto.getEmail()).isEqualTo(placedOrder.getEmail());
        assertThat(dto.getId()).isEqualTo(placedOrder.getId()).isNotNull();
        assertThat(dto.getTotalPrice().doubleValue()).isEqualTo(placedOrder.getTotalPrice());
    }

}