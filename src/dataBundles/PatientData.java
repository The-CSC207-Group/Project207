package dataBundles;

import entities.Patient;

import java.util.ArrayList;

public class PatientData extends UserData<Patient> {
    private final Patient patient;

    public PatientData(Patient patient) {
        super(patient);
        this.patient = patient;
    }

    public String getHealthNumber() {
        return patient.getHealthNumber();
    }

}
