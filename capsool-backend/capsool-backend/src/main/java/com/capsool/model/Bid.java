package com.capsool.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // ✅ Proper relation instead of orderId

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private User pharmacy; // ✅ Assuming Pharmacy Owner is a User

    @Column(name = "bid_amount", nullable = false)
    private Double bidAmount;

    @Column(name = "is_winner", nullable = false)
    private boolean isWinner = false;

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }
}
