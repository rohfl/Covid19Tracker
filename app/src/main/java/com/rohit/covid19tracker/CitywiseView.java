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
import static com.rohit.covid19tracker.StatewiseView.STATE_NAME_FOR_DISTRICT;

public class CitywiseView extends AppCompatActivity implements CityAdapter.OnCLickListener {
    public static final String DISTRICT_NAME = "districtName" ;
    public static final String DISTRICT_ACTIVE = "districtActive" ;
    public static final String DISTRICT_ACTIVE_NEW = "districtActiveNew" ;
    public static final String DISTRICT_CONFIRMED = "districtConfirmed" ;
    public static final String DISTRICT_CONFIRMED_NEW = "districtConfirmedNew" ;
    public static final String DISTRICT_RECOVERED = "districtRecovered" ;
    public static final String DISTRICT_RECOVERED_NEW = "districtRecoveredNew" ;
    public static final String DISTRICT_DECEASED = "districtDeceased" ;
    public static final String DISTRICT_DECEASED_NEW = "districtDeceasedNew" ;
    public String state ;
    private SwipeRefreshLayout swipeRefreshLayout ;
    private ArrayList<cities> cityWiseList ;
    private CityAdapter isAdapter ;
    private RequestQueue requestQueue ;
    private RecyclerView recyclerView ;
    private ProgressDialog progressDialog ;
    private String districtName , confirmed , confirmedNew , active , activeNew , recovered , recoveredNew , deceased , deceasedNew ;
    private EditText searchText ;
    private int confirmation = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citywise_view);
        Intent intent = getIntent() ;
        String stateName = intent.getStringExtra(STATE_NAME_FOR_DISTRICT) ;
        state = stateName ;
        Objects.requireNonNull(getSupportActionBar()).setTitle(stateName);
        cityWiseList = new ArrayList<>() ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        requestQueue = Volley.newRequestQueue(this) ;
        swipeRefreshLayout = findViewById(R.id.swipeRefresh) ;
        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        showProgressDialog();
        getData() ;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CitywiseView.this , "Date Refreshed" , Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void getData () {
        String URL = "https://api.covid19india.org/v2/state_district_wise.json" ;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    cityWiseList.clear() ;
                    for(int i = 0 ; i < response.length() ; i++){
                        JSONObject isObj = response.getJSONObject(i) ;
                        String isState = isObj.getString("state") ;
                        if(state.toLowerCase().equals(isState.toLowerCase())) {
                            JSONArray districtArray = isObj.getJSONArray("districtData") ;
                            for(int j = 0 ; j < districtArray.length() ; j++){
                                JSONObject jsonObject = districtArray.getJSONObject(j) ;
                                districtName = jsonObject.getString("district") ;
                                active = jsonObject.getString("active") ;
                                confirmed = jsonObject.getString("confirmed") ;
                                recovered = jsonObject.getString("recovered") ;
                                deceased = jsonObject.getString("deceased") ;
                                JSONObject object = jsonObject.getJSONObject("delta") ;
                                confirmedNew = object.getString("confirmed") ;
                                recoveredNew = object.getString("recovered") ;
                                deceasedNew = object.getString("deceased") ;
                                // for calculating new active cases in every districts ;
                                int temp = Integer.parseInt(confirmedNew) - (Integer.parseInt(recoveredNew)+Integer.parseInt(deceasedNew)) ;
                                activeNew = Integer.toString(temp) ;
                                cities isCity = new cities(districtName,confirmed,active,recovered,deceased,confirmedNew,activeNew,recoveredNew,deceasedNew) ;
                                cityWiseList.add(isCity) ;
                            }
                        }
                    }
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            isAdapter = new CityAdapter(CitywiseView.this,cityWiseList) ;
                            recyclerView.setAdapter(isAdapter) ;
                            isAdapter.setOnClickListener(CitywiseView.this) ;
                        }
                    } ;
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(runnable, 500);
                    confirmation = 1;



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
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
    public void onClick(int position) {
        Intent intent = new Intent(this,CityData.class) ;
        cities city = cityWiseList.get(position) ;
        intent.putExtra(DISTRICT_NAME,city.getDistrictName()) ;
        intent.putExtra(DISTRICT_ACTIVE,city.getActiveCases()) ;
        intent.putExtra(DISTRICT_ACTIVE_NEW,city.getActiveCasesNew()) ;
        intent.putExtra(DISTRICT_CONFIRMED,city.getConfirmedCases()) ;
        intent.putExtra(DISTRICT_CONFIRMED_NEW,city.getConfirmedCasesNew()) ;
        intent.putExtra(DISTRICT_RECOVERED,city.getRecoveredCases()) ;
        intent.putExtra(DISTRICT_RECOVERED_NEW,city.getRecoveredCasesNew()) ;
        intent.putExtra(DISTRICT_DECEASED,city.getDeceasedCases()) ;
        intent.putExtra(DISTRICT_DECEASED_NEW,city.getDeceasedCasesNew()) ;


        startActivity(intent) ;
    }
    private void filter(String text) {
        ArrayList<cities> filteredList = new ArrayList<>();
        for (cities item : cityWiseList) {
            if (item.getDistrictName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        isAdapter.filterList(filteredList);
    }
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(CitywiseView.this) ;
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(CitywiseView.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }
}
