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
        this.adminManager = new AdminManager(adminDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    public void deleteUser(Integer userId){
        adminManager.deleteAdminUser(userId);
        doctorManager.deleteDoctor(userId);
        patientManager.deletePatient(userId);
        secretaryManager.deleteSecretary(userId);
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
