package hr.ivlahek.sample.store.client.product;

import java.time.Instant;
import java.util.Date;

public class EntityDefaults {
    public static class Product1Defaults {
        public static String NAME = "name";
        public static double PRICE = 2.7;
        public static String SKU = "sku-1";
        public static Date DATE_CREATED = Date.from(Instant.now().minusSeconds(10));
        public static Date DATE_UPDATED = Date.from(Instant.now());
    }

    public static class Product2Defaults {
        public static final Date DATE_UPDATED = Date.from(Instant.now().minusSeconds(30));
        public static final Date DATE_CREATED = Date.from(Instant.now().minusSeconds(20));
        public static final double PRICE = 4.3;
        public static String SKU = "sku-2";
        public static final String NAME = "name-2";
    }

    public static class Product3Defaults {
        public static final Date DATE_UPDATED = Date.from(Instant.now().minusSeconds(40));
        public static final Date DATE_CREATED = Date.from(Instant.now().minusSeconds(50));
        public static final double PRICE = 1.3;
        public static String SKU = "sku-3";
        public static final String NAME = "name-3";
    }

    public static class PlacedOrder1Defaults {
        public static final String EMAIL = "john.doe@gmail.com";
        public static final double TOTAL_PRICE = 10D;
        public static final Date DATE_CREATED = Date.from(Instant.now());
    }

    public static class PlacedOrder2Defaults {
        public static final String EMAIL = "lala.po@gmail.com";
        public static final double TOTAL_PRICE = 73D;
        public static final Date DATE_CREATED = Date.from(Instant.now().minusSeconds(100));
    }


}
