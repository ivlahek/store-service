package hr.ivlahek.sample.store.persistence;

import hr.ivlahek.IntegrationTest;
import hr.ivlahek.sample.store.Application;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import hr.ivlahek.sample.store.persistence.repository.OrderItemRepository;
import hr.ivlahek.sample.store.persistence.repository.OrderRepository;
import hr.ivlahek.sample.store.persistence.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
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
    protected OrderRepository orderRepository;
    @Autowired
    protected OrderItemRepository orderItemRepository;

    @Before
    public void createDeletedProduct() throws Exception {
        productRepository.save(ProductBuilder.aDeletedProduct().build());
    }

    @After
    public void tearDown() {
        orderItemRepository.deleteAll();
        productRepository.deleteAllSoft();
        orderRepository.deleteAll();

    }
}
