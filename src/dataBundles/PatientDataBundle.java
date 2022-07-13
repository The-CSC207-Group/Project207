package dataBundles;

import entities.Patient;

import java.util.ArrayList;

public class PatientDataBundle {
    private final Patient patient;

    public PatientDataBundle(Patient patient) {
        this.patient = patient;
    }

    public String getUsername(){
        return patient.getUsername();
    }

    public int getContact(){
        return patient.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return patient.getLogs();
    }



    public String getHealthNumber() {
        return patient.getHealthNumber();
    }

    public ArrayList<Integer> getReports() {
        return patient.getReports();
    }


}
