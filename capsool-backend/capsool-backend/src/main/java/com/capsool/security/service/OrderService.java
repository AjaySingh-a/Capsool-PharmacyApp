package com.capsool.security.service;

import com.capsool.model.Order;
import com.capsool.model.OrderStatus;
import com.capsool.model.User;
import com.capsool.repository.OrderRepository;
import com.capsool.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private BidService bidService; // 🔥 Setter Injection ke liye

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // ✅ Setter Injection to break circular dependency
    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }

    // ✅ 1. Create New Order
    public Order placeOrder(String customerName, Map<String, Integer> medicines) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setMedicines(medicines);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    // ✅ 2. Get All Pending Orders
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(OrderStatus.PENDING);
    }

    // ✅ 3. Cancel Order (Only if PENDING)
    public boolean cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.CANCELED);
                orderRepository.save(order);
                return true;
            }
        }
        return false;
    }

    // ✅ 4. Assign Order to Lowest Bidder
    public void assignOrderToLowestBid(Long orderId, Long pharmacyId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Cannot assign a canceled order.");
        }

        User pharmacy = userRepository.findById(pharmacyId)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

        order.setWinnerPharmacy(pharmacy);
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    // ✅ 5. Update Order Status
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Cannot update a canceled order.");
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    // ✅ 6. Fetch All Confirmed Orders
    public List<Order> getConfirmedOrders() {
        return orderRepository.findByStatus(OrderStatus.CONFIRMED);
    }

    // ✅ 7. Fetch Orders for a Specific Pharmacy
    public List<Order> getPharmacyOrders(Long pharmacyId) {
        return orderRepository.findByWinnerPharmacyId(pharmacyId);
    }

    // ✅ 8. Fetch Order By ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
