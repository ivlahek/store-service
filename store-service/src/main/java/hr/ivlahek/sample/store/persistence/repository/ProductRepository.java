package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    Optional<Product> findBySku(String description);

    @Query(value = "delete from product", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllSoft();
}
