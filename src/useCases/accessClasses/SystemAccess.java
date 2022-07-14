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
     * @param username          String new username
     * @param password          String new password
     * @param contactDataBundle ContactDataBundle which includes contact info of the user.
     * @return PatientDataBundle which includes information of the patient.
     */
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber) {
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);
    }

    /**
     * @param userID Integer userID of the user.
     * @return Enum of the user type, exception if the user is not in the database.
     */
    public userType getType(Integer userID) throws Exception {
        ArrayList<DataMapperGateway<? extends User>> userDatabases = new ArrayList<>(Arrays.asList(patientDatabase,
                doctorDatabase, secretaryDatabase, adminDatabase));
        for (DataMapperGateway<? extends User> database : userDatabases) {
            if (database.getAllIds().contains(userID)) {
                String type = database.get(userID).getClass().getName();
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

    /**
     * @param userID   Integer userID of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public DoctorDataBundle doctorSignIn(Integer userID, String password) {
        Doctor doctor = doctorDatabase.get(userID);
        if (doctor.comparePassword(password)) {
            attachUserSignInLog(doctorDatabase, userID);
            return new DoctorDataBundle(userID, doctor);
        }
        return null;
    }

    /**
     * @param userID   Integer userID of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public PatientDataBundle patientSignIn(Integer userID, String password) {
        Patient patient = patientDatabase.get(userID);
        if (patient.comparePassword((password))) {
            attachUserSignInLog(patientDatabase, userID);
            return new PatientDataBundle(userID, patient);
        }
        return null;
    }

    /**
     * @param userID   Integer userID of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public SecretaryDataBundle secretarySignIn(Integer userID, String password) {
        Secretary secretary = secretaryDatabase.get(userID);
        if (secretary.comparePassword(password)) {
            attachUserSignInLog(secretaryDatabase, userID);
            return new SecretaryDataBundle(userID, secretary);
        }
        return null;
    }

    /**
     * @param userID   Integer userID of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public AdminDataBundle adminSignIn(Integer userID, String password) {
        Admin admin = adminDatabase.get(userID);
        if (admin.comparePassword(password)) {
            attachUserSignInLog(adminDatabase, userID);
            return new AdminDataBundle(userID, admin);
        }
        return null;
    }

    private <T extends User> void attachUserSignInLog(DataMapperGateway<T> database, Integer iDUser) {
        T user = database.get(iDUser);
        Integer iDLog = logManager.addLog(user.getUsername() + " signed in").getId();
        user.addLog(iDLog);
    }


}


