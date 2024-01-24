package ch.teko.currencytrader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.teko.currencytrader.models.Trade;
import ch.teko.currencytrader.models.TradeBuilder;

public class SingleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        TextView customerText = findViewById(R.id.input_customer);
        TextView sourceAmount = findViewById(R.id.input_source_amount);
        TextView sourceCurrency = findViewById(R.id.input_source_cur);
        TextView targetAmount = findViewById(R.id.input_target_amount);
        TextView targetCurrency = findViewById(R.id.input_target_cur);

        int intentPos = getIntent().getIntExtra("trade", -1);
        boolean isEdit = intentPos < 0 ? false : true;

        if (isEdit) {
            Trade trade = Controller.storage.getItem(intentPos);
            customerText.setText(trade.customer);
            sourceAmount.setText("" + trade.source.amount);
            sourceCurrency.setText(trade.source.currency.toString());
            targetAmount.setText("" + trade.target.amount);
            targetCurrency.setText(trade.target.currency.toString());
        }


        Button post = findViewById(R.id.btn_post);
        post.setOnClickListener(e -> {
            TradeBuilder builder = new TradeBuilder();
            builder.addCustomer(customerText.getText().toString());
            builder.addSourceAmount(sourceAmount.getText().toString());
            builder.addSourceCurrency(sourceCurrency.getText().toString().toUpperCase());
            builder.addTargetAmount(targetAmount.getText().toString());
            builder.addTargetCurrency(targetCurrency.getText().toString().toUpperCase());
            Trade trade = builder.build();

            if (isEdit) {
                Trade oldTrade = Controller.storage.getItem(intentPos);
                Controller.storage.updateItem(oldTrade, trade);
                System.out.println("succesfully updated trade.");
            } else {
                if (trade.source.amount == 0 ^ trade.target.amount == 0) {
                    if (trade.source.amount == 0) {
                        trade.source.amount = Controller.exchange.calculateSource(trade);
                    } else {
                        trade.target.amount = Controller.exchange.calculateTarget(trade);
                    }
                }

                Controller.storage.addItem(trade);
                System.out.println("succesfully added new trade.");
            }

            Intent intent = new Intent(this, OrderList.class);
            startActivity(intent);
        });

    }
}