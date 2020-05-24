package com.rohit.covid19tracker;

public class countries {
    private String countryName , confirmedCases , confirmedCasesNew , recoveredCases , deceasedCases , deceasedCasesNew , activeCases ;
    private String imageURL ;

    public countries(String countryName, String confirmedCases, String confirmedCasesNew, String recoveredCases, String deceasedCases, String deceasedCasesNew, String activeCases, String imageURL) {
        this.countryName = countryName;
        this.confirmedCases = confirmedCases;
        this.confirmedCasesNew = confirmedCasesNew;
        this.recoveredCases = recoveredCases;
        this.deceasedCases = deceasedCases;
        this.deceasedCasesNew = deceasedCasesNew;
        this.activeCases = activeCases;
        this.imageURL = imageURL;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public String getConfirmedCasesNew() {
        return confirmedCasesNew;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public String getDeceasedCases() {
        return deceasedCases;
    }

    public String getDeceasedCasesNew() {
        return deceasedCasesNew;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getImageURL() {
        return imageURL;
    }
}
