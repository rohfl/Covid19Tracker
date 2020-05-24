package com.rohit.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.rohit.covid19tracker.CitywiseView.DISTRICT_NAME ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_ACTIVE ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_ACTIVE_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_CONFIRMED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_RECOVERED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_RECOVERED_NEW ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_DECEASED ;
import static com.rohit.covid19tracker.CitywiseView.DISTRICT_DECEASED_NEW ;



public class CityData extends AppCompatActivity {

    private TextView confirmedCases , confirmedCasesNew , activeCases , activeCasesNew , recoveredCases , recoveredCasesNew , deceasedCases , deceasedCasesNew , date ;
    private PieChart pieChart ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent() ;
        String districtName = intent.getStringExtra(DISTRICT_NAME) ;
        Objects.requireNonNull(getSupportActionBar()).setTitle(districtName);
        String active = intent.getStringExtra(DISTRICT_ACTIVE) ;
        String activeNew = intent.getStringExtra(DISTRICT_ACTIVE_NEW) ;
        String confirmed = intent.getStringExtra(DISTRICT_CONFIRMED) ;
        String confirmedNew = intent.getStringExtra(DISTRICT_CONFIRMED_NEW) ;
        String recovered = intent.getStringExtra(DISTRICT_RECOVERED) ;
        String recoveredNew = intent.getStringExtra(DISTRICT_RECOVERED_NEW) ;
        String deceased = intent.getStringExtra(DISTRICT_DECEASED) ;
        String deceasedNew = intent.getStringExtra(DISTRICT_DECEASED_NEW) ;
        confirmedCases = findViewById(R.id.confirmedCases) ;
        confirmedCasesNew = findViewById(R.id.confirmedCasesNew) ;
        activeCases = findViewById(R.id.activeCases) ;
        activeCasesNew = findViewById(R.id.activeCasesNew) ;
        recoveredCases = findViewById(R.id.recoveredCases) ;
        recoveredCasesNew = findViewById(R.id.recoveredCasesNew) ;
        deceasedCases = findViewById(R.id.deceasedCases) ;
        deceasedCasesNew = findViewById(R.id.deceasedCasesNew) ;
        date = findViewById(R.id.date) ;
        String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        pieChart = findViewById(R.id.pieChart) ;
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active), Color.parseColor("#D44545")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered), Color.parseColor("#51D562")));
        pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(deceased), Color.parseColor("#B0A9A9")));

        pieChart.startAnimation(); ;

        confirmedCases.setText(NumberFormat.getInstance().format(Integer.parseInt(confirmed))) ;
        confirmedCasesNew.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(confirmedNew))) ;
        activeCases.setText(NumberFormat.getInstance().format(Integer.parseInt(active))) ;
        activeCasesNew.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(activeNew))) ;
        recoveredCases.setText(NumberFormat.getInstance().format(Integer.parseInt(recovered))) ;
        recoveredCasesNew.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(recoveredNew))) ;
        deceasedCases.setText(NumberFormat.getInstance().format(Integer.parseInt(deceased))) ;
        deceasedCasesNew.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(deceasedNew))) ;
        date.setText(date1) ;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
