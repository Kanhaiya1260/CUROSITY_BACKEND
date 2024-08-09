import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Order[] ordersArray = {
            new Order(1L, 10),
            new Order(2L, 20),
            new Order(1L, 15),
            new Order(3L, 10),
            new Order(2L, 5)
        };

        List<Order> result = Arrays.stream(ordersArray)
            .collect(Collectors.groupingBy(
                Order::getProductId,  // Group by productId
                Collectors.summingInt(Order::getQuantity)  // Sum quantities for each productId
            ))
            .entrySet().stream()
            .map(e -> new Order(e.getKey(), e.getValue()))  // Create new Order objects
            .collect(Collectors.toList());

        result.forEach(System.out::println);  // Print the result
    }
}