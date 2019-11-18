package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrderItem;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.PlacedOrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class PlacedOrderItemService {

    @Autowired
    private PlacedOrderItemRepository placedOrderItemRepository;

    private static final Logger logger = LoggerFactory.getLogger(PlacedOrderItemService.class);

    public List<PlacedOrderItem> save(Map<Long, Integer> quantityPerProduct, List<Product> products, PlacedOrder placedOrder) {
        logger.info("Saving information about the items in the order and their quantity!");
        List<PlacedOrderItem> listToSave = new ArrayList<>();
        products.forEach(product -> listToSave.add(new PlacedOrderItem(product, placedOrder, quantityPerProduct.get(product.getId()))));
        logger.debug("Saving {}!", listToSave);
        placedOrderItemRepository.saveAll(listToSave);
        return listToSave;
    }

    public Map<Long, List<OrderItemDto>> findOrderProductsPerPlacedOrder(List<PlacedOrder> content) {
        logger.info("Fetching data about the products in orders!");
        List<PlacedOrderItem> ordersItems = placedOrderItemRepository.findByPlacedOrderIn(content);

        return ordersItems
                .stream()
                .collect(groupingBy(placedOrderProduct -> placedOrderProduct.getPlacedOrder().getId(), mapping(placedOrderProduct -> new OrderItemDto(placedOrderProduct.getProduct().getId(), placedOrderProduct.getQuantity()), toList())));
    }
}
