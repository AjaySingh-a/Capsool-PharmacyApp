package com.capsool.model;

public enum OrderStatus {
    PENDING, // Waiting for bids
    CONFIRMED, // Bid finalized
    READY_TO_PICKUP, // Pharmacy has prepared the order
    DELIVERED, // Order successfully delivered
    CANCELED,  // ✅ Added CANCELED status
    FAILED    // ✅ No pharmacy placed a bid
}
