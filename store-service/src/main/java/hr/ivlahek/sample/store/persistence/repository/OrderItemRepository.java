package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderIn(List<Order> orderIds);
}
