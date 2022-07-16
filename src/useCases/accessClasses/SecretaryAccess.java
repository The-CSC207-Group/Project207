package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import useCases.managers.*;
import utilities.DatabaseQueryUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class SecretaryAccess {
    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    DoctorManager doctorManager;

    SecretaryManager secretaryManager;

    DataMapperGateway<Contact> contactDatabase;

    AppointmentManager appointmentManager;

    LogManager logManager;

    DatabaseQueryUtility databaseQueryUtility = new DatabaseQueryUtility();

    /**
     *
     * @param database database
     */

    public SecretaryAccess(Database database){
        this.patientDatabase = database.getPatientDatabase();
        this.secretaryDatabase = database.getSecretaryDatabase();
        this.prescriptionManager = new PrescriptionManager(database.getPrescriptionDatabase());
        this.patientManager = new PatientManager(database);
        this.doctorManager = new DoctorManager(database.getDoctorDatabase(), database.getContactDatabase());
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
        this.logManager = new LogManager(database.getLogDatabase());
        this.appointmentManager = new AppointmentManager(database);
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
    public PatientData createPatient(String username, String password, ContactDataBundle contactDataBundle,
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
    public DoctorData createDoctor (String username, String password, ContactDataBundle contactDataBundle){
        return doctorManager.createDoctor(username, password, contactDataBundle);
    }

    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient that is active or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patient.getId());
    }
    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patient.getId());
    }
    /**
     * Change the password of the signed in secretary or a patient. If the userId is not associated with a secretary/patient
     * in the database, nothing happens.
     * @param userId id of secretary/patient.
     * @param newPassword new password of the secretary/patient;
     */
    public void changePassword(Integer userId, String newPassword){
        secretaryManager.changeUserPassword(userId, newPassword);
        patientManager.changeUserPassword(userId, newPassword);
    }

    /**
     * Change the password of a patient by their username. If the userId is not associated with a patient
     * in the database, nothing happens.
     * @param patientUsername username of patient.
     * @param newPassword new password of the secretary/patient;
     */
    public void changePassword(String patientUsername, String newPassword){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient != null) {
            secretaryManager.changeUserPassword(patient.getId(), newPassword);
            patientManager.changeUserPassword(patient.getId(), newPassword);
        }
    }

    public ArrayList<AppointmentData> getScheduleData(Integer doctorId, LocalDate selectedDay){
        return appointmentManager.getScheduleData(doctorId, selectedDay);
    }
    public AppointmentData bookAppointment(Integer patientId, Integer doctorId, TimeBlock proposedTime){
        return appointmentManager.bookAppointment(patientId, doctorId, proposedTime);
    }
    public void removeAppointment(Integer appointmentId){
        appointmentManager.removeAppointment(appointmentId);
    }

    public ArrayList<AppointmentData> getPatientAppointmentDataBundles(Integer patientId){
        return  appointmentManager.getPatientAppointments(patientId);
    }

    public ArrayList<AppointmentData> getDoctorAppointmentDataBundles(Integer doctorId){
        return appointmentManager.getDoctorAppointments(doctorId);
    }


    /**
     * Gets an arraylist of log data bundles associated with a username. Can get logs from secretaries or patients.
     * @param username - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public ArrayList<LogDataBundle> getLogs(String username){
        ArrayList<LogDataBundle> dataBundlesPatient =  logManager.getLogDataBundlesFromUsername(username, patientDatabase);
        if (dataBundlesPatient != null){return dataBundlesPatient;}

        ArrayList<LogDataBundle> dataBundlesSecretary = logManager.getLogDataBundlesFromUsername(username, secretaryDatabase);
        if (dataBundlesSecretary != null){return dataBundlesSecretary;}
        return null;
    }
    public boolean doesPatientExist(String patient_username){
        return patientManager.doesPatientExist(patient_username);
    }
    public boolean doseDoctorExist (String doctor_username){
        return doctorManager.doesDoctorExist(doctor_username);
    }
    public Optional<PatientData> getPatient(String name){
        return patientDatabase.stream().filter(x -> x.getUsername() == name)
                .findFirst()
                .map(x -> new PatientData(x));
    }
}
