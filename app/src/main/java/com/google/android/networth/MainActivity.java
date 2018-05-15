package com.google.android.networth;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.networth.databinding.ActivityMainBinding;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ValueHolder valueholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind to a new valueholder instance
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        valueholder = new ValueHolder();
        binding.setValueholder(valueholder);

        try {
            // Get the data from Big Query.
            new BigQueryTask().execute(this).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}
