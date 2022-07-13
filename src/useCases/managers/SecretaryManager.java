package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;
import entities.Patient;
import entities.Secretary;
import entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class SecretaryManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;

    GenericUserManagerMethods<Secretary> secretaryUtilities;

    public SecretaryManager(DataMapperGateway<Secretary> secretaryDatabase){
        this.secretaryDatabase = secretaryDatabase;
        this.secretaryUtilities = new GenericUserManagerMethods<>(secretaryDatabase);
    }

    public Integer createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        return secretaryDatabase.add(secretary);
    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        secretaryUtilities.changePassword(IDUser, newPassword);
    }

    public void deleteSecretary(Integer idUser){
        secretaryUtilities.deleteUser(idUser);
    }
}
