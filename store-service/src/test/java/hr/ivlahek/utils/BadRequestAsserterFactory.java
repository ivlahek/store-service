package hr.ivlahek.utils;

import hr.ivlahek.sample.store.client.order.OrderResourceEndpoints;
import hr.ivlahek.sample.store.client.product.ProductResourceEndpoints;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class BadRequestAsserterFactory {

    public static BadRequestAsserter createForProductResource(TestRestTemplate restTemplate) {
        BadRequestAsserter badRequestAsserter = new BadRequestAsserter(restTemplate);
        badRequestAsserter.endPoint = ProductResourceEndpoints.PRODUCTS;
        return badRequestAsserter;
    }

    public static BadRequestAsserter createForProductIdResource(TestRestTemplate restTemplate) {
        BadRequestAsserter badRequestAsserter = new BadRequestAsserter(restTemplate);
        badRequestAsserter.endPoint = ProductResourceEndpoints.PRODUCTS_BY_ID;
        return badRequestAsserter;
    }

    public static BadRequestAsserter createForOrderResource(TestRestTemplate testRestTemplate) {
        BadRequestAsserter badRequestAsserter = new BadRequestAsserter(testRestTemplate);
        badRequestAsserter.endPoint = OrderResourceEndpoints.ORDERS;
        return badRequestAsserter;
    }
}
