package com.FreteGen.FreteGen.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
@Data
@Entity
@Table(name = "shipping_quotes")
public class ShippingData {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(name = "product_id", nullable = false)
        private UUID productId;

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @Column(nullable = false, length = 100)
        private String carrier;

        @Column(nullable = false, length = 100)
        private String service;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(name = "discount_price", precision = 10, scale = 2)
        private BigDecimal discountPrice;

        @Column(name = "delivery_time_days")
        private Integer deliveryTimeDays;

        @Column(name = "origin_zip", length = 10)
        private String originZip;

        @Column(name = "destination_zip", length = 10)
        private String destinationZip;

        @CreationTimestamp
        @Column(name = "quote_date", updatable = false)
        private OffsetDateTime quoteDate;

        @Lob
        @Column(name = "raw_response")
        private String rawResponse;


    }


