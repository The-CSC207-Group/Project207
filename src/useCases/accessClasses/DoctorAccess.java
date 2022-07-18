package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import database.Database;
import entities.*;
import useCases.managers.*;
import utilities.DatabaseQueryUtility;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class  DoctorAccess {


    private DataMapperGateway<Doctor> doctorDatabase;
    private DataMapperGateway<Patient> patientDatabase;
    private DataMapperGateway<Report> reportDatabase;
    private DataMapperGateway<Prescription> prescriptionDatabase;

    private DatabaseQueryUtility databaseQueryUtility = new DatabaseQueryUtility();

    private LogManager logManager;

    AppointmentManager appointmentManager;
    PrescriptionManager prescriptionManager;
    DoctorManager doctorManager;
    PatientManager patientManager;

    ReportManager reportManager;
    Database database;

    public DoctorAccess(Database database){
        this.doctorDatabase = database.getDoctorDatabase();
        this.patientDatabase = database.getPatientDatabase();
        this.prescriptionDatabase = database.getPrescriptionDatabase();
        this.appointmentManager = new AppointmentManager(database);
        this.reportDatabase = database.getReportDatabase();
        this.reportManager = new ReportManager(reportDatabase);
        this.logManager = new LogManager(database);
        this.patientManager = new PatientManager(database);
        this.database = database;
    }

    public boolean doesPatientExist(String patient_name){
        return patientManager.doesPatientExist(patient_name);
    }
    public ArrayList<ReportData> getPatientReports(PatientData patientData){
        return reportManager.getReportDataBundlesFromPatientDataBundle(patientData);
    }
    public void addPatientReport(PatientData patientData, DoctorData doctorData, ZonedDateTime dateNoted, String header, String body){
        reportManager.addReport(patientData, doctorData, dateNoted, header, body);
    }
    public void removePatientReport(ReportData reportData){
        reportManager.deleteReport(reportData.getReportId());
    }
    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     * @param patientData Data relating to a patient stored in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient that is active or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getActivePrescriptions(PatientData patientData){
        return prescriptionManager.getAllActivePrescriptions(patientData);
    }
    /**
     * Change the password of this doctor. If the userId is not associated with a doctor in the database,
     * nothing happens.
     * @param newPassword new password of the doctor;
     */
    public void changePassword(DoctorData doctorData, String newPassword){
        doctorManager.changeUserPassword(doctorData, newPassword);
    }

    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     * @param patientData the username associated with the patient in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getAllPrescriptions(PatientData patientData){
        return prescriptionManager.getAllPrescriptions(patientData);
    }

    /**
     * a method that creates a prescription for a specific patient assigned by a doctor, stored in the prescription
     * database
     * @param dateNoted date the prescription was created.
     * @param header Prescription name.
     * @param body Additional info about the prescription.
     * @param patientData the username associated with the patient in the database. Should not be null.
     * @param doctorData the username associated with the doctor in the database. Should not be null.
     * @param expiryDate Expiry data of the prescription.
     * @return The prescriptionDataBundle representing the prescription if both the doctor and patient exist in their
     * respective databases, otherwise return null.
     */
    public PrescriptionData createPrescription(ZonedDateTime dateNoted, String header, String body, PatientData patientData, DoctorData doctorData,
                                               ZonedDateTime expiryDate){
        return prescriptionManager.createPrescription(dateNoted, header, body, patientData.getId(), doctorData.getId(), expiryDate);
    }

    /**
     *
     * @param dateNoted date the prescription was created.
     * @param header Prescription name.
     * @param body Additional info about the prescription.
     * @param patientUsername Username of the patient to whom the prescription is assigned.
     * @param doctorId Id of the doctor who created the prescription.
     * @param expiryDate Expiry data of the prescription.
     * @return The prescriptionDataBundle representing the prescription if both the doctor and patient exist in their
     * respective databases, otherwise return null.
     */

    /**
     * Remove a prescription from the prescription database if it exists, otherwise do nothing.
     * @param prescriptionId id of the prescription to be removed.
     */
    public void deletePrescription(Integer prescriptionId){
        prescriptionManager.removePrescription(prescriptionId);
    }
    /**
     * a function that gets all appointments independent of a specific patient or doctor.
     * @return an arrayList of AppointmentData. if there is no appointments in the database, returns empty arrayList
     */
    public ArrayList<AppointmentData> getAllAppointments(){
        return appointmentManager.getAllAppointments();
    }

    /**
     *
     * @param doctorData the username associated with the doctor in the database. Should not be null.
     * @return
     */
    public ArrayList<AppointmentData> getAllDoctorAppointments(DoctorData doctorData){
        return appointmentManager.getDoctorAppointments(doctorData);
    }
    public ArrayList<AppointmentData> getAllPatientAppointments(PatientData patientData){
        return appointmentManager.getPatientAppointments(patientData);
    }
    public ArrayList<AppointmentData> getScheduleData(DoctorData doctorData, Integer year, Integer month, Integer dayOfMonth){
        return appointmentManager.getScheduleData(doctorData, new TimeManager().createLocalDate(year, month, dayOfMonth));
    }
    public void signOut(){

    }

    /**
     * Gets an arraylist of log data bundles associated with a username. Should only get logs from the logged in doctors.
     * @param userDataBundle - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public <T extends User> ArrayList<LogData> getLogs(UserData<T> userDataBundle){
        return logManager.getUserLogs(userDataBundle);
    }
    public Optional<Integer> getPatientId(String name){
        return patientManager.getPatientId(name);
    }
    public Optional<PatientData> getPatient(String username){
        return Optional.ofNullable(patientManager.getUser(username))
                .map(PatientData::new);
    }
    public Optional<DoctorData> getDoctorData(Integer doctorId){
        return Optional.ofNullable(doctorDatabase.get(doctorId)).map(DoctorData::new);
    }


}
