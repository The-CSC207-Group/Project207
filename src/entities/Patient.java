package entities;

import java.util.ArrayList;

public class Patient extends User {

    private String healthNumber;
    private ArrayList<Integer> reportIds = new ArrayList<>();

    public Patient(String username, String password, Integer contactInfoId, String healthNumber) {
        super(username, password, contactInfoId);
        this.healthNumber = healthNumber;
    }

    public Patient(String username, String password){
        super(username, password, new Contact().getId());
    }

    public String getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

    public ArrayList<Integer> getReportIds() {
        return reportIds;
    }

    public void addReportId(Integer reportId) {
        this.reportIds.add(reportId);
    }

    public void removeReportId(Integer reportId) {
        this.reportIds.remove(reportId);
    }
}
