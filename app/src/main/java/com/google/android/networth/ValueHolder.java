package com.google.android.networth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.text.DecimalFormat;

public class ValueHolder extends BaseObservable {

    private double mTotal = 0;
    private double mTotal_1 = 0;
    private double mDod = 0;

    private MainActivity mActivity;

    public ValueHolder(MainActivity activity){
        mActivity = activity;
    }

    public void setTotal(double total) {
        mTotal = total;
    }

    public void setYesterday(double yesterday) {
        mTotal_1 = yesterday;
    }

    public void calculateDelta(){
        mDod = (mTotal-mTotal_1) / mTotal_1;
        this.notifyChange();
    }

    @Bindable
    public String getTotal(){
        DecimalFormat monetary_format = new DecimalFormat("###.#k Eur");
        return monetary_format.format(mTotal/1000);
    }

    @Bindable
    public String getYesterday(){
        DecimalFormat monetary_format = new DecimalFormat("###.#k Eur");
        DecimalFormat percent_format = new DecimalFormat("#.#%");

        String delta_percent_formatted = percent_format.format(mDod);
        String yesterday = monetary_format.format(mTotal_1/1000);

        return "Yesterday: "+yesterday+" ("+delta_percent_formatted+")";
    }

    public void updateValues(){
        // Get the data from Big Query.
        BigQueryTask bigquerytask = new BigQueryTask(mActivity);
        bigquerytask.execute( getQuery() );

    }

    private String getQuery(){
        return  "SELECT "
                + "Total "
                + "FROM NetWorthTracker.Stats "
                + "ORDER BY Date DESC "
                + "LIMIT 2";
    }
}


