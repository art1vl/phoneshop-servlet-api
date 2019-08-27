package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Order extends Cart {
    private Long id;
    private String secureId;
    private String name;
    private String surname;
    private String phone;
    private DeliveryMode deliveryMode;
    private Date deliveryDate;
    private BigDecimal deliveryCost;
    private String address;
    private PaymentMethod paymentMethod;
    private BigDecimal orderCost;

    public Order() {}

    @Override
    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(super.getCartItems());
    }

    public Order(List<CartItem> cartItems, BigDecimal subCost, Long totalQuantity, Long id, String secureId, String name,
                 String surname, String phone, DeliveryMode deliveryMode, Date deliveryDate, BigDecimal deliveryCost,
                 String address, PaymentMethod paymentMethod, BigDecimal orderCost) {
        super(cartItems, subCost, totalQuantity);
        this.id = id;
        this.secureId = secureId;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.deliveryMode = deliveryMode;
        this.deliveryDate = deliveryDate;
        this.deliveryCost = deliveryCost;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.orderCost = orderCost;
    }

    public Order cloneOrder() {
        return new Order(super.getCartItems(), super.getSubCost(), super.getTotalQuantity(), this.id, this.secureId, this.name,
                this.surname, this.phone, this.deliveryMode, this.deliveryDate, this.deliveryCost, this.address,
                this.paymentMethod, this.orderCost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public String getAddress() {
        return address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
