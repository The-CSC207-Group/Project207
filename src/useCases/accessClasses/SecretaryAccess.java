package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import useCases.managers.*;
import utilities.DatabaseQueryUtility;

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

    DataMapperGateway<Doctor> doctorDatabase;

    DatabaseQueryUtility databaseQueryUtility = new DatabaseQueryUtility();

    /**
     * @param database database
     */

    public SecretaryAccess(Database database) {
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
    public void signOut() {

    }



    /**
     * NOTE: Handling of creating a patient that already exists is not present yet.
     *
     * @param username     username of the patient
     * @param password     password of the patient
     * @param contactData  data bundle of patient contact info
     * @param healthNumber health number of patient
     * @return PatientDataBundle with the newly created patient's information
     */
    public PatientData createPatient(String username, String password) {
        return patientManager.createPatient(username, password);
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public DoctorData createDoctor(String username, String password) {
        return doctorManager.createDoctor(username, password);
    }

    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     *
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     *                        returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient that is active or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getActivePrescriptions(String patientUsername) {
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null) {
            return null;
        }
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patient.getId());
    }

    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     *
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     *                        returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getAllPrescriptions(String patientUsername) {
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null) {
            return null;
        }
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patient.getId());
    }

    /**
     * Change the password of the signed in secretary or a patient. If the userId is not associated with a secretary/patient
     * in the database, nothing happens.
     *
     * @param newPassword new password of the secretary/patient;
     */
    public void changeSecretaryPassword(SecretaryData secretaryData, String newPassword) {
        secretaryManager.changeUserPassword(secretaryData, newPassword);
    }

    /**
     * Change the password of a patient by their username. If the userId is not associated with a patient
     * in the database, nothing happens.
     *
     * @param patientData data of patient.
     * @param newPassword new password of the secretary/patient;
     */
    public void changePatientPassword(PatientData patientData, String newPassword) {
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientData.getUsername());
        if (patient != null) {
            patientManager.changeUserPassword(patientData, newPassword);
        }
    }

    public ArrayList<AppointmentData> getScheduleData(DoctorData doctorData, Integer year, Integer month, Integer dayOfMonth) {
        return appointmentManager.getScheduleData(doctorData, new TimeManager().createLocalDate(year, month, dayOfMonth));
    }

    public AppointmentData bookAppointment(PatientData patientData, DoctorData doctorData,
                                           Integer year, Integer month, Integer day, Integer hour, Integer minute,
                                           Integer lenOfAppointment) {
        return appointmentManager.bookAppointment(patientData, doctorData, year, month, day, hour, minute, lenOfAppointment);
    }

    public void removeAppointment(AppointmentData appointmentData) {
        appointmentManager.removeAppointment(appointmentData);
    }

    public ArrayList<AppointmentData> getPatientAppointmentDataBundles(PatientData patientData) {
        return appointmentManager.getPatientAppointments(patientData);
    }

    public ArrayList<AppointmentData> getDoctorAppointmentDataBundles(DoctorData doctorData) {
        return appointmentManager.getDoctorAppointments(doctorData);
    }


    /**
     * Gets an arraylist of log data bundles associated with a username. Can get logs from secretaries or patients.
     *
     * @param userDataBundle - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public <T extends User> ArrayList<LogData> getLogs(UserData<T> userDataBundle) {
        return logManager.getLogDataBundlesFromUserDataBundle(userDataBundle);
    }

    public boolean doesPatientExist(String patient_username) {
        return patientManager.doesPatientExist(patient_username);
    }

    public boolean doesDoctorExist(String doctor_username) {
        return doctorManager.doesUserExist(doctor_username);
    }

    public Optional<PatientData> getPatient(String name) {
        return patientDatabase.stream().filter(x -> x.getUsername().equals(name))
                .findFirst()
                .map(PatientData::new);
    }

    public Optional<DoctorData> getDoctor(String name) {
        return doctorDatabase.stream().filter(x -> x.getUsername().equals(name))
                .findFirst()
                .map(DoctorData::new);
    }
}
