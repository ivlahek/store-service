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

import java.math.BigDecimal;
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
                .map(product -> new OrderItem( order, product.getId(), quantityPerProduct.get(product.getId()), product.getPrice()))
                .collect(toList());
        logger.debug("Saving {}!", listToSave);
        orderItemRepository.saveAll(listToSave);
        return listToSave;
    }

    public  List<OrderItem> findOrderItems(List<Order> orders) {
        logger.info("Fetching data about the products in orders!");
        return orderItemRepository.findByOrderIn(orders);
    }

}
