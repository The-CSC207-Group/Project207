package useCases.accessClasses;

import database.DataMapperGateway;
import entities.*;
import useCases.managers.PatientManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SystemAccess {

    private final DataMapperGateway<Patient> patientDatabase;
    private final DataMapperGateway<Admin> adminDatabase;
    private final DataMapperGateway<Secretary> secretaryDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;

    private final PatientManager patientManager;



    public SystemAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Admin> adminDatabase,
                        DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Doctor> doctorDatabase) {
        this.patientDatabase = patientDatabase;
        this.patientManager = new PatientManager(patientDatabase);
        this.adminDatabase = adminDatabase;
        this.secretaryDatabase = secretaryDatabase;
        this.doctorDatabase = doctorDatabase;
    }


    /**
     * @param username    new username
     * @param password    new password
     * @param contactInfo contact info of user created
     * @return true if account has been created, false if account failed to create
     */
    public boolean createPatient(String username, String password, int contactInfo, String healthNumber) {
        return patientManager.createPatient(username, password, contactInfo, healthNumber);
    }

    /**
     * @param userID   username of the user trying to sign in
     * @param password password of user trying to sign in
     * @return boolean, true if user can sign in, false if not
     */
    public boolean canSignIn(Integer userID, String password) {
        ArrayList<DataMapperGateway<? extends User>> userDatabases = new ArrayList<>(Arrays.asList(patientDatabase,
                doctorDatabase, secretaryDatabase, adminDatabase));
        for (DataMapperGateway<? extends User> database : userDatabases) {
            if (database.getAllIds().contains(userID)){
                User user = database.get(userID);
                return user != null && user.comparePassword(password);

            }
        }
        return false;
    }

}