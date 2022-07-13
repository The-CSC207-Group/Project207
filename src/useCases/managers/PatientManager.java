package useCases.managers;

import database.DataMapperGateway;
import entities.Patient;
import entities.User;

public class PatientManager{
    DataMapperGateway<Patient> patientDatabase;
    GenericUserManagerMethods<Patient> patientMethods;

    public PatientManager(DataMapperGateway<Patient> patientDatabase){
        this.patientDatabase = patientDatabase;
        this.patientMethods = new GenericUserManagerMethods<>(patientDatabase);
    }

    public Integer createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        return patientDatabase.add(patient);

    }
    public void changeUserPassword(Integer IDUser, String newPassword){
        patientMethods.changePassword(IDUser, newPassword);
    }
    public void deletePatient(Integer idUser){
        patientMethods.deleteUser(idUser);
    }

    public Patient getPatient(Integer idUser){
        return patientMethods.getUser(idUser);
    }

}
