package useCases.accessClasses;

import dataBundles.LogDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.*;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.userQueryConditions.HasUserId;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminAccess {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Admin> adminDatabase;

    PatientManager patientManager;

    DoctorManager doctorManager;

    AdminManager adminManager;

    SecretaryManager secretaryManager;

    LogManager logManager;

    public AdminAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor> doctorDatabase,
    DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Log> logDatabase){
        this.adminDatabase = adminDatabase;
        this.doctorDatabase = doctorDatabase;
        this.patientDatabase = patientDatabase;
        this.secretaryDatabase = secretaryDatabase;
        this.patientManager = new PatientManager(patientDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase);
        this.adminManager = new AdminManager(adminDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    public void deleteUser(String userId){
        deleteUserFromDatabase(adminDatabase, userId);
        deleteUserFromDatabase(doctorDatabase, userId);
        deleteUserFromDatabase(patientDatabase, userId);
        deleteUserFromDatabase(secretaryDatabase, userId);
    }
    private <T extends User> void deleteUserFromDatabase(DataMapperGateway<T> database, String userId){
        Query<T> query = new Query<T>();
        ArrayList<QueryCondition<T>> queryConditions = new ArrayList<>();
        queryConditions.add(new HasUserId<>(userId, true));
        Integer id = query.returnFirstMeetingConditions(database, queryConditions).getId();
        database.remove(id);
    }
    public <T extends User> void createUser(){

    }
    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        if (secretaryManager.getSecretary(userId) != null){return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogs());}
        if (doctorManager.getDoctor(userId) != null){return logManager.getLogDataBundlesFromLogIDs(doctorManager.getDoctor(userId).getLogs());}
        if (adminManager.getAdmin(userId) != null){return logManager.getLogDataBundlesFromLogIDs(adminManager.getAdmin(userId).getLogs());}
        return null;
    }


}
