package ch.teko.currencytrader;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.teko.currencytrader.exchange.CurrencyApiExchange;
import ch.teko.currencytrader.exchange.Exchange;
import ch.teko.currencytrader.exchange.IExchange;
import ch.teko.currencytrader.models.Trade;
import ch.teko.currencytrader.storages.IStorageService;
import ch.teko.currencytrader.storages.StorageFirebase;
import ch.teko.currencytrader.storages.StorageMock;

public class Controller {
    public static IStorageService<Trade> storage = getFirebaseFirestore();
    //public static IExchange exchange = new Exchange();
    public static OrderList CurrentListReference;
    public static void setOrderListReference(OrderList _orderList)
    {
        CurrentListReference = _orderList;
    }
    public static void updateListActivity()
    {
        CurrentListReference.RecreateArrayadapter();
    }
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

    private static IStorageService getFirebaseFirestore(){
        StorageFirebase firebaseStorage = new StorageFirebase();
        return firebaseStorage;
    }
}
