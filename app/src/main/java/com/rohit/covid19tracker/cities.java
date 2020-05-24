package com.rohit.covid19tracker;

import java.io.Serializable;

public class cities implements Serializable {

    private String districtName , confirmedCases , activeCases , recoveredCases , deceasedCases , confirmedCasesNew , activeCasesNew , recoveredCasesNew , deceasedCasesNew ;

    public String getDistrictName() {
        return districtName;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public String getDeceasedCases() {
        return deceasedCases;
    }

    public String getConfirmedCasesNew() {
        return confirmedCasesNew;
    }

    public String getActiveCasesNew() {
        return activeCasesNew;
    }

    public String getRecoveredCasesNew() {
        return recoveredCasesNew;
    }

    public String getDeceasedCasesNew() {
        return deceasedCasesNew;
    }

    public cities(String districtName, String confirmedCases, String activeCases, String recoveredCases, String deceasedCases, String confirmedCasesNew, String activeCasesNew, String recoveredCasesNew, String deceasedCasesNew) {
        this.districtName = districtName;
        this.confirmedCases = confirmedCases;
        this.activeCases = activeCases;
        this.recoveredCases = recoveredCases;
        this.deceasedCases = deceasedCases;
        this.confirmedCasesNew = confirmedCasesNew;
        this.activeCasesNew = activeCasesNew;
        this.recoveredCasesNew = recoveredCasesNew;
        this.deceasedCasesNew = deceasedCasesNew;
    }
}
