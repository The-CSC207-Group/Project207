package useCases;

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

    public boolean createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        Integer user_id = secretaryDatabase.add(secretary);
        return user_id != null;
    }

    public boolean deleteUser(Integer userID){
        ArrayList<DataMapperGateway<? extends User>> userDatabases = new ArrayList<>(Arrays.asList(patientDatabase,
                doctorDatabase, secretaryDatabase));
        for (DataMapperGateway<? extends User> database : userDatabases){
            if (database.getAllIds().contains(userID)){
                return database.remove(userID);
            }
        }
        return false;
    }
}
