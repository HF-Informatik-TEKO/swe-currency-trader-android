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
    public static IExchange exchange = new CurrencyApiExchange();
    //public static FirebaseStorage storage2 = FirebaseStorage.getInstance();
    public static FirebaseFirestore db;
    private static final String TAG = "TradeUploader";

    private static final String collectionName  = "trades";

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

        db = FirebaseFirestore.getInstance();
        StorageMock mock = new StorageMock();
        if(false)
        {
            getDataAndFillStorage(mock);
        }
        else
        {
            mock.addMockData();
            uploadTradesToFirebase(mock.getAllItems());
        }

        // mock.addMockData();


        //FirebaseFirestore database = FirebaseFirestore.getInstance();
        //return database;
        //uploadTradesToFirebase(mock.getAllItems());
        return mock;
    }

    private static void getDataAndFillStorage(StorageMock mock) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Trade trade = document.toObject(Trade.class);
                                    mock.addItem(trade);
                                    Log.d(TAG, "Trade added to storage: " + trade.toString());
                                }catch (Exception e)
                                {
                                    System.out.println(e.toString());
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void uploadTradesToFirebase(ArrayList<Trade> storagevar) {
        //ArrayList<Trade>  storagevar = storage.getAllItems();

        for (Trade trade : storagevar) {
            uploadTrade(trade);
        }
    }
    private static void uploadTrade(Trade trade) {
        db.collection(collectionName)
                .add(trade)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    private static void postValue()
    {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection(collectionName)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private static void getData()
    {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
