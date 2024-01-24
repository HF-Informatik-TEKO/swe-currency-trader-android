package ch.teko.currencytrader.storages;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import ch.teko.currencytrader.models.Currency;
import ch.teko.currencytrader.models.Money;
import ch.teko.currencytrader.models.Trade;

public class StorageMock implements IStorageService<Trade>, Parcelable {
    private ArrayList<Trade> storage = new ArrayList<>();

    @Override
    public void addItem(Trade item) {
        storage.add(item);
    }

    @Override
    public void removeItem(int pos) {
        storage.remove(pos);
    }

    @Override
    public void updateItem(Trade old, Trade update) {
        storage.remove(old);
        storage.add(update);
    }

    @Override
    public Trade getItem(int pos) {
        return storage.get(pos);
    }

    @Override
    public ArrayList<Trade> getAllItems() {
        return storage;
    }

    public void addMockData() {
        storage.add(new Trade("Minnie Mouse", new Money(100, Currency.CHF), new Money(100, Currency.USD)));
        storage.add(new Trade("Chuck Norris", new Money(9_999, Currency.USD), new Money(8_888.8, Currency.EUR)));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}
