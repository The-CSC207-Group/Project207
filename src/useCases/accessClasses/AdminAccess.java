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
import utilities.DatabaseQueryUtility;

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

    private DatabaseQueryUtility databaseQueryUtility = new DatabaseQueryUtility();


    /**
     * @param patientDatabase   database for patients.
     * @param doctorDatabase    database for doctors.
     * @param secretaryDatabase database for secretaries.
     * @param adminDatabase     database for admins.
     * @param contactDatabase   database for contacts.
     * @param logDatabase       database for logs.
     */
    public AdminAccess(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor> doctorDatabase,
                       DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact>
                               contactDatabase, DataMapperGateway<Log> logDatabase) {
        this.patientDatabase = patientDatabase;
        this.doctorDatabase = doctorDatabase;
        this.secretaryDatabase = secretaryDatabase;
        this.adminDatabase = adminDatabase;
        this.adminManager = new AdminManager(adminDatabase, contactDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    /**
     * @param userId Integer userID of the user being deleted. Can delete from admin, doctor, patient and secretary
     * databases.
     */
    public void deleteUser(Integer userId) {
        adminManager.deleteAdminUser(userId);
        doctorManager.deleteDoctor(userId);
        patientManager.deletePatient(userId);
        secretaryManager.deleteSecretary(userId);
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
     * Change the password of any user given their username. If the userId is not associated with a user any user database,
     * nothing happens.
     * @param username username of user.
     * @param newPassword new password of the doctor;
     */
    public void changePassword(String username, String newPassword){
        changePassUsingUsername(patientDatabase, username, newPassword);
        changePassUsingUsername(secretaryDatabase, username, newPassword);
        changePassUsingUsername(doctorDatabase, username, newPassword);
        changePassUsingUsername(adminDatabase, username, newPassword);
    }

    /**
     * Gets an arraylist of log data bundles associated with a username. Can get logs from admins, patients, secretaries and doctors.
     * @param username - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public ArrayList<LogDataBundle> getLogs(String username) {

        ArrayList<LogDataBundle> dataBundlesPatient =  logManager.getLogDataBundlesFromUsername(username, patientDatabase);
        if (dataBundlesPatient != null){return dataBundlesPatient;}

        ArrayList<LogDataBundle> dataBundlesSecretary = logManager.getLogDataBundlesFromUsername(username, secretaryDatabase);
        if (dataBundlesSecretary != null){return dataBundlesSecretary;}

        ArrayList<LogDataBundle> dataBundlesDoctor = logManager.getLogDataBundlesFromUsername(username, doctorDatabase);
        if (dataBundlesDoctor != null){return dataBundlesDoctor;}

        ArrayList<LogDataBundle> dataBundlesAdmin = logManager.getLogDataBundlesFromUsername(username, adminDatabase);
        if (dataBundlesAdmin != null){return dataBundlesAdmin;}
        return null;
    }

    private <T extends User> void changePassUsingUsername(DataMapperGateway<T> database, String username, String newPassword){
        User user = databaseQueryUtility.getUserByUsername(database, username);
        if (user != null){ user.setPassword(newPassword);}
    }


}
