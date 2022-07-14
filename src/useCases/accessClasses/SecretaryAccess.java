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

    /**
     *
     * @param prescriptionDatabase database for storing prescriptions.
     * @param patientDatabase database for storing patients.
     * @param doctorDatabase database for storing doctors.
     * @param secretaryDatabase database for storing secretaries.
     * @param logDatabase database for storing logs.
     * @param contactDatabase database for storing contacts.
     * @param appointmentDatabase database for storing appointments.
     */
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

    /**
     * For future use.
     */
    public void signOut(){

    }

    /**
     * Delete user with given id from their database. This user can delete secretaries and patients.
     * @param userId id associated with the user to be deleted.
     */
    public void deleteUser(Integer userId){
        secretaryManager.deleteSecretary(userId);
        patientManager.deletePatient(userId);
    }

    /**
     * NOTE: Handling of creating a patient that already exists is not present yet.
     * @param username username of the patient
     * @param password password of the patient
     * @param contactDataBundle data bundle of patient contact info
     * @param healthNumber health number of patient
     * @return PatientDataBundle with the newly created patient's information
     */
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber){
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);
    }

    /**
     *
     * @param username
     * @param password
     * @param contactDataBundle
     * @return
     */
    public DoctorDataBundle createDoctor (String username, String password, ContactDataBundle contactDataBundle){
        return doctorManager.createDoctor(username, password, contactDataBundle);
    }

    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(Integer patientId){
        if (patientManager.getPatient(patientId) == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patientId);
    }
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(Integer patientId){
        if (patientManager.getPatient(patientId) == null){return null;}
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patientId);
    }
    public void changePatientPassword(Integer userId, String newPassword){
        patientManager.changeUserPassword(userId, newPassword);
    }
    public ArrayList<AppointmentDataBundle> getScheduleData(Integer doctorId, LocalDate selectedDay){
        return appointmentManager.getScheduleData(doctorId, selectedDay);
    }
    public AppointmentDataBundle bookAppointment(Integer patientId, Integer doctorId, TimeBlock proposedTime){
        return appointmentManager.bookAppointment(patientId, doctorId, proposedTime);
    }
    public void removeAppointment(Integer appointmentId){
        appointmentManager.removeAppointment(appointmentId);
    }

    public ArrayList<AppointmentDataBundle> getPatientAppointmentDataBundles(Integer patientId){
        return  appointmentManager.getPatientAppointments(patientId);
    }

    public ArrayList<AppointmentDataBundle> getDoctorAppointmentDataBundles(Integer doctorId){
        return appointmentManager.getDoctorAppointments(doctorId);
    }


    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        if (secretaryManager.getSecretary(userId) != null){return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogs());}
        return null;
    }
}
