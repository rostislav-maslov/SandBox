package tech.maslov.sandbox.order.models;

public class PaymentInfo {
    public enum TYPE {
        ONLINE, CASH, CARD_OFFLINE
    }

    public enum STATUS{
        NOT_PAYED, PAYED, REFUND
    }

    private TYPE type;
    private STATUS status = STATUS.NOT_PAYED;

    private Double changeWith = 0.;

    private Double basketCost = 0.;
    private Double deliveryCost = 0.;
    private Double discountCost = 0.;

    private Double total = 0.;

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Double getBasketCost() {
        return basketCost;
    }

    public void setBasketCost(Double basketCost) {
        this.basketCost = basketCost;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Double getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(Double discountCost) {
        this.discountCost = discountCost;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getChangeWith() {
        return changeWith;
    }

    public void setChangeWith(Double changeWith) {
        this.changeWith = changeWith;
    }
}
