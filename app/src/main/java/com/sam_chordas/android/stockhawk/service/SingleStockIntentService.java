package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.utils.IConstants;

/**
 * Created by surajkumarsau on 10/08/16.
 */
public class SingleStockIntentService extends IntentService {

    public SingleStockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putString(IConstants.PARAMS_EXTRA_SYMBOL, intent.getStringExtra(IConstants.PARAMS_EXTRA_SYMBOL));
        bundle.putString(IConstants.PARAMS_EXTRA_END_DATE, intent.getStringExtra(IConstants.PARAMS_EXTRA_END_DATE));
        bundle.putString(IConstants.PARAMS_EXTRA_START_DATE, intent.getStringExtra(IConstants.PARAMS_EXTRA_START_DATE));

        SingleStockTaskService service = new SingleStockTaskService();
        service.onRunTask(new TaskParams(IConstants.PARAMS_TAG, bundle));
    }
}
