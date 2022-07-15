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
        this.adminManager = new AdminManager(adminDatabase, contactDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    /**
     * @param userId Integer userID of the user being deleted.
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

    public ArrayList<LogDataBundle> getLogs(Integer userId) {
        if (patientManager.getPatient(userId) != null) {
            return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogIds());
        }
        if (secretaryManager.getSecretary(userId) != null) {
            return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogIds());
        }
        if (doctorManager.getDoctor(userId) != null) {
            return logManager.getLogDataBundlesFromLogIDs(doctorManager.getDoctor(userId).getLogIds());
        }
        if (adminManager.getAdmin(userId) != null) {
            return logManager.getLogDataBundlesFromLogIDs(adminManager.getAdmin(userId).getLogIds());
        }
        return null;
    }


}
