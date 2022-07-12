package useCases;

import database.DataMapperGateway;
import entities.*;

import java.util.ArrayList;
import java.util.HashSet;


public class UserManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Admin> adminDatabase;

    ArrayList<DataMapperGateway<? extends User>> databases;

    public boolean createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        patientDatabase.add(patient);
        HashSet<Integer> all_ids  = patientDatabase.getAllIds();
        return all_ids.contains(patient.getId());
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        doctorDatabase.add(doctor);
        HashSet<Integer> all_ids = doctorDatabase.getAllIds();
        return all_ids.contains(doctor.getId());
    }

    public boolean createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        secretaryDatabase.add(secretary);
        HashSet<Integer> all_ids = secretaryDatabase.getAllIds();
        return all_ids.contains(secretary.getId());
    }

    public boolean createAdmin(String username, String password, int contactInfo){
        Admin admin = new Admin(username, password, contactInfo);
        adminDatabase.add(admin);
        HashSet<Integer> all_ids = adminDatabase.getAllIds();
        return all_ids.contains(admin.getId());
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
