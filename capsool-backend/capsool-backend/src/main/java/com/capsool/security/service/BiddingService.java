package com.capsool.security.service;

import com.capsool.model.Bid;
import com.capsool.model.Order;
import com.capsool.model.OrderStatus;
import com.capsool.model.User;
import com.capsool.repository.BidRepository;
import com.capsool.repository.OrderRepository;
import com.capsool.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BiddingService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void finalizeBidding(Long orderId) {
        List<Bid> bids = bidRepository.findByOrderIdOrderByBidAmountAsc(orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        if (bids.isEmpty()) {
            order.setStatus(OrderStatus.FAILED); // 🔥 No bids → Order is FAILED
            orderRepository.save(order);
            return; // ✅ Exit early, no winner
        }

        Bid winningBid = bids.get(0);
        winningBid.setWinner(true);
        bidRepository.save(winningBid);

        // ✅ Fetch the winning pharmacy (Ensuring it's a valid User)
        User winnerPharmacy = userRepository.findById(winningBid.getPharmacy().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pharmacy user not found with ID: " + winningBid.getPharmacy().getId()));

        order.setStatus(OrderStatus.CONFIRMED);
        order.setWinnerPharmacy(winnerPharmacy); // ✅ Proper JPA relation
        orderRepository.save(order);
    }
}
