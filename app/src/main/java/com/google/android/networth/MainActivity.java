package com.google.android.networth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.QueryResult;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // Credentials file: this file is stored in the assets/ directory. Replace it with yours.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView total = findViewById(R.id.networth);
        TextView yesterday = findViewById(R.id.yesterday);

        try {
            QueryResult result = new BigQueryTask().execute(MainActivity.this).get();
            Log.d("MAIN VALUE",result.toString());
            this.processResults(result,total,yesterday);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private void processResults(QueryResult results, TextView total_view, TextView yesterday_view){
        int count = 0;
        double total = 0;
        double total_yesterday=0;
        Iterator<List<FieldValue>> results_iterator = results.iterateAll();
        while(results_iterator.hasNext()) {
            List<FieldValue> rows = results_iterator.next();
            Log.d("ROW", "Total" + rows.toString());

            if (count == 0) {
                total = rows.get(0).doubleValue();
            } else {
                total_yesterday = rows.get(0).doubleValue();
            }
            count ++;
        }

        Log.d("VALUE", "Total: " +total);
        Log.d("VALUE", "Yesterday's total: " +total_yesterday);

        // Total today
        DecimalFormat monetary_format = new DecimalFormat("###.#k Eur");
        String total_formatted = monetary_format.format(total/1000);
        String total_text = total_formatted;
        total_view.setText(total_text);

        // Delta from yesterday
        DecimalFormat percent_format = new DecimalFormat("#.#");
        double delta_percent = 100 * (total-total_yesterday) / total_yesterday;

        String delta_percent_formatted = percent_format.format(delta_percent);
        String total_yesterday_formatted = monetary_format.format(total_yesterday/1000);

        String yesterday_text = "Yesterday: "+total_yesterday_formatted+" ("+delta_percent_formatted+"%)";
        yesterday_view.setText(yesterday_text);
    }

}
