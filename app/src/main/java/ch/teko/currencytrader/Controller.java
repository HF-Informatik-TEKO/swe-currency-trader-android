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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Controller {
    public static IStorageService<Trade> storage = getFirebaseFirestore();
    public static IExchange exchange = new Exchange();
    public static OrderList CurrentListReference;
    public static void setOrderListReference(OrderList _orderList)
    {
        CurrentListReference = _orderList;
    }
    public static void updateListActivity()
    {
        CurrentListReference.RecreateArrayadapter();
    }
    //public static IExchange exchange = new CurrencyApiExchange();

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
