package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.OrderItem;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> save(Map<Long, Integer> quantityPerProduct, List<Product> products, Order order) {
        logger.info("Saving information about the items in the order and their quantity!");
        List<OrderItem> listToSave = products
                .stream()
                .map(product -> new OrderItem(product, order, quantityPerProduct.get(product.getId())))
                .collect(toList());
        logger.debug("Saving {}!", listToSave);
        orderItemRepository.saveAll(listToSave);
        return listToSave;
    }

    public Map<Long, List<OrderItemDto>> findItemsPerOrder(List<Order> orders) {
        logger.info("Fetching data about the products in orders!");
        List<OrderItem> ordersItems = orderItemRepository.findByOrderIn(orders);

        return ordersItems
                .stream()
                .collect(groupingBy(placedOrderProduct -> placedOrderProduct.getOrder().getId(),
                        mapping(placedOrderProduct -> new OrderItemDto(placedOrderProduct.getProduct().getId(), placedOrderProduct.getQuantity()), toList())));
    }
}