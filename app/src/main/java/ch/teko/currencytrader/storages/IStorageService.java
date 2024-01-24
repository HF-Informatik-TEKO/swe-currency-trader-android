package ch.teko.currencytrader.storages;

import java.util.ArrayList;

public interface IStorageService<T> {
    public void addItem(T item);
    public void removeItem(int pos);
    public void updateItem(T old, T update);
    public T getItem(int pos);
    public ArrayList<T> getAllItems();
}
