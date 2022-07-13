package useCases.managers;

import dataBundles.ReportDataBundle;
import database.DataMapperGateway;
import entities.Patient;

import java.util.ArrayList;

public class PatientManager {
    DataMapperGateway<Patient> patientDatabase;
    GenericUserManagerUtilities<Patient> patientUtilities;

    public PatientManager(DataMapperGateway<Patient> patientDatabase){
        this.patientDatabase = patientDatabase;
        this.patientUtilities = new GenericUserManagerUtilities<>(patientDatabase);
    }

    public boolean createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        Integer user_id = patientDatabase.add(patient);
        return user_id != null;
    }
    public void changeUserPassword(Integer IDUser, String newPassword){
        patientUtilities.changePassword(IDUser, newPassword);
    }

}
