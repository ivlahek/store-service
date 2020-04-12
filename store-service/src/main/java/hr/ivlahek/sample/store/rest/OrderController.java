package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.rest.definition.OrderApiDefinition;
import hr.ivlahek.sample.store.service.OrderItemService;
import hr.ivlahek.sample.store.service.OrderService;
import hr.ivlahek.sample.store.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
        return orderService.placeOrder(createOrderDto);
    }

    public PageResponseDto<OrderDto> getOrders(Pageable pageable, Date dateFrom, Date dateTo) {
        logger.info("Get order between dates {} and {}. Page {} ", dateFrom, dateTo, pageable);
        return orderService.getOrders(pageable, dateFrom, dateTo);

    }
}
