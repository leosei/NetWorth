package com.google.android.networth;

import android.os.AsyncTask;
import android.util.Log;

//import com.google.api.services.bigquery.Bigquery;
//import com.google.api.services.bigquery.model.TableRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BigQuerySync extends AsyncTask<String,Void,String> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String result;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}


/*
public class BigQuerySync extends AsyncTask<String,void,List<TableRow>> {

    Bigquery mBigQuery;
    BigQueryFactory mFactory;


    protected void  onPreExecute() {
        // Create a new Bigquery client authorized via Application Default Credentials.
        mFactory = new BigQueryFactory();
        mBigQuery = mFactory.createAuthorizedClient();

    }

    protected TableRow doInBackground(String projectId) {
        List<TableRow> rows = mFactory.executeQuery(
                        "SELECT Total FROM NetWorthTracker.Stats\n" +
                                "ORDER BY Date\n"+
                                " DESC LIMIT 1\n",
                        mBigQuery,
                        projectId);
        return rows
    }



    protected void onPostExecute(List<TableRow> rows){
        mFactory.displayResults(rows);
    }
}*/