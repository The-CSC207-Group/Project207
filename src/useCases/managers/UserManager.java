package useCases.managers;

import database.DataMapperGateway;
import entities.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class UserManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Admin> adminDatabase;

    ArrayList<DataMapperGateway<? extends User>> databases;

    public boolean createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        Integer user_id = patientDatabase.add(patient);
        return user_id != null;
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        Integer user_id = doctorDatabase.add(doctor);
        return user_id != null;
    }

    public boolean createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        Integer user_id = secretaryDatabase.add(secretary);
        return user_id != null;
    }

    public boolean createAdmin(String username, String password, int contactInfo){
        Admin admin = new Admin(username, password, contactInfo);
        Integer user_id = adminDatabase.add(admin);
        return user_id != null;
    }

    public boolean deleteUser(Integer userID){
        ArrayList<DataMapperGateway<? extends  User>> userDatabases = new ArrayList<>(Arrays.asList(patientDatabase,
                doctorDatabase, secretaryDatabase, adminDatabase));
       for (DataMapperGateway<? extends User> database : userDatabases){
           if (database.getAllIds().contains(userID)){
               return database.remove(userID);
           }
       }
       return false;
    }

    public void addLogIdToUserLogs(Integer userId, Integer logId){
        User user = getUserWithId(userId);
        user.addLog(logId);
    }
    public void removeLogIdFromUserLogs(Integer userId, Integer logId){
        User user = getUserWithId(userId);
        user.getLogs().remove(logId);
    }
    private User getUserWithId(Integer userId){
        User user = null;
        for (DataMapperGateway<? extends User> database : databases){
            user = database.get(userId);
            if (user != null){return user;}
        }
        return null;
    }

}
