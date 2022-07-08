package useCases;

import database.DataMapperGateway;
import entities.Patient;
import entities.Prescription;
import useCases.AppointmentManager;
import entities.Appointment;

import java.util.ArrayList;

public class PatientAccess {
    private DataMapperGateway<Patient> patientDatabase;
    AppointmentManager appointmentManager;

    //PrescriptionManager prescriptionManager;
    public PatientAccess(DataMapperGateway<Patient> database, AppointmentManager appointmentManager) {
        this.patientDatabase = database;
        this.appointmentManager = appointmentManager;
    }

    public void signOut(){

    }
    public void deleteCurrentUser() {

    }
    public void changeCurrentUserPassword(String newPassword){

    }
    public ArrayList<Prescription> getActivePrescriptions(Patient patient){
        return null;
    }
    public ArrayList<Prescription> getPrescriptionsHistory(Patient patient){
        return null;
    }
    public ArrayList<Appointment> getAppointments(Patient patient){
        return null;
    }
}
