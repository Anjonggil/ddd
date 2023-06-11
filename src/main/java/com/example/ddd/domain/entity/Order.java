package com.example.ddd.domain.entity;

import com.example.ddd.domain.vo.OrderState;

import java.util.List;

public class Order {

    private String orderNumber;
    private List<OrderLine> orderLines;
    private Money totalAmounts;

    private ShippingInfo shippingInfo;

    private OrderState state;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null){
            throw new IllegalArgumentException("no Orders!");
        }
        this.shippingInfo = shippingInfo;
    }

    public Order(List<OrderLine> orderLines){
        setOrderLines(orderLines);
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOnoOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOnoOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()){
            throw new IllegalArgumentException("no Orders!");
        }
    }

    private void calculateTotalAmounts(){
        for (OrderLine orderLine : orderLines) {
            Money amounts = orderLine.getAmounts();
            this.totalAmounts.add(amounts);
        }
    }

    public void changeShippingInfo(ShippingInfo shippingInfo){
        verifyNotYetShipped();
        setShippingInfo(shippingInfo);
    }

    public void cancel(){
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    private void verifyNotYetShipped(){
        if (state != OrderState.PAYMENT_WAITING &&
                state != OrderState.PREPARING){
            throw new IllegalArgumentException("already shipped");
        }
    }
}
