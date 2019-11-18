package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.exception.InternalServerErrorException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.PlacedOrderRepository;
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
    @Autowired
    private PlacedOrderRepository placedOrderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PlacedOrderItemService placedOrderItemService;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public PlacedOrder placeOrder(CreateOrderDto createOrderDto) {
        logger.info("Place order method called {}", createOrderDto);
        List<Product> products = productService.findByIds(createOrderDto.getOrderItemDtos().stream().map(OrderItemDto::getProductId).collect(Collectors.toList()));
        logger.info("Founds #{} products", products.size());
        if (products.isEmpty()) {
            throw new InternalServerErrorException(ExceptionMessage.PRODUCT_CART_EMPTY);
        }
        Map<Long, Integer> quantityMapPerProduct = createOrderDto.getOrderItemDtos().stream().collect(Collectors.toMap(OrderItemDto::getProductId, OrderItemDto::getQuantity, Integer::sum));
        double price = new PriceCalculator().calculate(quantityMapPerProduct, products);
        logger.debug("Order price is {}", price);

        PlacedOrder placedOrder = placedOrderRepository.save(new OrderMapper().map(createOrderDto, price));
        logger.info("Order saved {}!", placedOrder);
        placedOrder.getPlacedOrderItems().addAll(placedOrderItemService.save(quantityMapPerProduct, products, placedOrder));
        logger.info("Details about the order saved!");
        return placedOrder;
    }

    public Page<PlacedOrder> getOrders(Pageable pageable, Date dateFrom, Date dateTo) {
        return placedOrderRepository.findByDateCreatedBetween(dateFrom, dateTo, pageable);
    }
}
