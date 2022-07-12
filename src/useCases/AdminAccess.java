package useCases;

import database.DataMapperGateway;
import entities.*;
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

    public AdminAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor> doctorDatabase,
    DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Admin> adminDatabase){
        this.adminDatabase = adminDatabase;
        this.doctorDatabase = doctorDatabase;
        this.patientDatabase = patientDatabase;
        this.secretaryDatabase = secretaryDatabase;
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

}
