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
import java.util.stream.Collectors;

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

    public DoctorAccess(Database database){
        this.doctorDatabase = database.getDoctorDatabase();
        this.patientDatabase = database.getPatientDatabase();
        this.prescriptionDatabase = database.getPrescriptionDatabase();
        this.appointmentManager = new AppointmentManager(database);
        this.prescriptionManager = prescriptionManager;
        this.doctorManager = doctorManager;
        this.logManager = new LogManager(database.getLogDatabase());
        this.patientManager = new PatientManager(database);
    }
    public DoctorAccess(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Patient> patientDatabase,
                        DataMapperGateway<Prescription> prescriptionDatabase, AppointmentManager appointmentManager,
                        PrescriptionManager prescriptionManager, DoctorManager doctorManager, DataMapperGateway<Log>
                                logDatabase){
    }
//    boolean CreateAppointment(PatientDataBundle patientData, AppointmentData doctorData, ){
//        return appointmentManager.bookAppointment(patientData.getId(), doctorData.getId(),
//                new TimeBlock(
//            ZonedDateTime.
//        ) );
//    }


    public boolean doesPatientExist(String patient_name){
        return patientManager.doesPatientExist(patient_name);
    }
    public ArrayList<ReportData> getPatientReports(Integer patientId){
        return patientDatabase.get(patientId).getReportIds().stream()
                .map(x -> reportDatabase.get(x))
                .map(ReportData::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public void addPatientReport(Report report){
        reportDatabase.add(report);
    }
    public void removePatientReport(Integer patientId, Integer reportId){
        patientDatabase.get(patientId).removeReportId(reportId);
    }
    /**
     * Get an array list of PrescriptionDataBundles containing each prescription in the database belonging to the patient
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient that is active or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getActivePrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patient.getId());
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
     * @param patientUsername the username associated with the patient in the database. Should not be null. An empty arraylist is
     * returned if the patient does not exist or does not have any prescriptions.
     * @return An array list of PrescriptionDataBundles containing each prescription in the database belonging to the
     * patient or null if the patient does not exist in the patient database.
     */
    public ArrayList<PrescriptionData> getAllPrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patient.getId());
    }

    /**
     *
     * @param dateNoted date the prescription was created.
     * @param header Prescription name.
     * @param body Additional info about the prescription.
     * @param patientUsername Username of the patient to whom the prescription is assigned.
     * @param doctorUsername Username of the doctor who created the prescription.
     * @param expiryDate Expiry data of the prescription.
     * @return The prescriptionDataBundle representing the prescription if both the doctor and patient exist in their
     * respective databases, otherwise return null.
     */
    public PrescriptionData createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, String doctorUsername,
                                               ZonedDateTime expiryDate){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        Doctor doctor = databaseQueryUtility.getUserByUsername(doctorDatabase, doctorUsername);

        if (patient == null){return null;}
        if (doctor == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patient.getId(), doctor.getId(), expiryDate);
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
    public PrescriptionData createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, Integer doctorId,
                                               ZonedDateTime expiryDate){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);

        if (patient == null){return null;}
        if (doctorDatabase.get(doctorId) == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patient.getId(), doctorId, expiryDate);
    }
    public PrescriptionData createPrescription(String header, String body, PatientData patientData, DoctorData doctorData, Integer monthsTillExpiry){
        return createPrescription(ZonedDateTime.now(), header, body, patientData.getUsername(), doctorData.getId(), ZonedDateTime.now().plusMonths(monthsTillExpiry));
    }

    /**
     * Remove a prescription from the prescription database if it exists, otherwise do nothing.
     * @param prescriptionId id of the prescription to be removed.
     */
    public void deletePrescription(Integer prescriptionId){
        prescriptionManager.removePrescription(prescriptionId);
    }
    public ArrayList<AppointmentData> getAllAppointments(){
        return appointmentManager.getAllAppointments();
    }
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
     * @param username - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public ArrayList<LogData> getLogs(String username){
        ArrayList<LogData> dataBundlesDoctor = logManager.getLogDataBundlesFromUsername(username, doctorDatabase);
        if (dataBundlesDoctor != null){return dataBundlesDoctor;}

        return null;
    }
    public Optional<Integer> getPatientId(String name){
        return patientManager.getPatientId(name);
    }
    public Optional<PatientData> getPatient(String username){
        return Optional.ofNullable(patientManager.getUser(username))
                .map(PatientData::new);
    }
    public Optional<DoctorData> getDoctorData(Integer doctorId){
        return Optional.ofNullable(doctorDatabase.get(doctorId)).map(x -> new DoctorData(x));
    }



}
