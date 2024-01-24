package ch.teko.currencytrader.exchange;

import ch.teko.currencytrader.models.Trade;

public interface IExchange {
    public double calculateTarget(Trade trade);
    public double calculateSource(Trade trade);
}
