package hr.ivlahek.sample.store.service.util;

import hr.ivlahek.sample.store.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PriceCalculator {

    public double calculate(Map<Long, Integer> quantityMapPerProduct, List<Product> products) {
        Function<Product, BigDecimal> function = value -> BigDecimal.valueOf(value.getPrice()).multiply(BigDecimal.valueOf(quantityMapPerProduct.get(value.getId())));
        BigDecimal reduce = products.stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce.doubleValue();
    }
}