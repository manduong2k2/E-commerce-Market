package com.e_com.OrderService.Order.Domain.Model;

import java.util.UUID;

import com.e_com.OrderService.Shared.Domain.Entity;

public class OrderItem extends Entity<UUID>{
    private String productVariantId;
    private Quantity quantity;
    private ProductSnapShot snapShot;

    public OrderItem() {
        super(null);
    }

    public OrderItem(UUID id, String productVariantId, int quantity) {
        super(id);
        this.productVariantId = productVariantId;
        this.quantity = new Quantity(quantity);
    }

    //Business methods

    public void plusOne() {
        this.quantity.setValue(this.quantity.getValue()+1);
    }

    public void minusOne() {
        this.quantity.setValue(this.quantity.getValue()-1);
    }

    public double getTotal() {
        return this.quantity.getValue() * this.snapShot.getPrice();
    }

    //Base methods
    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getProductVariantId() {
        return productVariantId;
    }
    
    public int getQuantity() {
        return quantity.getValue();
    }

    public void setQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }

    public ProductSnapShot getSnapShot() {
        return snapShot;
    }

    public void setSnapShot(ProductSnapShot snapShot) {
        this.snapShot = snapShot;
    }
}
