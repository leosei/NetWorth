package com.google.android.networth;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.cloud.AuthCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryResponse;
import com.google.cloud.bigquery.QueryResult;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BigQueryTask extends AsyncTask<String, Void, QueryResult> {
    private final String CREDENTIALS_FILE = "AccountTools-9673ba909d81.json";
    private final String PROJECT_ID = "accounttools-1370";

    private Context mContext;
    private MainActivity mActivity;

    public  BigQueryTask (MainActivity mainactivity){
        mActivity = mainactivity;
        mContext = mainactivity.getApplicationContext();
    }

    @Override
    protected QueryResult doInBackground(String... query) {
        QueryResult result = null;

        try {
            AssetManager am = mContext.getAssets();
            InputStream isCredentialsFile = am.open(CREDENTIALS_FILE);
            Log.d("BQ","opened config file");
            BigQuery bigquery = BigQueryOptions.builder()
                    .authCredentials(AuthCredentials.createForJson(isCredentialsFile))
                    .projectId( PROJECT_ID )
                    .build().service();
            Log.d("BQ","created bigquery service");

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.builder(query[0])
                            .build();

            Log.d("BQ","query ready");
            // Create a job ID so that we can safely retry.
            JobId jobId = JobId.of(UUID.randomUUID().toString());
            Job queryJob = bigquery.create(JobInfo.builder(queryConfig).jobId(jobId).build());
            Log.d("BQ","job running");

            // Check for errors
            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.status().error() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                Log.d("BQ","JOb issue");
                throw new RuntimeException(queryJob.status().error().toString());
            }

            QueryResponse response = bigquery.getQueryResults(jobId);
            result = response.result();


        } catch (Exception e) {
            Log.d("Main", "Exception: " + e.toString());
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        Log.d("Main", "Launching BigQuery API request ");
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(QueryResult results) {
        Log.d("bq", "results ready");

        int count = 0;

        // Go through Query results
        Iterator<List<FieldValue>> results_iterator = results.iterateAll();
        while (results_iterator.hasNext()) {
            List<FieldValue> row = results_iterator.next();

            // First value is today's total
            if (count == 0) {
                mActivity.valueholder.setTotal( row.get(0).doubleValue() );
            }
            // Second values is yesterday's total
            else {
                mActivity.valueholder.setYesterday( row.get(0).doubleValue() );
                mActivity.valueholder.calculateDelta();
            }
            count++;
        }
    }

}
