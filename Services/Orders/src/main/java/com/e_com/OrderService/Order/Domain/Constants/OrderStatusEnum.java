package com.e_com.OrderService.Order.Domain.Constants;

public enum OrderStatusEnum {
    PENDING,
    PAID,
    PROCESSING,
    SHIPPING,
    COMPLETED,
    CANCELLED,
    EXPIRED;

    public static boolean isValid(String value) {

        try {

            OrderStatusEnum.valueOf(value);

            return true;

        } catch (IllegalArgumentException e) {

            return false;
        }
    }
}
