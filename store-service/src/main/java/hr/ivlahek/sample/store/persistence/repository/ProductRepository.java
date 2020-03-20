package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    Optional<Product> findBySku(String description);
}
