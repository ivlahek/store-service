package hr.ivlahek.sample.store.service.mapper;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.Order;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {

    public Order map(CreateOrderDto createOrderDto, double totalPrice) {
        Order order = new Order();
        order.setDateCreated(Date.from(Instant.now()));
        order.setEmail(createOrderDto.getEmail().toLowerCase());
        order.setTotalPrice(totalPrice);
        return order;
    }

    public OrderDto map(Order order) {
        OrderDto orderDto = mapCommon(order);
        List<OrderItemDto> orderItemDtos = order
                .getOrderItems()
                .stream()
                .map(placedOrderItem -> new OrderItemDto(placedOrderItem.getProduct().getId(), placedOrderItem.getQuantity()))
                .collect(Collectors.toList());
        orderDto.setOrderItemDtos(orderItemDtos);
        return orderDto;
    }

    private OrderDto mapCommon(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setDateCreated(order.getDateCreated());
        orderDto.setEmail(order.getEmail());
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(BigDecimal.valueOf(order.getTotalPrice()));
        return orderDto;
    }

    public List<OrderDto> map(List<Order> content, Map<Long, List<OrderItemDto>> itemsPerPlacedOrder) {
        return content
                .stream()
                .map(placedOrder -> map(placedOrder, itemsPerPlacedOrder.get(placedOrder.getId())))
                .collect(Collectors.toList());
    }

    private OrderDto map(Order order, List<OrderItemDto> itemsPerPlacedOrder) {
        OrderDto orderDto = mapCommon(order);
        orderDto.getOrderItemDtos().addAll(itemsPerPlacedOrder);
        return orderDto;
    }
}
