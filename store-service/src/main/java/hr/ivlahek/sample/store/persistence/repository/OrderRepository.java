package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByDateCreatedBetween(Date dateFrom, Date dateTo, Pageable pageable);
}
