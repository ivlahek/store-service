package hr.ivlahek.sample.store.service.util;

import hr.ivlahek.sample.store.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PriceCalculator {

    public double calculate(Map<Long, Integer> quantityMapPerProduct, List<Product> products) {
        BigDecimal reduce = products
                .stream()
                .map(product -> map(product.getPrice(), quantityMapPerProduct.get(product.getId())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce.doubleValue();
    }

    private BigDecimal map(double product, Integer quantity) {
        return BigDecimal
                .valueOf(product)
                .multiply(BigDecimal.valueOf(quantity));
    }
}