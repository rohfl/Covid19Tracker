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

import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_NAME ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_ACTIVE ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_CONFIRMED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_CONFIRMED_NEW ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_RECOVERED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_DECEASED ;
import static com.rohit.covid19tracker.CountrywiseView.COUNTRY_DECEASED_NEW ;
public class CountryData extends AppCompatActivity {
    TextView activeCases , confirmedCases , confirmedCasesNew , deceasedCases , deceasedCasesNew , recoveredCases , date1;
    PieChart pieChart ;
    String date ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);
        Intent intent = getIntent() ;
        String countryName = intent.getStringExtra(COUNTRY_NAME) ;
        Objects.requireNonNull(getSupportActionBar()).setTitle(countryName);
        String countryConfirmed = intent.getStringExtra(COUNTRY_CONFIRMED) ;
        String countryConfirmedNew = intent.getStringExtra(COUNTRY_CONFIRMED_NEW) ;
        String countryActive = intent.getStringExtra(COUNTRY_ACTIVE) ;
        String countryRecovered = intent.getStringExtra(COUNTRY_RECOVERED) ;
        String countryDeceased = intent.getStringExtra(COUNTRY_DECEASED) ;
        String countryDeceasedNew = intent.getStringExtra(COUNTRY_DECEASED_NEW) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        confirmedCases = findViewById(R.id.confirmedCases) ;
        confirmedCasesNew = findViewById(R.id.confirmedCasesNew) ;
        activeCases = findViewById(R.id.activeCases) ;
        recoveredCases = findViewById(R.id.recoveredCases) ;
        deceasedCases = findViewById(R.id.deceasedCases) ;
        deceasedCasesNew = findViewById(R.id.deceasedCasesNew) ;

        pieChart = findViewById(R.id.pieChart) ;

        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(countryActive), Color.parseColor("#D44545")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(countryRecovered), Color.parseColor("#51D562")));
        pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(countryDeceased), Color.parseColor("#B0A9A9")));

        pieChart.startAnimation() ;

        int tempActive = Integer.parseInt(countryActive) ;
        countryActive = NumberFormat.getInstance().format(tempActive);
        int tempConfirmed = Integer.parseInt(countryConfirmed) ;
        countryConfirmed = NumberFormat.getInstance().format(tempConfirmed) ;
        int tempRecovered = Integer.parseInt(countryRecovered) ;
        countryRecovered = NumberFormat.getInstance().format(tempRecovered) ;
        int tempDeceased = Integer.parseInt(countryDeceased) ;
        countryDeceased = NumberFormat.getInstance().format(tempDeceased) ;
        int tempConfirmedNew = Integer.parseInt(countryConfirmedNew) ;
        countryConfirmedNew = NumberFormat.getInstance().format(tempConfirmedNew) ;
        int tempDeceasedNew = Integer.parseInt(countryDeceasedNew) ;
        countryDeceasedNew = NumberFormat.getInstance().format(tempDeceasedNew) ;

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        confirmedCases.setText(countryConfirmed) ;
        confirmedCasesNew.setText("+"+countryConfirmedNew) ;
        activeCases.setText(countryActive) ;
        recoveredCases.setText(countryRecovered) ;
        deceasedCases.setText(countryDeceased) ;
        deceasedCasesNew.setText("+"+countryDeceasedNew) ;
        date1 = findViewById(R.id.date) ;
        date1.setText(date) ;


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
