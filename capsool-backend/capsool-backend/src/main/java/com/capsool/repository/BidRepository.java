package com.capsool.repository;

import com.capsool.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    // ✅ Fetch all bids for a specific order, sorted by bid amount (Lowest First)
    List<Bid> findByOrderIdOrderByBidAmountAsc(Long orderId);

    // ✅ Prevent Duplicate Bid (Check if pharmacy already placed bid on same order)
    Optional<Bid> findByOrderIdAndPharmacyId(Long orderId, Long pharmacyId);
}
