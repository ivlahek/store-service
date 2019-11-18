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
        assertThat(dto.getTotalPrice()).isEqualTo(placedOrder.getTotalPrice());
    }

//    @Test
//    public void should_map_to_dto_list() {
//        PlacedOrder placedOrder1 = PlacedOrderBuilder.anOrder1().withId(1L)
//                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product1).withQuantity(1).build())
//                .build();
//        PlacedOrder placedOrder2 = PlacedOrderBuilder.anOrder2().withId(2L)
//                .addPlacedOrderItem(PlacedOrderProductBuilder.aPlacedOrderItem().withProduct(product2).withQuantity(2).build())
//                .build();
//
//        List<PlacedOrderDto> dtos = orderMapper.map(Lists.newArrayList(placedOrder1, placedOrder2));
//        assertThat(dtos).hasSize(2);
//
//        PlacedOrderDto placedOrderDto1 = dtos.get(0);
//        assertProductDto(placedOrderDto1, placedOrder1);
//        assertThat(placedOrderDto1.getPlacedOrderItemDtos()).hasSize(1);
//        PlacedOrderItemDto placedOrderItemDto1 = placedOrderDto1.getPlacedOrderItemDtos().get(0);
//        assertThat(placedOrderItemDto1.getProductId()).isEqualTo(product1.getId());
//        assertThat(placedOrderItemDto1.getQuantity()).isEqualTo(1);
//
//        PlacedOrderDto placedOrderDto2 = dtos.get(1);
//        assertProductDto(placedOrderDto2, placedOrder2);
//        assertThat(placedOrderDto2.getPlacedOrderItemDtos()).hasSize(1);
//
//        PlacedOrderItemDto placedOrderItemDto2 = placedOrderDto2.getPlacedOrderItemDtos().get(0);
//        assertThat(placedOrderItemDto2.getProductId()).isEqualTo(product2.getId());
//        assertThat(placedOrderItemDto2.getQuantity()).isEqualTo(2);
//    }

}