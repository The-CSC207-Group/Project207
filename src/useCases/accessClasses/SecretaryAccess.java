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
     * @param iDUser id associated with the user to be deleted.
     */
    public void deleteUser(Integer iDUser){
        secretaryManager.deleteSecretary(iDUser);
        patientManager.deletePatient(iDUser);
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
