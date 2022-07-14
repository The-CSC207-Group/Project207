package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;

import entities.*;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SystemAccess {

    private final DataMapperGateway<Patient> patientDatabase;
    private final DataMapperGateway<Admin> adminDatabase;
    private final DataMapperGateway<Secretary> secretaryDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;
    private final PatientManager patientManager;

    private final LogManager logManager;

    private enum userType {
        patient, admin, secretary, doctor
    }


    public SystemAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Admin> adminDatabase,
                        DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Doctor> doctorDatabase,
                        DataMapperGateway<Contact> contactDatabase, DataMapperGateway<Log> logDatabase) {
        this.patientDatabase = patientDatabase;
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.adminDatabase = adminDatabase;
        this.secretaryDatabase = secretaryDatabase;
        this.doctorDatabase = doctorDatabase;
        this.logManager = new LogManager(logDatabase);
    }


    /**
     * @param username    new username
     * @param password    new password
     * @param contactDataBundle contact info of user created
     * @return true if account has been created, false if account failed to create
     */
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber) {
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);
    }

    /**
     * @param userId username of the user trying to sign in
     * @return boolean, true if user can sign in, false if not
     */
    public userType getType(Integer userId) throws Exception {
        ArrayList<DataMapperGateway<? extends User>> userDatabases = new ArrayList<>(Arrays.asList(patientDatabase,
                doctorDatabase, secretaryDatabase, adminDatabase));
        for (DataMapperGateway<? extends User> database : userDatabases) {
            if (database.getAllIds().contains(userId)) {
                String type = database.get(userId).getClass().getName();
                switch (type) {
                    case "Admin":
                        return userType.admin;
                    case "Secretary":
                        return userType.secretary;
                    case "Patient":
                        return userType.patient;
                    case "Doctor":
                        return userType.doctor;
                }
            }
        }
        throw new Exception("user not in databases");
    }

    public DoctorDataBundle doctorSignIn(Integer userId, String password){
        Doctor doctor = doctorDatabase.get(userId);
        if (doctor.comparePassword(password)){
            attachUserSignInLog(doctorDatabase, userId);
            return new DoctorDataBundle(userId, doctor);
        }
        return null;
    }

    public PatientDataBundle patientSignIn(Integer userId, String password){
        Patient patient = patientDatabase.get(userId);
        if (patient.comparePassword((password))){
            attachUserSignInLog(patientDatabase, userId);
            return new PatientDataBundle(userId, patient);
        }
        return null;
    }

    public SecretaryDataBundle secretarySignIn(Integer userId, String password){
        Secretary secretary = secretaryDatabase.get(userId);
        if (secretary.comparePassword(password)){
            attachUserSignInLog(secretaryDatabase, userId);
            return new SecretaryDataBundle(userId, secretary);
        }
        return null;
    }

    public AdminDataBundle adminSignIn(Integer userId, String password){
        Admin admin = adminDatabase.get(userId);
        if (admin.comparePassword(password)){
            attachUserSignInLog(adminDatabase, userId);
            return new AdminDataBundle(userId, admin);
        }
        return null;
    }

    private <T extends User> void attachUserSignInLog(DataMapperGateway<T> database, Integer userId){
        T user = database.get(userId);
        Integer iDLog = logManager.addLog( user.getUsername() + " signed in").getId();
        user.addLog(iDLog);
    }



}


