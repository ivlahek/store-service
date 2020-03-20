package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.OrderItem;
import hr.ivlahek.sample.store.rest.definition.OrderApiDefinition;
import hr.ivlahek.sample.store.service.OrderItemService;
import hr.ivlahek.sample.store.service.OrderService;
import hr.ivlahek.sample.store.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RestController
@Transactional
public class OrderController implements OrderApiDefinition {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    public OrderDto placeOrder(CreateOrderDto createOrderDto) {
        logger.info("Place order called {}", createOrderDto);
        return new OrderMapper().map(orderService.placeOrder(createOrderDto));
    }

    public PageResponseDto<OrderDto> getOrders(Pageable pageable, Date dateFrom, Date dateTo) {
        logger.info("Get order between dates {} and {}. Page {} ", dateFrom, dateTo, pageable);
        Page<Order> page = orderService.getOrders(pageable, dateFrom, dateTo);
        List<OrderItem> orderItemList = orderItemService.findOrderItems(page.getContent());
        Map<Long, List<OrderItemDto>> itemsPerOrder = groupItemsPerOrder(orderItemList);
        return PageResponseDto.PageResponseDtoBuilder.aPageResponseDto()
                .withContent(new OrderMapper().map(page.getContent(), itemsPerOrder))
                .withFirst(page.isFirst())
                .withLast(page.isLast())
                .withNextPage(page.hasNext())
                .withSize(page.getSize())
                .withTotalElements(page.getTotalElements())
                .withTotalPages(page.getTotalPages())
                .withPreviousPage(page.hasPrevious())
                .withPreviousPage(page.hasContent())
                .withNumber(page.getNumber())
                .withNumberOfElements(page.getNumberOfElements())
                .build();

    }

    private Map<Long, List<OrderItemDto>> groupItemsPerOrder(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .collect(groupingBy(orderItem -> orderItem.getOrder().getId(),
                        mapping(orderItem -> new OrderItemDto(orderItem.getProductId(),
                                orderItem.getQuantity(),
                                BigDecimal.valueOf(orderItem.getProductPrice())), toList())));
    }
}
