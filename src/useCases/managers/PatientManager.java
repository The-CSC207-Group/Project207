package useCases.managers;

import database.DataMapperGateway;
import entities.Patient;

public class PatientManager {
    DataMapperGateway<Patient> patientDatabase;
    GenericUserManagerMethods<Patient> patientUtilities;

    public PatientManager(DataMapperGateway<Patient> patientDatabase){
        this.patientDatabase = patientDatabase;
        this.patientUtilities = new GenericUserManagerMethods<>(patientDatabase);
    }

    public Integer createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        return patientDatabase.add(patient);

    }
    public void changeUserPassword(Integer IDUser, String newPassword){
        patientUtilities.changePassword(IDUser, newPassword);
    }
    public void deletePatient(Integer idUser){
        patientUtilities.deleteUser(idUser);
    }

    public Patient getPatient(Integer idUser){
        return patientUtilities.getUser(idUser);
    }
}
