package com.capsool.security.service;

import com.capsool.dto.BidResponseDTO;
import com.capsool.dto.PlaceBidRequest;
import com.capsool.exception.ResourceNotFoundException;
import com.capsool.model.Bid;
import com.capsool.model.Order;
import com.capsool.model.OrderStatus;
import com.capsool.model.User;
import com.capsool.repository.BidRepository;
import com.capsool.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;  // ✅ Constructor Injection for clean dependency

    // ✅ Constructor Injection (Best Practice)
    public BidService(BidRepository bidRepository, UserRepository userRepository, OrderService orderService) {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    // ✅ 1. Place a Bid (Prevent Duplicate Bidding)
    public BidResponseDTO placeBid(PlaceBidRequest request) {
        Order order = orderService.getOrderById(request.getOrderId());

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Cannot place bid on a canceled order.");
        }

        User pharmacy = userRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        // ✅ Prevent Duplicate Bidding
        Optional<Bid> existingBid = bidRepository.findByOrderIdAndPharmacyId(
                request.getOrderId(), request.getPharmacyId());
        if (existingBid.isPresent()) {
            throw new IllegalStateException("You have already placed a bid for this order.");
        }

        // ✅ Place New Bid
        Bid bid = new Bid();
        bid.setOrder(order);
        bid.setPharmacy(pharmacy);
        bid.setBidAmount(request.getBidAmount());

        Bid savedBid = bidRepository.save(bid);
        return mapToBidResponseDTO(savedBid);
    }

    // ✅ 2. Get All Bids for an Order
    public List<BidResponseDTO> getBidsForOrder(Long orderId) {
        List<Bid> bids = bidRepository.findByOrderIdOrderByBidAmountAsc(orderId);
        return bids.stream().map(this::mapToBidResponseDTO).collect(Collectors.toList());
    }

    // ✅ 3. Finalize the Lowest Bid and Assign Order
    @Transactional
    public BidResponseDTO finalizeLowestBid(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Cannot finalize bids for a canceled order.");
        }

        List<Bid> bids = bidRepository.findByOrderIdOrderByBidAmountAsc(orderId);
        Optional<Bid> lowestBid = bids.stream().min((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()));

        if (lowestBid.isPresent()) {
            Bid winningBid = lowestBid.get();
            winningBid.setWinner(true);
            bidRepository.save(winningBid);

            // ✅ Automatically Change Order Status to CONFIRMED
            orderService.assignOrderToLowestBid(orderId, winningBid.getPharmacy().getId());
            orderService.updateOrderStatus(orderId, OrderStatus.CONFIRMED);

            // ✅ Return the Winner Bid Response
            return mapToBidResponseDTO(winningBid);
        } else {
            throw new IllegalStateException("No bids available to finalize.");
        }
    }

    // ✅ 4. Convert Bid to BidResponseDTO
    private BidResponseDTO mapToBidResponseDTO(Bid bid) {
        return new BidResponseDTO(
                bid.getId(),
                bid.getOrder().getId(),
                bid.getPharmacy().getId(),
                bid.getPharmacy().getUsername(),
                bid.getBidAmount(),
                bid.isWinner()
        );
    }
}
