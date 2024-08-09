
import java.util.*;
import java.util.stream.Collectors;

class Order {
    private Long productId;
    private int quantity;

    // Constructors, getters, and setters

    public Order(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
