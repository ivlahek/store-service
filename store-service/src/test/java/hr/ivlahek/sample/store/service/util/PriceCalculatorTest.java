package hr.ivlahek.sample.store.service.util;

import com.google.common.collect.Lists;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    public void should_calculate_price_if_order_item_list_contains_unknown_products() {
        Product product1 = ProductBuilder.aProduct1().withId(1L).build();
        Product product2 = ProductBuilder.aProduct2().withId(2L).build();

        List<Product> products = Lists.newArrayList(product1, product2);

        Map<Long, Integer> quantityMapPerProduct = new HashMap<>();
        quantityMapPerProduct.put(1L, 4);
        quantityMapPerProduct.put(2L, 1);

        //OPERATE
        double price = priceCalculator.calculate(quantityMapPerProduct, products);

        //CHECK
        assertThat(price).isEqualTo(15.1);
    }

}