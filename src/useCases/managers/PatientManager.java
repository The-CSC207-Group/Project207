package useCases.managers;

import database.DataMapperGateway;
import entities.Patient;

public class PatientManager {
    DataMapperGateway<Patient> patientDatabase;

    public boolean createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        Integer user_id = patientDatabase.add(patient);
        return user_id != null;
    }
}
