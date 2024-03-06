package ch.teko.currencytrader.storages;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.teko.currencytrader.Controller;
import ch.teko.currencytrader.R;
import ch.teko.currencytrader.models.Currency;
import ch.teko.currencytrader.models.Money;
import ch.teko.currencytrader.models.Trade;

public class StorageFirebase implements IStorageService<Trade> {
    public StorageFirebase()
    {
        db = FirebaseFirestore.getInstance();
        getDataAndFillStorage();
    }
    public FirebaseFirestore db;
    private final String TAG = "TradeUploader";

    private final String collectionName  = "trades";

    private ArrayList<Trade> storage = new ArrayList<>();

    @Override
    public void addItem(Trade item) {
        storage.add(item);
        uploadTrade(item);
    }

    @Override
    public void removeItem(int pos) {
        Trade item = storage.get(pos);
        deleteTradeFromFirebase(item.id);
        storage.remove(pos);
    }

    @Override
    public void updateItem(Trade old, Trade update) {
        storage.remove(old);
        storage.add(update);
        updateTradeInFirebase(old, update);
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



    private void getDataAndFillStorage() {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Trade trade = document.toObject(Trade.class);
                                    storage.add(trade);
                                    Log.d(TAG, "Trade added to storage: " + trade.toString());
                                    Controller.updateListActivity();
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

    private void uploadTrade(Trade trade) {
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


   private void deleteTradeFromFirebase(String tradeId) {
       // Query to find the document with the specified field value
       db.collection(collectionName)
               .whereEqualTo("id", tradeId)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               // Delete the document with the matching field value
                               document.getReference().delete()
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {
                                               Log.d(TAG, "Trade deleted from Firebase with ID: " + tradeId);
                                           }
                                       })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Log.w(TAG, "Error deleting trade from Firebase with ID: " + tradeId, e);
                                           }
                                       });
                           }
                       } else {
                           Log.w(TAG, "Error getting documents.", task.getException());
                       }
                   }
               });
   }

    private void updateTradeInFirebase(Trade oldtrade, Trade trade) {
        // Get the document reference for the specified tradeId
        DocumentReference tradeRef = db.collection(collectionName).document(oldtrade.id);

        // Update the document with the new data
        tradeRef.set(trade)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Trade updated in Firebase with ID: " + trade.id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating trade in Firebase with ID: " + trade.id, e);
                    }
                });
    }

}
