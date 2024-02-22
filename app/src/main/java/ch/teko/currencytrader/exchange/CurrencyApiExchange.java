package ch.teko.currencytrader.exchange;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import ch.teko.currencytrader.models.Currency;
import ch.teko.currencytrader.models.Trade;

public class CurrencyApiExchange implements IExchange {

    private String[] CURRENCIES = { "USD", "CHF", "EUR" };
    private HashMap<Currency, Double> exchange = getDefaultExchangeValues();

    public CurrencyApiExchange() {
        loadDataFromApi();
    }

    @Override
    public double calculateTarget(Trade trade) {
        return trade.source.amount * getRate(trade.source.currency, trade.target.currency);
    }

    @Override
    public double calculateSource(Trade trade) {
        return trade.target.amount * getRate(trade.target.currency, trade.source.currency);
    }

    private double getRate(Currency from, Currency to) {
        return exchange.get(from) * exchange.get(to);
    }

    private HashMap<Currency, Double> getDefaultExchangeValues() {
        HashMap<Currency, Double> map = new HashMap<>();
        map.put(Currency.USD, 1.0000);
        map.put(Currency.EUR, 0.9252);
        map.put(Currency.CHF, 0.8815);

        return map;
    }

    private void loadDataFromApi() {
        CurrencyApiRequestTask task = new CurrencyApiRequestTask();
        try {
            HashMap<String, Double> res = task.execute(CURRENCIES).get();

            if (res.size() < 2) {
                return;
            }
            exchange.clear();
            for (HashMap.Entry<String, Double> entry : res.entrySet()) {
                Double value = entry.getValue();
                Currency currency = Enum.valueOf(Currency.class, entry.getKey());

                exchange.put(currency, value);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
