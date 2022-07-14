package useCases.accessClasses;

import dataBundles.LogDataBundle;
import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Log;
import entities.Patient;
import entities.Prescription;
import entities.Contact;
import useCases.managers.AppointmentManager;
import entities.Appointment;
import useCases.managers.LogManager;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;

public class PatientAccess {

    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    LogManager logManager;
    public PatientAccess(DataMapperGateway<Prescription> prescriptionDatabase,
                         DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Log> logDatabase, DataMapperGateway<Contact> contactDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    public void signOut(){

    }
    public void deleteCurrentUser(Integer userId) {
        patientManager.deletePatient(userId);
    }
    public void changeCurrentUserPassword(Integer userId, String newPassword){
        patientManager.changeUserPassword(userId, newPassword);
    }
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(Integer userId){
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(userId);
    }
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(Integer userId){

        return prescriptionManager.getPatientAllPrescriptionDataByUserId(userId);
    }
    public ArrayList<Appointment> getAppointments(Patient patient){
        return null;
    }
    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        return null;
    }
}
