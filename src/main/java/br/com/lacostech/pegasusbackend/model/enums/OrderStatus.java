package br.com.lacostech.pegasusbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    PAID(1, "Paid"),
    SHIPPED(2, "Shipped"),
    DELIVERED(3, "Delivered"),
    CANCELED(4, "Canceled");

    private final Integer code;
    private final String name;

}
