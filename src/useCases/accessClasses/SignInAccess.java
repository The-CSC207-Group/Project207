package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;

import database.Database;
import entities.*;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SignInAccess {

    private final DataMapperGateway<Patient> patientDatabase;
    private final DataMapperGateway<Admin> adminDatabase;
    private final DataMapperGateway<Secretary> secretaryDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;
    private final PatientManager patientManager;

    private final LogManager logManager;


    private enum userType {
        patient, admin, secretary, doctor
    }

    /**
     */
    public SignInAccess(Database database) {
        this.patientDatabase = database.getPatientDatabase();
        this.patientManager = new PatientManager(database);
        this.adminDatabase = database.getAdminDatabase();
        this.secretaryDatabase = database.getSecretaryDatabase();
        this.doctorDatabase = database.getDoctorDatabase();
        this.logManager = new LogManager(database);
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
        return patientManager.createPatient(username, password);
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
     * @param username String username of the user trying to sign in
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public DoctorData doctorSignIn(String username, String password) {
        Doctor doctor = doctorDatabase.getByCondition(user -> user.getUsername().equals(username));
        if (doctor == null || !doctor.comparePassword(password)) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

    /**
     * Sign In method for Patients.
     * @param username String username of the user trying to sign in
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public PatientData patientSignIn(String username, String password) {
        Patient patient = patientDatabase.getByCondition(user -> user.getUsername().equals(username));
        if (patient == null || !patient.comparePassword(password)) {
            return null;
        } else {
            return new PatientData(patient);
        }
    }

    /**
     * Sign In method for Secretaries.
     *
     * @param username String username of the user trying to sign in
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public SecretaryData secretarySignIn(String username, String password) {
        Secretary secretary = secretaryDatabase.getByCondition(user -> user.getUsername().equals(username));
        if (secretary == null || !secretary.comparePassword(password)) {
            return null;
        } else {
            return new SecretaryData(secretary);
        }
    }

    /**
     * Sign In method for admins.
     * @param username String username of the user trying to sign in
     * @param password String password of the user trying to sign in
     * @return DoctorDataBundle if sign in is successful, or else return null.
     */
    public AdminData adminSignIn(String username, String password) {
        Admin admin = adminDatabase.getByCondition(user -> user.getUsername().equals(username));
        if (admin == null || !admin.comparePassword(password)) {
            return null;
        } else {
            return new AdminData(admin);
        }
    }

}


