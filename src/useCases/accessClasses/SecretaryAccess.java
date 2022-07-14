package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecretaryAccess {
    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    DoctorManager doctorManager;

    SecretaryManager secretaryManager;

    DataMapperGateway<Contact> contactDatabase;

    AppointmentManager appointmentManager;

    LogManager logManager;

    public SecretaryAccess(DataMapperGateway<Prescription> prescriptionDatabase,
                           DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor>
                           doctorDatabase, DataMapperGateway<Secretary> secretaryDatabase,
                           DataMapperGateway<Log> logDatabase,
                           DataMapperGateway<Contact> contactDatabase,
                           DataMapperGateway<Appointment> appointmentDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
        this.logManager = new LogManager(logDatabase);
        this.appointmentManager = new AppointmentManager(appointmentDatabase, doctorDatabase);
    }
    public void signOut(){

    }
    public void deleteUser(Integer iDUser){
        secretaryManager.deleteSecretary(iDUser);
        patientManager.deletePatient(iDUser);
    }
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber){
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);
    }
    public DoctorDataBundle createDoctor (String username, String password, ContactDataBundle contactDataBundle){
        return doctorManager.createDoctor(username, password, contactDataBundle);
    }

    public ArrayList<PrescriptionDataBundle> getActivePatientPrescriptions(Integer iDUser){
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(iDUser);
    }
    public ArrayList<PrescriptionDataBundle> getAllPatientPrescriptions(Integer iDUser){
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(iDUser);
    }
    public void changePatientPassword(Integer iDUser, String newPassword){
        patientManager.changeUserPassword(iDUser, newPassword);
    }
    public ArrayList<AppointmentDataBundle> getScheduleData(Integer doctorId, LocalDate selectedDay){
        return appointmentManager.getScheduleData(doctorId, selectedDay);
    }
    public AppointmentDataBundle bookAppointment(Integer iDPatient, Integer iDDoctor, TimeBlock proposedTime){
        return appointmentManager.bookAppointment(iDPatient, iDDoctor, proposedTime);
    }
    public void removeAppointment(Integer iDAppointment){
        appointmentManager.removeAppointment(iDAppointment);
    }

    public ArrayList<AppointmentDataBundle> getPatientAppointmentDataBundles(Integer idPatient){
       return  appointmentManager.getPatientAppointments(idPatient);
    }

    public ArrayList<AppointmentDataBundle> getDoctorAppointmentDataBundles(Integer idDoctor){
        return appointmentManager.getDoctorAppointments(idDoctor);
    }


    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        if (secretaryManager.getSecretary(userId) != null){return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogs());}
        return null;
    }
}
