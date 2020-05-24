package com.rohit.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class CountrywiseView extends AppCompatActivity implements CountryAdapter.OnClickListener {

    public static final String COUNTRY_NAME = "countryName";
    public static final String COUNTRY_CONFIRMED = "confirmedCases";
    public static final String COUNTRY_ACTIVE= "activeCases";
    public static final String COUNTRY_RECOVERED = "recoveredCases";
    public static final String COUNTRY_DECEASED = "deceasedCases";
    public static final String COUNTRY_CONFIRMED_NEW = "confirmedCasesNew";
    public static final String COUNTRY_DECEASED_NEW = "deceasedCasesNew";
    private ProgressDialog progressDialog ;
    private RecyclerView recyclerView ;
    private CountryAdapter isAdapter ;
    private String countryName , activeCases , confirmedCases , deceasedCases , recoveredCases , confirmedCasesNew , deceasedCasesNew , imageURL;
    private ArrayList<countries> countryWiseList ;
    private SwipeRefreshLayout swipe ;
    private RequestQueue requestQueue ;
    private EditText searchText ;
    private int confirmation = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrywise_view);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Country Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipe = findViewById(R.id.swipeRefresh) ;
        countryWiseList = new ArrayList<>() ;
        searchText = findViewById(R.id.searchText) ;
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString()) ;
            }
        });
        requestQueue = Volley.newRequestQueue(this) ;
        showProgressDialog();
        getData() ;
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipe.setRefreshing(false);
                Toast.makeText(CountrywiseView.this , "Date Refreshed" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData() {

        String URL = "https://disease.sh/v2/countries" ;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    countryWiseList.clear();
                    for(int i = 0 ; i < response.length() ; i++ ) {

                        JSONObject jsonObject = response.getJSONObject(i) ;
                        countryName = jsonObject.getString("country") ;
                        confirmedCases = jsonObject.getString("cases") ;
                        confirmedCasesNew = jsonObject.getString("todayCases") ;
                        deceasedCases = jsonObject.getString("deaths") ;
                        deceasedCasesNew = jsonObject.getString("todayDeaths") ;
                        recoveredCases = jsonObject.getString("recovered") ;
                        activeCases = jsonObject.getString("active") ;
                        // Here we have the imageURL in a json object of the current json object ;
                        JSONObject temp = jsonObject.getJSONObject("countryInfo") ;
                        imageURL = temp.getString("flag") ;
                        countries country = new countries(countryName,confirmedCases,confirmedCasesNew,recoveredCases,deceasedCases,deceasedCasesNew,activeCases,imageURL) ;
                        countryWiseList.add(country) ;




                    }
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            isAdapter = new CountryAdapter(countryWiseList,CountrywiseView.this) ;
                            recyclerView.setAdapter(isAdapter);
                            isAdapter.setOnItemClickListener(CountrywiseView.this);
                        }
                    } ;
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(runnable, 500);
                    confirmation = 1;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest) ;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent countryIntent = new Intent(this,CountryData.class) ;
        countries isCountry = countryWiseList.get(position) ;

        countryIntent.putExtra(COUNTRY_NAME,isCountry.getCountryName()) ;
        countryIntent.putExtra(COUNTRY_CONFIRMED,isCountry.getConfirmedCases()) ;
        countryIntent.putExtra(COUNTRY_CONFIRMED_NEW,isCountry.getConfirmedCasesNew()) ;
        countryIntent.putExtra(COUNTRY_ACTIVE,isCountry.getActiveCases()) ;
        countryIntent.putExtra(COUNTRY_RECOVERED,isCountry.getRecoveredCases()) ;
        countryIntent.putExtra(COUNTRY_DECEASED,isCountry.getDeceasedCases()) ;
        countryIntent.putExtra(COUNTRY_DECEASED_NEW,isCountry.getDeceasedCasesNew()) ;

        startActivity(countryIntent) ;

    }
    private void filter(String text) {
        ArrayList<countries> filteredList = new ArrayList<>();
        for (countries item : countryWiseList) {
            if (item.getCountryName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        isAdapter.filterList(filteredList);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(CountrywiseView.this) ;
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(CountrywiseView.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }

}
