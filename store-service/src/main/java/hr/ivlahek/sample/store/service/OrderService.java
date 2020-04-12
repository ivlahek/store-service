package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.CreateOrderItemDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.exception.InternalServerErrorException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.OrderRepository;
import hr.ivlahek.sample.store.service.internal.InternalProductService;
import hr.ivlahek.sample.store.service.mapper.OrderMapper;
import hr.ivlahek.sample.store.service.util.PriceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InternalProductService productService;
    @Autowired
    private OrderItemService orderItemService;

    public OrderDto placeOrder(CreateOrderDto createOrderDto) {
        logger.info("Place order method called {}", createOrderDto);
        List<Long> productIds = createOrderDto.getOrderItemDtos()
                .stream()
                .map(CreateOrderItemDto::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productService.findByIds(productIds);
        logger.info("Founds #{} products", products.size());
        if (products.isEmpty()) {
            throw new InternalServerErrorException(ExceptionMessage.PRODUCT_CART_EMPTY);
        }
        Map<Long, Integer> quantityMapPerProduct = createOrderDto.getOrderItemDtos()
                .stream()
                .collect(Collectors.toMap(CreateOrderItemDto::getProductId, CreateOrderItemDto::getQuantity, Integer::sum));
        double price = new PriceCalculator().calculate(quantityMapPerProduct, products);
        logger.info("Order price is {}", price);

        Order order = orderRepository.save(new OrderMapper().map(createOrderDto, price));
        logger.info("Order saved {}!", order);
        order.getOrderItems().addAll(orderItemService.save(quantityMapPerProduct, products, order));
        logger.info("Details about the order saved!");
        return new OrderMapper().map(order);
    }

    public PageResponseDto<OrderDto> getOrders(Pageable pageable, Date dateFrom, Date dateTo) {
        logger.info("Fetching orders!");
        Page<Order> page = orderRepository.findByDateCreatedBetween(dateFrom, dateTo, pageable);

        return PageResponseDto.PageResponseDtoBuilder.aPageResponseDto()
                .withContent(new OrderMapper().map(page.getContent()))
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
