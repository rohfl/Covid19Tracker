package com.rohit.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StatewiseView extends AppCompatActivity implements StateAdapter.OnItemClickListner {

    public static final String STATE_NAME = "stateName";
    public static final String STATE_CONFIRMED = "confirmedCases";
    public static final String STATE_ACTIVE = "activeCases";
    public static final String STATE_RECOVERED = "recoveredCases";
    public static final String STATE_DECEASED = "deceasedCases";
    public static final String STATE_CONFIRMED_NEW = "confirmedCasesNew";
    public static final String STATE_RECOVERED_NEW = "recoveredCasesNew";
    public static final String STATE_DECEASED_NEW = "deceasedCasesNew";
    public static final String DATE_AND_TIME = "dateAndTime";
    public static final String STATE_ACTIVE_NEW = "activeCasesNew" ;
    public static final String STATE_NAME_FOR_DISTRICT = "stateName" ;
 //   private static final String TAG = "Error" ;

    public ProgressDialog progressDialog ;
    private RecyclerView recyclerView ;
    private StateAdapter isAdapter ;
    private String stateName , activeCases , confirmedCases , deceasedCases , recoveredCases , confirmedCasesNew , activeCasesNew , deceasedCasesNew , recoveredCasesNew ;
    private String dateAndTime ;
    private ArrayList<states> stateWiseList ;
    private SwipeRefreshLayout swipe ;
    private RequestQueue requestQueue ;
    private EditText searchText ;
    private int confirmation = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_statewise_view) ;
        Objects.requireNonNull(getSupportActionBar()).setTitle("State Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipe = findViewById(R.id.swipeRefresh) ;
        stateWiseList = new ArrayList<>() ;
        searchText = findViewById(R.id.searchText) ;
        requestQueue = Volley.newRequestQueue(this) ;

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

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipe.setRefreshing(false);
                Toast.makeText(StatewiseView.this , "Data Refreshed" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(){

        String URL = "https://api.covid19india.org/data.json" ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try{
                    stateWiseList.clear();
                    JSONArray jsonArray = response.getJSONArray("statewise") ;
                    for ( int i = 1 ; i < jsonArray.length() ; i++) {

                        JSONObject object = jsonArray.getJSONObject(i) ;
                        stateName = object.getString("state" );
                        activeCases = object.getString("active") ;
                        recoveredCases = object.getString("recovered") ;
                        confirmedCases = object.getString("confirmed") ;
                        deceasedCases = object.getString("deaths") ;
                        confirmedCasesNew = object.getString("deltaconfirmed") ;
                        recoveredCasesNew = object.getString("deltarecovered") ;
                        deceasedCasesNew = object.getString("deltadeaths") ;
                        dateAndTime = object.getString("lastupdatedtime") ;
                        int temp1 = Integer.parseInt(confirmedCasesNew) - (Integer.parseInt(recoveredCasesNew) + Integer.parseInt(deceasedCasesNew) ) ;
                        activeCasesNew = Integer.toString(temp1) ;
                        states isState = new states(stateName, activeCases, confirmedCases, deceasedCases, recoveredCases, confirmedCasesNew, activeCasesNew, deceasedCasesNew, recoveredCasesNew, dateAndTime) ;
                        stateWiseList.add(isState) ;


                    }

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            isAdapter = new StateAdapter(StatewiseView.this , stateWiseList) ;
                            recyclerView.setAdapter(isAdapter) ;
                            isAdapter.setOnItemClickListener(StatewiseView.this);
                        }
                    } ;
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(runnable, 500);
                    confirmation = 1;



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
        requestQueue.add(jsonObjectRequest) ;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(int position) {
       // Toast.makeText(StatewiseView.this , "StateWiseView" , Toast.LENGTH_SHORT).show();
        Intent stateData = new Intent(this, StateData.class);

        states item = stateWiseList.get(position);

        stateData.putExtra(STATE_NAME, item.getStateName());
        stateData.putExtra(STATE_CONFIRMED, item.getConfirmedCases());
        stateData.putExtra(STATE_ACTIVE, item.getActiveCases());
        stateData.putExtra(STATE_RECOVERED, item.getRecoveredCases());
        stateData.putExtra(STATE_DECEASED, item.getDeceasedCases());
        stateData.putExtra(STATE_CONFIRMED_NEW, item.getConfirmedCasesNew());
        stateData.putExtra(STATE_RECOVERED_NEW, item.getRecoveredCasesNew());
        stateData.putExtra(STATE_DECEASED_NEW, item.getDeceasedCasesNew());
        stateData.putExtra(DATE_AND_TIME, item.getDateAndTime());
        stateData.putExtra(STATE_ACTIVE_NEW,item.getActiveCasesNew()) ;


        startActivity(stateData);
    }
    private void filter(String text) {
        ArrayList<states> filteredList = new ArrayList<>();
        for (states item : stateWiseList) {
            if (item.getStateName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        isAdapter.filterList(filteredList);
    }
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(StatewiseView.this) ;
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(StatewiseView.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }
}
