package useCases.accessClasses;

import dataBundles.ContactDataBundle;
import dataBundles.PatientDataBundle;
import database.DataMapperGateway;
import entities.*;
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


    public AdminAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor> doctorDatabase,
    DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact>
                       contactDatabase){
        this.adminManager = new AdminManager(adminDatabase, contactDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);

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

}
