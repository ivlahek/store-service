package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.rest.definition.OrderApiDefinition;
import hr.ivlahek.sample.store.service.OrderMapper;
import hr.ivlahek.sample.store.service.OrderService;
import hr.ivlahek.sample.store.service.PlacedOrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
public class OrderController implements OrderApiDefinition {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PlacedOrderItemService placedOrderItemService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public OrderDto placeOrder(CreateOrderDto createOrderDto) {
        logger.info("Place order api called {}", createOrderDto);
        return new OrderMapper().map(orderService.placeOrder(createOrderDto));
    }

    public PageResponseDto<OrderDto> getOrders(Pageable pageable, Date dateFrom, Date dateTo) {
        logger.info("Get order between dates {} and {}. Page {} ", dateFrom, dateTo, pageable);
        Page<PlacedOrder> page = orderService.getOrders(pageable, dateFrom, dateTo);
        Map<Long, List<OrderItemDto>> itemsPerPlacedOrder = placedOrderItemService.findOrderProductsPerPlacedOrder(page.getContent());
        return PageResponseDto.PageResponseDtoBuilder.aPageResponseDto()
                .withContent(new OrderMapper().map(page.getContent(), itemsPerPlacedOrder))
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
}
