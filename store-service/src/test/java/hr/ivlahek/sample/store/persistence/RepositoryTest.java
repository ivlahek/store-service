package hr.ivlahek.sample.store.persistence;

import hr.ivlahek.IntegrationTest;
import hr.ivlahek.sample.store.Application;
import hr.ivlahek.sample.store.persistence.repository.PlacedOrderItemRepository;
import hr.ivlahek.sample.store.persistence.repository.PlacedOrderRepository;
import hr.ivlahek.sample.store.persistence.repository.ProductRepository;
import org.junit.After;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
@Category(value = IntegrationTest.class)
public abstract class RepositoryTest {

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected PlacedOrderRepository placedOrderRepository;
    @Autowired
    protected PlacedOrderItemRepository placedOrderItemRepository;

    @After
    public void tearDown() {
        placedOrderItemRepository.deleteAll();
        productRepository.deleteAll();
        placedOrderRepository.deleteAll();

    }
}
