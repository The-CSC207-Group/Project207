package useCases.accessClasses;

import dataBundles.LogDataBundle;
import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Log;
import entities.Patient;
import entities.Prescription;
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
                         DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Log> logDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase);
        this.logManager = new LogManager(logDatabase);
    }

    public void signOut(){

    }
    public void deleteCurrentUser(Integer iDUser) {
        patientManager.deletePatient(iDUser);
    }
    public void changeCurrentUserPassword(Integer iDUser, String newPassword){
        patientManager.changeUserPassword(iDUser, newPassword);
    }
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(Integer iDUser){
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(iDUser);
    }
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(Integer iDUser){

        return prescriptionManager.getPatientAllPrescriptionDataByUserId(iDUser);
    }
    public ArrayList<Appointment> getAppointments(Patient patient){
        return null;
    }
    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        return null;
    }
}
