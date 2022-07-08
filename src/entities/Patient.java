package entities;

import java.util.ArrayList;

public class Patient extends User {

    private String healthNumber;
    private ArrayList<Integer> reports = new ArrayList<>();

    public Patient(int id, String username, String password, int contactInfo, String healthNumber) {
        super(id, username, password, "patient", contactInfo);
        this.healthNumber = healthNumber;
    }

    public String getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

    public ArrayList<Integer> getReports() {
        return reports;
    }

    public void addReport(int reportID) {
        this.reports.add(reportID);
    }

    public void removeReport(int reportID) {
        this.reports.remove(reportID);
    }
}
