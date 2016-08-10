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

    public SingleStockIntentService() {
        super(SingleStockIntentService.class.getName());
    }

    public SingleStockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle args = new Bundle();

        args.putString(IConstants.PARAMS_EXTRA_SYMBOL,
                intent.getStringExtra(IConstants.PARAMS_EXTRA_SYMBOL));
        args.putString(IConstants.PARAMS_EXTRA_START_DATE,
                intent.getStringExtra(IConstants.PARAMS_EXTRA_START_DATE));
        args.putString(IConstants.PARAMS_EXTRA_END_DATE,
                intent.getStringExtra(IConstants.PARAMS_EXTRA_END_DATE));

        // Initiates the task service
        SingleStockTaskService taskService = new SingleStockTaskService();
        taskService.onRunTask(new TaskParams(IConstants.PARAMS_TAG, args));

    }
}
