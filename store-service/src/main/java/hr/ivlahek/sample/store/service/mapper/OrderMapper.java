package hr.ivlahek.sample.store.service.mapper;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.persistence.entity.Order;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
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
        OrderDto orderDto = new OrderDto();
        orderDto.setDateCreated(order.getDateCreated());
        orderDto.setEmail(order.getEmail());
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(BigDecimal.valueOf(order.getTotalPrice()));
        return orderDto;
    }

    public List<OrderDto> map(List<Order> content) {
        return content.stream().map(this::map).collect(Collectors.toList());
    }
}
