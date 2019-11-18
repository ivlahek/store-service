package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Long> {
    Page<PlacedOrder> findByDateCreatedBetween(Date dateFrom, Date dateTo, Pageable pageable);
}
