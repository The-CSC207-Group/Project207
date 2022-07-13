package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;
import entities.Patient;
import entities.Secretary;
import entities.User;

public class SecretaryManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;

    GenericUserManagerMethods<Secretary> secretaryMethods;

    public SecretaryManager(DataMapperGateway<Secretary> secretaryDatabase){
        this.secretaryDatabase = secretaryDatabase;
        this.secretaryMethods = new GenericUserManagerMethods<>(secretaryDatabase);
    }

    public Integer createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        return secretaryDatabase.add(secretary);
    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        secretaryMethods.changePassword(IDUser, newPassword);
    }

    public void deleteSecretary(Integer idUser){
        secretaryMethods.deleteUser(idUser);
    }
    public Secretary getSecretary(Integer idUser){
        return secretaryMethods.getUser(idUser);
    }


}
