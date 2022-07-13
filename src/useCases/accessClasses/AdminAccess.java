package useCases.accessClasses;

import dataBundles.LogDataBundle;
import dataBundles.ContactDataBundle;
import dataBundles.PatientDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.*;
import useCases.managers.AdminManager;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;
import useCases.managers.SecretaryManager;
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
    DataMapperGateway<Contact> contactDatabase;
    AdminManager adminManager;
    PatientManager patientManager;
    DoctorManager doctorManager;
    SecretaryManager secretaryManager;
    LogManager logManager;


    public AdminAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor> doctorDatabase,
    DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact>
                       contactDatabase, DataMapperGateway<Log> logDatabase) {
        this.adminManager = new AdminManager(adminDatabase, contactDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
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
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle, String healthNumber){
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);

    }
    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        if (secretaryManager.getSecretary(userId) != null){return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogs());}
        if (doctorManager.getDoctor(userId) != null){return logManager.getLogDataBundlesFromLogIDs(doctorManager.getDoctor(userId).getLogs());}
        if (adminManager.getAdmin(userId) != null){return logManager.getLogDataBundlesFromLogIDs(adminManager.getAdmin(userId).getLogs());}
        return null;
    }


}
