package com.google.android.networth;

import android.view.View;

public class RefreshListener  implements View.OnClickListener
{

    private ValueHolder mValueholder;

    public RefreshListener(ValueHolder valueHolder) {
        mValueholder = valueHolder;
    }

    @Override
    public void onClick(View v)
    {
        mValueholder.updateValues();
    }

};
