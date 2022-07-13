package dataBundles;

import entities.Patient;

import java.util.ArrayList;

public class PatientDataBundle extends DataBundle {
    private final Patient patient;

    public PatientDataBundle(Integer id, Patient patient) {
        super(id);
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
