package com.google.android.networth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.text.DecimalFormat;

public class ValueHolder extends BaseObservable {

    private double mTotal = 0;
    private double mTotal_1 = 0;
    private double mDod = 0;

    private String total = "";
    private String mDod_text = "";
    private String yesterday = "";


    public void setTotal(double total) {
        mTotal = total;
    }

    public void setYesterday(double yesterday) {
        mTotal_1 = yesterday;
    }

    public void format(){
        mDod = (mTotal-mTotal_1) / mTotal_1;

        DecimalFormat monetary_format = new DecimalFormat("###.#k Eur");
        String total_formatted = monetary_format.format(mTotal/1000);
        total = total_formatted;

        // Delta from yesterday
        DecimalFormat percent_format = new DecimalFormat("#.#%");

        String delta_percent_formatted = percent_format.format(mDod);
        mDod_text = monetary_format.format(mTotal_1/1000);

        yesterday = "Yesterday: "+mDod_text+" ("+delta_percent_formatted+")";

        this.notifyChange();
    }

    @Bindable
    public String getTotal(){
        return total;
    }

    @Bindable
    public String getYesterday(){
        return yesterday;
    }
}
