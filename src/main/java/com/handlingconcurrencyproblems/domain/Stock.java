package com.handlingconcurrencyproblems.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    @Version
    private Long version;

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("stock quantity must not be negative");
        }

        this.quantity -= quantity;
    }
}
