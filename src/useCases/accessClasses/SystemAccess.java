package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;

import database.Database;
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

    private AccessCommonMethods commonMethods = new AccessCommonMethods();

    private enum userType {
        patient, admin, secretary, doctor
    }

    /**
     */
    public SystemAccess(Database database) {
        this.patientDatabase = database.getPatientDatabase();
        this.patientManager = new PatientManager(database);
        this.adminDatabase = database.getAdminDatabase();
        this.secretaryDatabase = database.getSecretaryDatabase();
        this.doctorDatabase = database.getDoctorDatabase();
        this.logManager = new LogManager(database.getLogDatabase());
    }


    /**
     * Creates a new Patient object and stores it in the database, returns PatientDataBundle.
     * @param username          String new username
     * @param password          String new password
     * @param contactData ContactDataBundle which includes contact info of the user. Cannot be null.
     * @param healthNumber      Int Health number of the patient being created.
     * @return PatientDataBundle which includes information of the patient.
     */
    public PatientData createPatient(String username, String password, ContactData contactData,
                                     String healthNumber) {
        return patientManager.createPatient(username, password, contactData, healthNumber);
    }

    /**
     * Return the type of the given userId.
     * @param userId Integer userID of the user.
     * @return Enum of the user type, exception if the user is not in the database.
     */
    public userType getType(Integer userId) {
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
        return null;
    }

    /**
     * Sign In method for doctors.
     * @param userId   Integer userId of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public DoctorData doctorSignIn(Integer userId, String password) {
        Doctor doctor = doctorDatabase.get(userId);
        if (doctor.comparePassword(password)) {
            commonMethods.attachUserSignInLog(doctorDatabase, userId, logManager);
            return new DoctorData(doctor);
        }
        return null;
    }

    /**
     * Sign In method for Patients.
     * @param userId   Integer userId of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public PatientData patientSignIn(Integer userId, String password) {
        Patient patient = patientDatabase.get(userId);
        if (patient.comparePassword((password))) {
            commonMethods.attachUserSignInLog(patientDatabase, userId, logManager);
            return new PatientData(patient);
        }
        return null;
    }

    /**
     * Sign In method for Secretaries.
     * @param userId   Integer userId of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public SecretaryData secretarySignIn(Integer userId, String password) {
        Secretary secretary = secretaryDatabase.get(userId);
        if (secretary.comparePassword(password)) {
            commonMethods.attachUserSignInLog(secretaryDatabase, userId, logManager);
            return new SecretaryData(userId, secretary);
        }
        return null;
    }

    /**
     * Sign In method for admins.
     * @param userId   Integer userId of the user
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public AdminData adminSignIn(Integer userId, String password) {
        Admin admin = adminDatabase.get(userId);
        if (admin.comparePassword(password)) {
            commonMethods.attachUserSignInLog(adminDatabase, userId, logManager);
            return new AdminData(admin);
        }
        return null;
    }


}


