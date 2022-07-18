package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import useCases.managers.AppointmentManager;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;

public class PatientAccess {
    DataMapperGateway<Patient> patientDatabase;

    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    LogManager logManager;
    AppointmentManager appointmentManager;

    /**
     *
     */
    public PatientAccess(Database database) {
        this.prescriptionManager = new PrescriptionManager(database.getPrescriptionDatabase());
        this.patientManager = new PatientManager(database);
        this.logManager = new LogManager(database);
        this.appointmentManager = new AppointmentManager(database);
        this.patientDatabase = patientDatabase;

    }

    public void signOut(){

    }

    /**
     * Delete the current patient from the patient database. If the patient doesn't exist in the database, nothing happens.
     * @param userId id of the user.
     */
    public void deleteCurrentUser(String username) {
        patientManager.deleteUser(username);
    }

    /**
     * Change the password of the current patient. If the userId is not associated with a patient in the database,
     * nothing happens.
     * @param userId id of patient.
     * @param newPassword new password of the patient;
     */
    public void changeCurrentUserPassword(PatientData patientData, String newPassword){
        patientManager.changeUserPassword(patientData, newPassword);
    }

    /**
     * Get prescriptionDataBundles representing the active prescriptions associated with a patient.
     * @param userId id of the patient whose prescriptions we are accessing.
     * @return Arraylist of PrescriptionDataBundles representing the patient's prescriptions. If the patient
     * does not exist, empty arraylist is returned.
     */
    public ArrayList<PrescriptionData> getActivePrescriptions(PatientData patientData){
        return prescriptionManager.getAllActivePrescriptions(patientData);
    }
    /**
     * Get prescriptionDataBundles representing the prescriptions associated with a patient.
     * @param userId Id of the patient whose prescriptions we are accessing.
     * @return Arraylist of PrescriptionDataBundles representing the patient's prescriptions. If the patient
     * does not exist, empty arraylist is returned.
     */
    public ArrayList<PrescriptionData> getAllPrescriptions(PatientData patientData){
        return prescriptionManager.getAllPrescriptions(patientData);
    }

    /**
     * get all appointments relating to a specific patient.
     * @param patientData the username associated with the patient in the database. Should not be null.
     * @return an ArrayList of AppointmentData, representing all the appointments assigned to the patient.
     */
    public ArrayList<AppointmentData> getAppointments(PatientData patientData){
        return appointmentManager.getPatientAppointments(patientData);
    }

    /**
     * Gets an arraylist of log data bundles associated with a username. Should only get logs from the logged in patients.
     * @param userData - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public <T extends User> ArrayList<LogData> getLogs(UserData<T> userData){
        return logManager.getUserLogs(userData);
    }
}
