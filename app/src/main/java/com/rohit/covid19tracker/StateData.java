package com.rohit.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.Objects;
import static com.rohit.covid19tracker.StatewiseView.DATE_AND_TIME ;
import static com.rohit.covid19tracker.StatewiseView.STATE_ACTIVE ;
import static com.rohit.covid19tracker.StatewiseView.STATE_CONFIRMED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_DECEASED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_DECEASED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_NAME ;
import static com.rohit.covid19tracker.StatewiseView.STATE_RECOVERED ;
import static com.rohit.covid19tracker.StatewiseView.STATE_RECOVERED_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_ACTIVE_NEW ;
import static com.rohit.covid19tracker.StatewiseView.STATE_NAME_FOR_DISTRICT;

public class StateData extends AppCompatActivity {
    private TextView confirmedCases , confirmedCasesNew , activeCases , activeCasesNew , recoveredCases , recoveredCasesNew , deceasedCases , deceasedCasesNew , dateAndTime ;
    private PieChart pieChart ;
    public String state ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_data);
        Intent intent = getIntent() ;
        String stateName = intent.getStringExtra(STATE_NAME) ;
        state = stateName ;
        Objects.requireNonNull(getSupportActionBar()).setTitle(stateName);
        String stateConfirmed = intent.getStringExtra(STATE_CONFIRMED) ;
        String stateConfirmedNew = intent.getStringExtra(STATE_CONFIRMED_NEW) ;
        String stateActive = intent.getStringExtra(STATE_ACTIVE) ;
        String stateActiveNew = intent.getStringExtra(STATE_ACTIVE_NEW) ;
        String stateRecovered = intent.getStringExtra(STATE_RECOVERED) ;
        String stateRecoveredNew = intent.getStringExtra(STATE_RECOVERED_NEW) ;
        String stateDeceased = intent.getStringExtra(STATE_DECEASED) ;
        String stateDeceasedNew = intent.getStringExtra(STATE_DECEASED_NEW) ;
        String dateAndTime1 = intent.getStringExtra(DATE_AND_TIME) ;

        confirmedCases = findViewById(R.id.confirmedCases) ;
        confirmedCasesNew = findViewById(R.id.confirmedCasesNew) ;
        activeCases = findViewById(R.id.activeCases) ;
        activeCasesNew = findViewById(R.id.activeCasesNew) ;
        recoveredCases = findViewById(R.id.recoveredCases) ;
        recoveredCasesNew = findViewById(R.id.recoveredCasesNew) ;
        deceasedCases = findViewById(R.id.deceasedCases) ;
        deceasedCasesNew = findViewById(R.id.deceasedCasesNew) ;
        dateAndTime = findViewById(R.id.dateAndTime) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pieChart = findViewById(R.id.pieChart) ;

        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(stateActive), Color.parseColor("#D44545")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(stateRecovered), Color.parseColor("#51D562")));
        pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(stateDeceased), Color.parseColor("#B0A9A9")));

        pieChart.startAnimation() ;

        int tempActive = Integer.parseInt(stateActive) ;
        stateActive = NumberFormat.getInstance().format(tempActive);
        int tempConfirmed = Integer.parseInt(stateConfirmed) ;
        stateConfirmed = NumberFormat.getInstance().format(tempConfirmed) ;
        int tempRecovered = Integer.parseInt(stateRecovered) ;
        stateRecovered = NumberFormat.getInstance().format(tempRecovered) ;
        int tempDeceased = Integer.parseInt(stateDeceased) ;
        stateDeceased = NumberFormat.getInstance().format(tempDeceased) ;
        int tempActiveNew = Integer.parseInt(stateActiveNew) ;
        stateActiveNew = NumberFormat.getInstance().format(tempActiveNew);
        int tempConfirmedNew = Integer.parseInt(stateConfirmedNew) ;
        stateConfirmedNew = NumberFormat.getInstance().format(tempConfirmedNew) ;
        int tempRecoveredNew = Integer.parseInt(stateRecoveredNew) ;
        stateRecoveredNew = NumberFormat.getInstance().format(tempRecoveredNew) ;
        int tempDeceasedNew = Integer.parseInt(stateDeceasedNew) ;
        stateDeceasedNew = NumberFormat.getInstance().format(tempDeceasedNew) ;

        MainActivity obj = new MainActivity() ;
        dateAndTime1 = obj.formatDate(dateAndTime1,0) ;

        confirmedCases.setText(stateConfirmed) ;
        confirmedCasesNew.setText("+"+stateConfirmedNew) ;
        activeCases.setText(stateActive) ;
        activeCasesNew.setText("+"+stateActiveNew) ;
        recoveredCases.setText(stateRecovered) ;
        recoveredCasesNew.setText("+"+stateRecoveredNew) ;
        deceasedCases.setText(stateDeceased) ;
        deceasedCasesNew.setText("+"+stateDeceasedNew) ;
        dateAndTime.setText(dateAndTime1);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    public void openCityWise (View view) {
        Intent intent = new Intent(this,CitywiseView.class) ;
        intent.putExtra(STATE_NAME_FOR_DISTRICT,state) ;
        startActivity(intent) ;
    }
}
