package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import useCases.managers.*;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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


    public AdminAccess(Database database) {
        this.patientDatabase = database.getPatientDatabase();
        this.doctorDatabase = database.getDoctorDatabase();
        this.secretaryDatabase = database.getSecretaryDatabase();
        this.adminDatabase = database.getAdminDatabase();
        this.adminManager = new AdminManager(adminDatabase, contactDatabase);
        this.patientManager = new PatientManager(database);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
        this.logManager = new LogManager(database.getLogDatabase());
    }


    /**
     * @param username Integer userID of the user being deleted. Can delete from admin, doctor, patient and secretary
     * databases.
     */
    public boolean deletePatientUser(String username) {
        return adminManager.deleteUser(username);
    }

    /**
     * @param username          String new username
     * @param password          String new password
     */
    public PatientData createPatient(String username, String password) {
        return patientManager.createPatient(username, password);

    }
    public UserData createAcount(String username, String password, userType type){
        switch (type){
            case Admin: {
                return createAdmin(username, password);
            }
            case Patient: {
                return createPatient(username, password);
            }
            case Doctor: {
                return createDoctor(username, password);
            }
            case Secretary: {
                return createSecretary(username, password);
            }
        }
        throw new NoSuchElementException();
    }
    public DoctorData createDoctor (String username, String password){
        return doctorManager.createDoctor(username, password);
    }
    public SecretaryData createSecretary (String username, String password){
        return secretaryManager.createSecretary(username, password);
    }
    public boolean doesPatientExist(String patient_username){
        return patientManager.doesPatientExist(patient_username);
    }
    public boolean doesDoctorExist (String doctor_username){

        return doctorManager.doesUserExist(doctor_username);
    }
    public boolean doesSecretaryExist (String secretary_username){
        return secretaryManager.doesUserExist(secretary_username);
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
     * @param userDataBundle - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public <T extends User>ArrayList<LogData> getLogs(UserData<T> userDataBundle) {
        return logManager.getUserLogs(userDataBundle);
    }

    private <T extends User> void changePassUsingUsername(DataMapperGateway<T> database, String username, String newPassword){
        User user = database.getByCondition(x -> x.getUsername().equals(username));
        if (user != null){ user.setPassword(newPassword);}
    }
    public AdminData createAdmin(String userName, String password){
        return adminManager.createAdmin(userName, password);
    }
    public boolean changeAdminsPassword(AdminData adminData, String newPassword){
        return adminManager.changeUserPassword(adminData, newPassword);
    }


}
