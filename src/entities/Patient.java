package entities;

import java.util.ArrayList;

public class Patient extends User {

    // variables

    private String healthNumber;
    private ArrayList<Report> reports = new ArrayList<>();

    // constructors

    public Patient(String username, String password, Contact contactInfo, String healthNumber) {
        super(username, password, "patient", contactInfo);
        this.healthNumber = healthNumber;
    }

    // methods

    public String getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        this.reports.add(report);
    }

    public void removeReport(Report report) {
        this.reports.remove(report);
    }
}
