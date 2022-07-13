package useCases.accessClasses;

import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Patient;
import entities.Prescription;
import useCases.managers.AppointmentManager;
import entities.Appointment;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;

import java.util.ArrayList;

public class PatientAccess {

    PrescriptionManager prescriptionManager;
    PatientManager patientManager;
    public PatientAccess(DataMapperGateway<Prescription> prescriptionDatabase,
                         DataMapperGateway<Patient> patientDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase);
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
}
