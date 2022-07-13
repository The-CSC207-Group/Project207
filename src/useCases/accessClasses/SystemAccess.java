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
    private final DataMapperGateway<Contact> contactDatabase;

    private final PatientManager patientManager;

    private final LogManager logManager;

    private enum userType {
        patient, admin, secretary, doctor
    }


    public SystemAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Admin> adminDatabase,
                        DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Doctor> doctorDatabase,
                        DataMapperGateway<Contact> contactDatabase, DataMapperGateway<Log> logDatabase) {
        this.patientDatabase = patientDatabase;
        this.patientManager = new PatientManager(patientDatabase);
        this.adminDatabase = adminDatabase;
        this.secretaryDatabase = secretaryDatabase;
        this.doctorDatabase = doctorDatabase;
        this.contactDatabase = contactDatabase;
        this.logManager = new LogManager(logDatabase);
    }


    /**
     * @param username    new username
     * @param password    new password
     * @param contactDataBundle contact info of user created
     * @return true if account has been created, false if account failed to create
     */
    public Integer createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                 String healthNumber) {
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        return patientManager.createPatient(username, password, contactId, healthNumber);
    }

    /**
     * @param userID username of the user trying to sign in
     * @return boolean, true if user can sign in, false if not
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

    public DoctorDataBundle doctorSignIn(Integer userID, String password){
        Doctor doctor = doctorDatabase.get(userID);
        if (doctor.comparePassword(password)){
            attachUserSignInLog(doctorDatabase, userID);
            return new DoctorDataBundle(userID, doctor);
        }
        return null;
    }

    public PatientDataBundle patientSignIn(Integer userID, String password){
        Patient patient = patientDatabase.get(userID);
        if (patient.comparePassword((password))){
            attachUserSignInLog(patientDatabase, userID);
            return new PatientDataBundle(userID, patient);
        }
        return null;
    }

    public SecretaryDataBundle secretarySignIn(Integer userID, String password){
        Secretary secretary = secretaryDatabase.get(userID);
        if (secretary.comparePassword(password)){
            attachUserSignInLog(secretaryDatabase, userID);
            return new SecretaryDataBundle(userID, secretary);
        }
        return null;
    }

    public AdminDataBundle adminSignIn(Integer userID, String password){
        Admin admin = adminDatabase.get(userID);
        if (admin.comparePassword(password)){
            attachUserSignInLog(adminDatabase, userID);
            return new AdminDataBundle(userID, admin);
        }
        return null;
    }

    private <T extends User> void attachUserSignInLog(DataMapperGateway<T> database, Integer iDUser){
        T user = database.get(iDUser);
        Integer iDLog = logManager.addLog( user.getUsername() + " signed in");
        user.addLog(iDLog);
    }

    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle){
        return new Contact(contactDataBundle.getName(),
                contactDataBundle.getEmail(),
                contactDataBundle.getPhoneNumber(),
                contactDataBundle.getAddress(),
                contactDataBundle.getBirthday(),
                contactDataBundle.getEmergencyContactName(),
                contactDataBundle.getEmergencyContactEmail(),
                contactDataBundle.getEmergencyContactPhoneNumber(),
                contactDataBundle.getEmergencyRelationship());
    }


}


