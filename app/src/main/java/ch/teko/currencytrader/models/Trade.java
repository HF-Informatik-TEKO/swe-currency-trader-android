package ch.teko.currencytrader.models;

import java.util.UUID;

public class Trade {
    public String id;
    public String customer;
    public Money source;
    public Money target;

    public Trade(String customer, Money source, Money target) {
        this.id = generateUniqueId();
        this.customer = customer;
        this.source = source;
        this.target = target;
     }
    public Trade()
    {

    }

    @Override
    public String toString() {
        return customer + " | " +
            source.amount + " " + source.currency + ", " +
            target.amount + " " + target.currency;
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
