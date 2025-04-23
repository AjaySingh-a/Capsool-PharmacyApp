package com.capsool.controller;

import com.capsool.model.Order;
import com.capsool.security.service.OrderService;
import com.capsool.security.service.BiddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final BiddingService biddingService;

    public OrderController(OrderService orderService, BiddingService biddingService) {
        this.orderService = orderService;
        this.biddingService = biddingService;
    }

    // ✅ 1. Only Pharmacy Owners Can Fetch Pending Orders
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    // ✅ 2. Only Pharmacy Owners Can Finalize Bidding
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    @PostMapping("/{orderId}/finalize")
    public ResponseEntity<String> finalizeBidding(@PathVariable Long orderId) {
        try {
            biddingService.finalizeBidding(orderId);
            return ResponseEntity.ok("Bidding finalized, winner selected!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error finalizing bidding: " + e.getMessage());
        }
    }
    // ✅ Allow customer to cancel their order (only if still pending)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok("Order has been canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Order cannot be canceled at this stage.");
        }
    }


    // ✅ 3. Fetch all confirmed orders (For All Users)
    @GetMapping("/confirmed")
    public ResponseEntity<List<Order>> getConfirmedOrders() {
        return ResponseEntity.ok(orderService.getConfirmedOrders());
    }

    // ✅ 4. Only Pharmacy Owners Can Fetch Their Own Orders
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<List<Order>> getOrdersForPharmacy(@PathVariable Long pharmacyId) {
        return ResponseEntity.ok(orderService.getPharmacyOrders(pharmacyId));
    }
}
