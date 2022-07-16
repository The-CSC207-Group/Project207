package dataBundles;

import entities.Patient;

import java.util.ArrayList;

public class PatientData {
    private final Patient patient;

    public PatientData(Patient patient) {
        this.patient = patient;
    }

    public String getUsername(){
        return patient.getUsername();
    }

    public int getContact(){
        return patient.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return patient.getLogIds();
    }



    public String getHealthNumber() {
        return patient.getHealthNumber();
    }

    public ArrayList<Integer> getReports() {
        return patient.getReportIds();
    }

    public Integer getId(){
        return patient.getId();
    }


}
