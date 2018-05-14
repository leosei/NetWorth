package com.google.android.networth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String projectId ="accounttools-1370";


        //Some url endpoint that you may have
        String myUrl = "http://google.com";
        //String to place our result in
        String result = "" ;
        //Instantiate new instance of our class
        BigQuerySync getRequest = new BigQuerySync();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TextView networth = findViewById(R.id.networth);
        networth.setText(result);
    }
}
