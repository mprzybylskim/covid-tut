package mprzybylskim.tutorials.covid19tracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int totalCases;
    private int differenceADay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getDifferenceADay() {
        return differenceADay;
    }

    public void setDifferenceADay(int differenceADay) {
        this.differenceADay = differenceADay;
    }
}
