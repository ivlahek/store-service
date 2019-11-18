package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderMapper {

    public PlacedOrder map(CreateOrderDto createOrderDto, double price) {
        PlacedOrder placedOrder = new PlacedOrder();
        placedOrder.setDateCreated(Date.from(Instant.now()));
        placedOrder.setEmail(createOrderDto.getEmail().toLowerCase());
        placedOrder.setTotalPrice(price);
        return placedOrder;
    }

    public OrderDto map(PlacedOrder placedOrder) {
        OrderDto orderDto = mapCommon(placedOrder);
        placedOrder.getPlacedOrderItems().forEach(placedOrderProduct -> orderDto.getOrderItemDtos().add(new OrderItemDto(placedOrderProduct.getProduct().getId(), placedOrderProduct.getQuantity())));
        return orderDto;
    }

    private OrderDto mapCommon(PlacedOrder placedOrder) {
        OrderDto orderDto = new OrderDto();
        orderDto.setDateCreated(placedOrder.getDateCreated());
        orderDto.setEmail(placedOrder.getEmail());
        orderDto.setId(placedOrder.getId());
        orderDto.setTotalPrice(placedOrder.getTotalPrice());
        return orderDto;
    }


    public List<OrderDto> map(List<PlacedOrder> content, Map<Long, List<OrderItemDto>> itemsPerPlacedOrder) {
        List<OrderDto> result = new ArrayList<>();
        content.forEach(placedOrder -> result.add(map(placedOrder, itemsPerPlacedOrder.get(placedOrder.getId()))));
        return result;
    }

    private OrderDto map(PlacedOrder placedOrder, List<OrderItemDto> itemsPerPlacedOrder) {
        OrderDto orderDto = mapCommon(placedOrder);
        orderDto.getOrderItemDtos().addAll(itemsPerPlacedOrder);
        return orderDto;
    }
}
