package ch.teko.currencytrader;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkTask extends AsyncTask<String, Void, HashMap<String, Double>> {
    @Override
    protected HashMap<String, Double> doInBackground(String... params) {

        String from = params[0];
        String[] to = Arrays.stream(params, 1, params.length).toArray(String[]::new);
        System.out.println(params);
        return new HashMap<String, Double>();

        /*String from = "CHF";
        String[] to = {"USD", "EUR", "CHF"};
        String urlString = String.format(
                "https://api.currencyapi.com/v3/latest?apikey=%s&base_currency=%s&currencies=%s",
                "cur_live_L82NBB9WjZxvMCKvSHuVKr0pfxOGmqonOq3d9WOV",
                from,
                to
        );
        try {
            //URL url = new URL("https://api.currencyapi.com/v3/latest?apikey=cur_live_L82NBB9WjZxvMCKvSHuVKr0pfxOGmqonOq3d9WOV&base_currency=CHF&currencies=USD,EUR,CHF");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response:\n" + response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String[] {"Success"};*/
    }

    @Override
    protected void onPostExecute(String[] result) {
        // Handle the result on the main thread
        if (result.equals("Success")) {
            // Do something with the data
        } else {
            // Show an error message
        }
    }
}
