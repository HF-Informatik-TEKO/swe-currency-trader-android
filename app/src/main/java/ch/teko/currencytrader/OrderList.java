package ch.teko.currencytrader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ch.teko.currencytrader.models.Trade;

public class OrderList extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Controller.setOrderListReference(this);
        ListView listView = findViewById(R.id.listView_items);
        List<String> data = Controller.storageToList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view, pos, id) -> {
            Trade item = Controller.storage.getItem(pos);

            Intent intent = new Intent(this, SingleView.class);
            intent.putExtra("trade", pos);
            startActivity(intent);

            System.out.println("short: " + item);
        });

        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            String item = adapter.getItem(pos);
            adapter.remove(item);

            Controller.storage.removeItem(pos);

            System.out.println("long: " + item);
            return false;
        });

        Button create = findViewById(R.id.btn_create);
        create.setOnClickListener(e -> {
            Intent intent = new Intent(this, SingleView.class);
            startActivity(intent);
        });
    }

    public void RecreateArrayadapter()
    {
        ListView listView = findViewById(R.id.listView_items);
        List<String> data = Controller.storageToList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
}