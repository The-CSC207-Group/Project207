package useCases.accessClasses;

import dataBundles.AppointmentDataBundle;
import dataBundles.LogDataBundle;
import dataBundles.PrescriptionDataBundle;
import dataBundles.ReportDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.AppointmentManager;
import useCases.managers.DoctorManager;
import useCases.managers.LogManager;
import useCases.managers.PrescriptionManager;
import utilities.DatabaseQueryUtility;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DoctorAccess {


    private DataMapperGateway<Doctor> doctorDatabase;
    private DataMapperGateway<Patient> patientDatabase;
    private DataMapperGateway<Report> reportDatabase;
    private DataMapperGateway<Prescription> prescriptionDatabase;

    private DatabaseQueryUtility databaseQueryUtility = new DatabaseQueryUtility();

    private LogManager logManager;

    AppointmentManager appointmentManager;
    PrescriptionManager prescriptionManager;
    DoctorManager doctorManager;

    public DoctorAccess(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Patient> patientDatabase,
                        DataMapperGateway<Prescription> prescriptionDatabase, AppointmentManager appointmentManager,
                        PrescriptionManager prescriptionManager, DoctorManager doctorManager, DataMapperGateway<Log>
                                logDatabase){
        this.doctorDatabase = doctorDatabase;
        this.patientDatabase = patientDatabase;
        this.prescriptionDatabase = prescriptionDatabase;
        this.appointmentManager = appointmentManager;
        this.prescriptionManager = prescriptionManager;
        this.doctorManager = doctorManager;
        this.logManager = new LogManager(logDatabase);

    }
    public ArrayList<ReportDataBundle> getPatientReports(Integer patientId){
        return patientDatabase.get(patientId).getReportIds().stream()
                .map(x -> reportDatabase.get(x))
                .map(x -> new ReportDataBundle(x.getId(), x))
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
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patient.getId());
    }
    /**
     * Change the password of this doctor. If the userId is not associated with a doctor in the database,
     * nothing happens.
     * @param userId id of doctor.
     * @param newPassword new password of the doctor;
     */
    public void changePassword(Integer userId, String newPassword){
        doctorManager.changeUserPassword(userId, newPassword);
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
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, String doctorUsername,
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
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, Integer doctorId,
                                                     ZonedDateTime expiryDate){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);

        if (patient == null){return null;}
        if (doctorDatabase.get(doctorId) == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patient.getId(), doctorId, expiryDate);
    }

    /**
     * Remove a prescription from the prescription database if it exists, otherwise do nothing.
     * @param prescriptionId id of the prescription to be removed.
     */
    public void deletePrescription(Integer prescriptionId){
        prescriptionManager.removePrescription(prescriptionId);
    }
    public ArrayList<AppointmentDataBundle> getAllAppointments(){
        return appointmentManager.getAllAppointments();
    }
    public ArrayList<AppointmentDataBundle> getAllDoctorAppointments(Integer doctorId){
        return appointmentManager.getDoctorAppointments(doctorId);
    }
    public ArrayList<AppointmentDataBundle> getAllPatientAppointments(Integer patientId){
        return appointmentManager.getPatientAppointments(patientId);
    }

    public ArrayList<AppointmentDataBundle> getScheduleData(Integer doctorId, LocalDate selectedDay){
        return appointmentManager.getScheduleData(doctorId, selectedDay);
    }
    public void signOut(){

    }

    /**
     * Gets an arraylist of log data bundles associated with a username. Should only get logs from the logged in doctors.
     * @param username - username of the user whose logs we want to get.
     * @return null if the user does not exist in any databases or an arraylist of logs otherwise.
     */
    public ArrayList<LogDataBundle> getLogs(String username){
        ArrayList<LogDataBundle> dataBundlesDoctor = logManager.getLogDataBundlesFromUsername(username, doctorDatabase);
        if (dataBundlesDoctor != null){return dataBundlesDoctor;}

        return null;
    }


}
