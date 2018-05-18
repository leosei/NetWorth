package com.google.android.networth;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.networth.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ValueHolder valueholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new valueholder and bind to it.
        valueholder = new ValueHolder(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setValueholder(valueholder);
        // Update values
        //valueholder.updateValues();

        Button button = findViewById(R.id.updateButton);
        button.setOnClickListener( new RefreshListener(valueholder));
    }

}
