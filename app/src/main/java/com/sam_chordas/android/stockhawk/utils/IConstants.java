package com.sam_chordas.android.stockhawk.utils;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteDatabase;

/**
 * Created by surajkumarsau on 10/08/16.
 */
public interface IConstants {
    String DATE_FORMAT = "yyyy-MM-dd";

    String ROBOTO_LIGHT_FONT = "fonts/Roboto-Light.ttf";

    String INTENT_STOCK_HISTORY = "stock_history";
    String INTENT_EXTRA_STOCK_HISTORY = "extra_stock_history";

    String PARAMS_EXTRA_SYMBOL = "stock_symbol";
    String PARAMS_EXTRA_START_DATE = "start_date";
    String PARAMS_EXTRA_END_DATE = "end_date";
    String PARAMS_TAG = "param_tag";

    String INTENT_EXTRA_STOCK_SYMBOL = "stock_symbol";

    String STATE_SYMBOL = "state_symbol";
    String STATE_DATES = "state_dates";
    String STATE_VALUES = "state_values";
    String STATE_LOWER_BOUND = "state_lower_bound";
    String STATE_UPPER_BOUND = "state_upper_bound";
    String STATE_CHART_SHOWN = "chart_shown";

    int INDEX_STOCK_ID = 0;
    int INDEX_SYMBOL = 1;
    int INDEX_BIDPRICE = 2;

    String[] QUOTE_COLUMNS = {
            QuoteDatabase.QUOTES + "." + QuoteColumns._ID,
            QuoteColumns.SYMBOL,
            QuoteColumns.BIDPRICE
    };
}
