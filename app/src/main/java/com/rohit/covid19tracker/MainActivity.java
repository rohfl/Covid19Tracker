package com.rohit.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView confirmedCases;
    private TextView activeCases;
    private TextView recoveredCases;
    private TextView deceasedCases;
    private TextView samplesTested;
    private TextView time;
    private TextView confirmedCasesNew;
    private TextView activeCasesNew;
    private TextView recoveredCasesNew;
    private TextView deceasedCasesNew;
    private TextView samplesTestedNew ;
    private String confirmedCases1 , activeCases1 , recoveredCases1 , deceasedCases1 , date1 ;
    private String confirmedCases1New , recoveredCases1New , deceasedCases1New ;
    private PieChart pieChart ;
    private TextView date ;
    private SwipeRefreshLayout swipe ;
    private ProgressDialog progressDialog ;
    private int confirmation = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmedCases = findViewById(R.id.confirmedCases) ;
        activeCases = findViewById(R.id.activeCases) ;
        recoveredCases = findViewById(R.id.recoveredCases) ;
        deceasedCases = findViewById(R.id.deceasedCases) ;
        samplesTested = findViewById(R.id.samplesTested) ;
        time = findViewById(R.id.time) ;
        date = findViewById(R.id.date) ;
        confirmedCasesNew = findViewById(R.id.confirmedCasesNew) ;
        activeCasesNew = findViewById(R.id.activeCasesNew) ;
        recoveredCasesNew = findViewById(R.id.recoveredCasesNew) ;
        deceasedCasesNew = findViewById(R.id.deceasedCasesNew) ;
        samplesTestedNew = findViewById(R.id.samplesTestedNew) ;
        swipe = findViewById(R.id.main_refreshLayout) ;
        pieChart = findViewById(R.id.pieChart) ;
        showProgressDialog();
        getData() ;

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                confirmedCases.setText(null) ;
                activeCases.setText(null) ;
                recoveredCases.setText(null) ;
                deceasedCases.setText(null) ;
                samplesTested.setText(null);
                time.setText(null);
                confirmedCasesNew.setText(null) ;
                activeCasesNew.setText(null) ;
                recoveredCasesNew.setText(null) ;
                deceasedCasesNew.setText(null) ;
                samplesTestedNew.setText(null) ;
                getData() ;
                swipe.setRefreshing(false);
                Toast.makeText(MainActivity.this , "Refreshed" , Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getData(){

        RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        String url = "https://api.covid19india.org/data.json" ;
        pieChart.clearChart() ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("statewise") ;
                    JSONObject stateWise = jsonArray.getJSONObject(0) ;
                        confirmedCases1 = stateWise.getString("confirmed");
                        activeCases1 = stateWise.getString("active");
                        date1 = stateWise.getString("lastupdatedtime");
                        recoveredCases1 = stateWise.getString("recovered");
                        deceasedCases1 = stateWise.getString("deaths");
                        confirmedCases1New = stateWise.getString("deltaconfirmed");
                        deceasedCases1New = stateWise.getString("deltadeaths");
                        recoveredCases1New = stateWise.getString("deltarecovered");

                        Runnable progressRunnable = new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                progressDialog.cancel();


                                // Making Temporary Strings to parse and use in our PieChart ;

                                String active = activeCases1 ;
                                String recovered = recoveredCases1 ;
                                String deceased = deceasedCases1 ;



                                // Calculating new Active Cases as it is not available in the api api.covid19india.org/data.json ;

                                int intActive = (Integer.parseInt(confirmedCases1New)) - ((Integer.parseInt(recoveredCases1New)) + Integer.parseInt(deceasedCases1New));
                                activeCasesNew.setText("+" + NumberFormat.getInstance().format(intActive));

                                int intConfirmed = Integer.parseInt(confirmedCases1) ;
                                confirmedCases1 = NumberFormat.getInstance().format(intConfirmed) ;
                                confirmedCases.setText(confirmedCases1) ;

                                int intConfirmedNew = Integer.parseInt(confirmedCases1New) ;
                                confirmedCases1New = NumberFormat.getInstance().format(intConfirmedNew) ;
                                confirmedCasesNew.setText("+" + confirmedCases1New) ;

                                intActive = Integer.parseInt(activeCases1) ;
                                activeCases1 = NumberFormat.getInstance().format(intActive) ;
                                activeCases.setText(activeCases1) ;

                                int intRecovered = Integer.parseInt(recoveredCases1);
                                recoveredCases1 = NumberFormat.getInstance().format(intRecovered);
                                recoveredCases.setText(recoveredCases1);

                                int intRecoveredNew = Integer.parseInt(recoveredCases1New);
                                recoveredCases1New = NumberFormat.getInstance().format(intRecoveredNew);
                                recoveredCasesNew.setText("+" + recoveredCases1New);

                                int intDeceased = Integer.parseInt(deceasedCases1);
                                deceasedCases1 = NumberFormat.getInstance().format(intDeceased);
                                deceasedCases.setText(deceasedCases1);

                                int intDeceasedCasesNew = Integer.parseInt(deceasedCases1New);
                                deceasedCases1New = NumberFormat.getInstance().format(intDeceasedCasesNew);
                                deceasedCasesNew.setText("+" + deceasedCases1New);

                                String dateFormat = formatDate(date1, 1);
                                date.setText(dateFormat);

                                String timeFormat = formatDate(date1, 2);
                                time.setText(timeFormat);

                                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active), Color.parseColor("#D44545"))) ;
                                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered), Color.parseColor("#51D562"))) ;
                                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(deceased), Color.parseColor("#828282"))) ;

                                pieChart.startAnimation() ;
                                getSamples();

                            }
                        } ;

                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 1000);
                        confirmation = 1 ;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public String formatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.info) {
            Intent intent = new Intent(this,InfoActivity.class) ;
            startActivity(intent) ;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openStateData(View view){
        Intent intent = new Intent(this,StatewiseView.class) ;
        startActivity(intent) ;
    }
    public void openWorldData(View view){
        Intent intent = new Intent(this,CountrywiseView.class) ;
        startActivity(intent) ;
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }

    public void getSamples() {
        RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        String url = "https://api.covid19india.org/data.json" ;
      //  pieChart.clearChart() ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Getting Total Samples and Samples taken today
                    JSONArray jsonArray = response.getJSONArray("tested") ;
                    JSONObject tested = jsonArray.getJSONObject(jsonArray.length() - 1 ) ;
                    String tested1 = tested.getString("totalsamplestested") ;
                    String tested2 ;
                    if(tested1.isEmpty()){
                        tested = jsonArray.getJSONObject(jsonArray.length() - 2 ) ;
                        tested1 = tested.getString("totalsamplestested") ;
                        tested = jsonArray.getJSONObject(jsonArray.length() - 3 ) ;
                        tested2 = tested.getString("totalsamplestested") ;

                        if(tested1.isEmpty()){
                            tested1 = tested2 ;
                        }
                    }
                    else {
                        tested = jsonArray.getJSONObject(jsonArray.length() - 2 ) ;
                        tested2 = tested.getString("totalsamplestested") ;
                    }

                    int newSample = Integer.parseInt(tested1) - Integer.parseInt(tested2) ;
                    samplesTested.setText(NumberFormat.getInstance().format(Integer.parseInt(tested1))) ;
                    samplesTestedNew.setText("+"+NumberFormat.getInstance().format(newSample)) ;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            } ,  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

}
