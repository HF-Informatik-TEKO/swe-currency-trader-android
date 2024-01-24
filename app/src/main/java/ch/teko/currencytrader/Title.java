package ch.teko.currencytrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Button startButton = findViewById(R.id.title_btn_start);
        startButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, OrderList.class);
            startActivity(intent);
        });
    }
}