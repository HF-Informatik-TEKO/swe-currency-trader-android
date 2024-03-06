package ch.teko.currencytrader.exchange;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyApiRequestTask extends AsyncTask<String, Void, HashMap<String, Double>> {
    @Override
    protected HashMap<String, Double> doInBackground(String... params) {

        if (params.length < 1) {
            return  new HashMap<String, Double>();
        }

        String from = params[0];
        String[] to = Arrays.stream(params, 1, params.length).toArray(String[]::new);

        // Commented out to save requests on limited API.
        String urlString = getUrlString(from, to);
        String response = performRequest(urlString);

        // Response to mock the currency API.
        //String response = "{\"meta\":{\"last_updated_at\":\"2024-02-21T23:59:59Z\"},\"data\":{\"CHF\":{\"code\":\"CHF\",\"value\":0.8792101731},\"EUR\":{\"code\":\"EUR\",\"value\":0.9240101663}}}";

        HashMap<String, Double> parsedResponse = parseResponse(response);
        parsedResponse.put(from, 1.0d);

        return parsedResponse;
    }

    private HashMap<String, Double> parseResponse(String input) {
        HashMap<String, Double> map = new HashMap<>();
        if (input == null) {
            return map;
        }

        Pattern pattern = Pattern.compile("\"code\":\"(\\w+)\",\"value\":(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String name = matcher.group(1);
            Double value = Double.parseDouble(matcher.group(2));

            System.out.println(name + " - " + value);
            map.put(name, value);
        }

        return map;
    }

    private static String performRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))
            ) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response:\n" + response);

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @NonNull
    private static String getUrlString(String from, String[] to) {
        StringJoiner sj = new StringJoiner(",");
        for (String s : to) {
            sj.add(s);
        }

        String urlString = String.format(
                "https://api.currencyapi.com/v3/latest?apikey=%s&base_currency=%s&currencies=%s",
                "cur_live_L82NBB9WjZxvMCKvSHuVKr0pfxOGmqonOq3d9WOV",
                from,
                sj
        );
        return urlString;
    }

    @Override
    protected void onPostExecute(HashMap<String, Double> result) {
        // Handle the result on the main thread
        if (result.equals("Success")) {
            // Do something with the data
        } else {
            // Show an error message
        }
    }
}
