package useCases.accessClasses;

import dataBundles.AppointmentDataBundle;
import dataBundles.LogDataBundle;
import dataBundles.PrescriptionDataBundle;
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
        this.logManager = new LogManager(database.getLogDatabase());
        this.appointmentManager = new AppointmentManager(database);
    }

    public void signOut(){

    }

    /**
     * Delete the current patient from the patient database. If the patient doesn't exist in the database, nothing happens.
     * @param userId id of the user.
     */
    public void deleteCurrentUser(Integer userId) {
        patientManager.deletePatient(userId);
    }

    /**
     * Change the password of the current patient. If the userId is not associated with a patient in the database,
     * nothing happens.
     * @param userId id of patient.
     * @param newPassword new password of the patient;
     */
    public void changeCurrentUserPassword(Integer userId, String newPassword){
        patientManager.changeUserPassword(userId, newPassword);
    }

    /**
     * Get prescriptionDataBundles representing the active prescriptions associated with a patient.
     * @param userId id of the patient whose prescriptions we are accessing.
     * @return Arraylist of PrescriptionDataBundles representing the patient's prescriptions. If the patient
     * does not exist, empty arraylist is returned.
     */
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(Integer userId){
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(userId);
    }
    /**
     * Get prescriptionDataBundles representing the prescriptions associated with a patient.
     * @param userId Id of the patient whose prescriptions we are accessing.
     * @return Arraylist of PrescriptionDataBundles representing the patient's prescriptions. If the patient
     * does not exist, empty arraylist is returned.
     */
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(Integer userId){
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(userId);
    }
    public ArrayList<AppointmentDataBundle> getAppointments(Integer patientId){
        return appointmentManager.getPatientAppointments(patientId);
    }

    /**
     * Gets an arraylist of log data bundles associated with a username. Should only get logs from the logged in patients.
     * @param username - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public ArrayList<LogDataBundle> getLogs(String username){
        return logManager.getLogDataBundlesFromUsername(username, patientDatabase);
    }
}
