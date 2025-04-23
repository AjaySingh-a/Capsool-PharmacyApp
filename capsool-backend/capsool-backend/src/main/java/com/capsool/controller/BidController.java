package com.capsool.controller;

import com.capsool.dto.PlaceBidRequest;
import com.capsool.dto.BidResponseDTO;
import com.capsool.security.service.BidService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    // ✅ 1. Only Pharmacy Owner Can Place a Bid
    @PostMapping("/place")
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    public ResponseEntity<?> placeBid(@RequestBody @Valid PlaceBidRequest request) {
        try {
            BidResponseDTO bidResponse = bidService.placeBid(request);
            return ResponseEntity.ok(bidResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Failed to place bid: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }

    // ✅ 2. Get all bids for an order (Response in DTO Format)
    @GetMapping("/{orderId}")
    public ResponseEntity<List<BidResponseDTO>> getBidsForOrder(@PathVariable Long orderId) {
        List<BidResponseDTO> bidResponses = bidService.getBidsForOrder(orderId);
        if (bidResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bidResponses);
    }

    // ✅ 3. Only Pharmacy Owners Can Finalize the Lowest Bid
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    @PostMapping("/finalize/{orderId}")
    public ResponseEntity<?> finalizeLowestBid(@PathVariable Long orderId) {
        try {
            BidResponseDTO winningBid = bidService.finalizeLowestBid(orderId);
            return ResponseEntity.ok(winningBid);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Failed to finalize bid: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }
}
