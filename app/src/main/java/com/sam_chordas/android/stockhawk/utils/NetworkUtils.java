package com.sam_chordas.android.stockhawk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sam_chordas.android.stockhawk.rest.QuoteDataPoint;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by surajkumarsau on 10/08/16.
 */
public class NetworkUtils {

    public static String fetchData(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static ArrayList<QuoteDataPoint> dataToDataPoints(String response) {
        ArrayList<QuoteDataPoint> dataPoints = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject != null && jsonObject.length() != 0) {

                jsonObject = jsonObject.getJSONObject(IConstants.JSON_QUERY);
                int count = Integer.parseInt(jsonObject.getString(IConstants.JSON_COUNT));

                // If at least one historic data point was returned
                if (count > 1){

                    JSONArray resultsArray = jsonObject.getJSONObject(IConstants.JSON_RESULTS).getJSONArray(IConstants.JSON_QUOTE_ARRAY);

                    if (resultsArray != null && resultsArray.length() != 0){
                        for (int i = 0; i < resultsArray.length(); i++){
                            jsonObject = resultsArray.getJSONObject(i);

                            String date = jsonObject.getString(IConstants.JSON_DATE);
                            Float closeBid = Float.parseFloat(jsonObject.getString(IConstants.JSON_CLOSE_BID));

                            QuoteDataPoint dp = new QuoteDataPoint(date, closeBid);
                            dataPoints.add(dp);
                        }
                    }
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return dataPoints;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

    }
}
