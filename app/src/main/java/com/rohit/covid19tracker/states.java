package com.rohit.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class states {

    private String stateName , activeCases , confirmedCases , deceasedCases , recoveredCases , confirmedCasesNew , activeCasesNew , deceasedCasesNew , recoveredCasesNew ;
    private String dateAndTime ;
    public states(String stateName, String activeCases, String confirmedCases, String deceasedCases, String recoveredCases, String confirmedCasesNew, String activeCasesNew, String deceasedCasesNew, String recoveredCasesNew, String dateAndTime) {
        this.stateName = stateName;
        this.activeCases = activeCases;
        this.confirmedCases = confirmedCases;
        this.deceasedCases = deceasedCases;
        this.recoveredCases = recoveredCases;
        this.confirmedCasesNew = confirmedCasesNew;
        this.activeCasesNew = activeCasesNew;
        this.deceasedCasesNew = deceasedCasesNew;
        this.recoveredCasesNew = recoveredCasesNew;
        this.dateAndTime = dateAndTime ;
    }

    public String getStateName() {
        return stateName;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public String getDeceasedCases() {
        return deceasedCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public String getConfirmedCasesNew() {
        return confirmedCasesNew;
    }

    public String getActiveCasesNew() {
        return activeCasesNew;
    }

    public String getDeceasedCasesNew() {
        return deceasedCasesNew;
    }

    public String getRecoveredCasesNew() {
        return recoveredCasesNew;
    }

    public String getDateAndTime() {
        return dateAndTime ;
    }
}
