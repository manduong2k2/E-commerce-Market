package com.e_com.CartService.Cart.Domain.Constants;

public enum CartStatusEnum {
    ACTIVE,
    CHECKED_OUT,
    ABANDONED,
    EXPIRED;

    public static boolean isValid(String value) {
        try {
            CartStatusEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
