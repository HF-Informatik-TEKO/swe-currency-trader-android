package ch.teko.currencytrader.exchange;

import java.util.HashMap;

import ch.teko.currencytrader.models.Trade;

public class Exchange implements IExchange{
    HashMap<String, Double> exchange = getDefaultExchangeValues();


    @Override
    public double calculateTarget(Trade trade) {
        String curr = trade.source.currency.toString() + trade.target.currency.toString();
        double rate = exchange.get(curr);
        return Math.floor(trade.source.amount * rate * 100) / 100;
    }

    @Override
    public double calculateSource(Trade trade) {
        String curr = trade.target.currency.toString() + trade.source.currency.toString();
        double rate = exchange.get(curr);
        return Math.floor(trade.target.amount * rate * 100) / 100;
    }

    private HashMap<String, Double> getDefaultExchangeValues() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("CHFUSD", 1.1585);
        map.put("USDCHF", 0.8628);
        map.put("CHFEUR", 1.0631);
        map.put("EURCHF", 0.9400);
        map.put("USDEUR", 0.9248);
        map.put("EURUSD", 1.0923);

        return map;
    }

}
