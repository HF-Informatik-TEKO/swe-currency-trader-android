package ch.teko.currencytrader;

import java.util.List;
import java.util.stream.Collectors;

import ch.teko.currencytrader.exchange.CurrencyApiExchange;
import ch.teko.currencytrader.exchange.Exchange;
import ch.teko.currencytrader.exchange.IExchange;
import ch.teko.currencytrader.models.Trade;
import ch.teko.currencytrader.storages.IStorageService;
import ch.teko.currencytrader.storages.StorageMock;

public class Controller {
    public static IStorageService<Trade> storage = getMockedStorage();
    public static IExchange exchange = new CurrencyApiExchange();


    public static List<String> storageToList() {
        return storage.getAllItems().stream()
            .map(e -> e.toString())
            .collect(Collectors.toList());
    }

    private static IStorageService getMockedStorage() {
        StorageMock mock = new StorageMock();
        mock.addMockData();
        return mock;
    }
}
