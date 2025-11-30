package com.FreteGen.FreteGen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightQuoteDTO {

        private String id;

        private Company company;

        private String name;

        private BigDecimal price;

        @JsonProperty("custom_price")
        private BigDecimal customPrice;

        private BigDecimal discount;

        @JsonProperty("delivery_time")
        private Integer deliveryTime;

        @Data
        public static class Company {
            private String name;
        }

    }




