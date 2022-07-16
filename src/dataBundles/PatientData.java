package dataBundles;

import entities.Patient;

import java.util.ArrayList;

public class PatientData extends UserData<Patient> {
    private final Patient patient;

    public PatientData(Patient patient) {
        super(patient);
        this.patient = patient;
    }

    public String getUsername() {
        return patient.getUsername();
    }

    public Integer getContact() {
        return patient.getContactInfoId();
    }


    public String getHealthNumber() {
        return patient.getHealthNumber();
    }

    public Integer getId() {
        return patient.getId();
    }

    public String getPatientUsername(){
        return patient.getUsername();
    }


}
