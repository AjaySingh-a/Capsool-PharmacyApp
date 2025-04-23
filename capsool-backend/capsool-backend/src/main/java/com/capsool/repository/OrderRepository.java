package com.capsool.repository;

import com.capsool.model.Order;
import com.capsool.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Fetch orders by status (Already present)
    List<Order> findByStatus(OrderStatus status);

    // ✅ Fetch all orders won by a specific pharmacy (Already present)
    List<Order> findByWinnerPharmacyId(Long winnerPharmacyId);

    // ✅ Fetch all pending orders (to be shown in pharmacy bidding dashboard)
    List<Order> findByStatusOrderByOrderTimeAsc(OrderStatus status);

    // ✅ Fetch a specific order by ID (to ensure an order exists before bidding)
    Optional<Order> findById(Long orderId);
}

