package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacedOrderItemRepository extends JpaRepository<PlacedOrderItem, Long> {
    List<PlacedOrderItem> findByPlacedOrderIn(List<PlacedOrder> placedOrderIds);
}
