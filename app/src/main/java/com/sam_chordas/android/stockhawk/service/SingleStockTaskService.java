package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.rest.QuoteDataPoint;
import com.sam_chordas.android.stockhawk.utils.IConstants;
import com.sam_chordas.android.stockhawk.utils.NetworkUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by surajkumarsau on 10/08/16.
 */
public class SingleStockTaskService extends GcmTaskService {

    private OkHttpClient client = new OkHttpClient();
    private Context mContext;
    private StringBuilder mStoredSymbols = new StringBuilder();

    @Override
    public int onRunTask(TaskParams params) {
        if(mContext == null)
            mContext = this;

        String symbol = params.getExtras().getString(IConstants.PARAMS_EXTRA_SYMBOL);
        String startDate = params.getExtras().getString(IConstants.PARAMS_EXTRA_START_DATE);
        String endDate = params.getExtras().getString(IConstants.PARAMS_EXTRA_END_DATE);

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");

        try {
            urlBuilder.append(URLEncoder.encode("select * from yahoo.finance.historicaldata where symbol=\"" + symbol + "\"" +
                    " and startDate=\"" + startDate + "\"" +
                    " and endDate=\"" + endDate + "\"", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        urlBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                + "org%2Falltableswithkeys&callback=");

        String url = urlBuilder.toString();

        int result = GcmNetworkManager.RESULT_FAILURE;

        try {
            String response = NetworkUtils.fetchData(client, url);
            result = GcmNetworkManager.RESULT_SUCCESS;

            ArrayList<QuoteDataPoint> points = NetworkUtils.dataToDataPoints(response);

            Intent historyIntent = new Intent(IConstants.INTENT_STOCK_HISTORY);
            historyIntent.putParcelableArrayListExtra(IConstants.INTENT_EXTRA_STOCK_HISTORY, points);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(historyIntent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
