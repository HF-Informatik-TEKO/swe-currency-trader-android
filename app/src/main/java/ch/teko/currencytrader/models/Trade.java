package ch.teko.currencytrader.models;

public class Trade {
    public String customer;
    public Money source;
    public Money target;

    public Trade(String customer, Money source, Money target) {
        this.customer = customer;
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return customer + " | " +
            source.amount + " " + source.currency + ", " +
            target.amount + " " + target.currency;
    }
}
