package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.Application;
import hr.ivlahek.sample.store.persistence.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class WebApiTest extends RepositoryTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;
}
