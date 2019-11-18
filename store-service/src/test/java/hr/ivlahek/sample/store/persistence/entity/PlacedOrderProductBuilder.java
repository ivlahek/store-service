package hr.ivlahek.sample.store.persistence.entity;

public final class PlacedOrderProductBuilder {
    private PlacedOrder placedOrder;
    private Product product;
    private int quantity = 1;

    private PlacedOrderProductBuilder() {
    }

    public static PlacedOrderProductBuilder aPlacedOrderItem() {
        return new PlacedOrderProductBuilder();
    }

    public PlacedOrderProductBuilder withPlacedOrder(PlacedOrder placedOrder) {
        this.placedOrder = placedOrder;
        return this;
    }

    public PlacedOrderProductBuilder withProduct(Product product) {
        this.product = product;
        return this;
    }

    public PlacedOrderProductBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public PlacedOrderItem build() {
        PlacedOrderItem placedOrderItem = new PlacedOrderItem();
        placedOrderItem.setPlacedOrder(placedOrder);
        placedOrderItem.setProduct(product);
        placedOrderItem.setQuantity(quantity);
        return placedOrderItem;
    }
}
