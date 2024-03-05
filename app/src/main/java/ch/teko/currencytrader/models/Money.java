package ch.teko.currencytrader.models;

public class Money {
    public double amount;
    public Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money()
    {

    }
}
