package com.capsool.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false) // ✅ Customer name required
    private String customerName;

    @ElementCollection
    @CollectionTable(name = "order_medicines", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "medicine_name")
    @Column(name = "quantity")
    private Map<String, Integer> medicines;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "winner_pharmacy_id", nullable = true)
    private User winnerPharmacy;

    @PrePersist
    protected void onCreate() {
        this.orderTime = LocalDateTime.now();
    }

    public void setWinnerPharmacy(User pharmacy) {
        this.winnerPharmacy = pharmacy;
    }
}
