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
    public void addPatientReport(Integer patientId, Integer reportId){
        patientDatabase.get(patientId).addReportId(reportId);
    }
    public void removePatientReport(Integer patientId, Integer reportId){
        patientDatabase.get(patientId).removeReportId(reportId);
    }
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patient.getId());
    }
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(String patientUsername){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        if (patient == null){return null;}
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patient.getId());
    }
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, String doctorUsername,
                                                     ZonedDateTime expiryDate){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);
        Doctor doctor = databaseQueryUtility.getUserByUsername(doctorDatabase, doctorUsername);

        if (patient == null){return null;}
        if (doctor == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patient.getId(), doctor.getId(), expiryDate);
    }
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, String patientUsername, Integer doctorId,
                                                     ZonedDateTime expiryDate){
        Patient patient = databaseQueryUtility.getUserByUsername(patientDatabase, patientUsername);

        if (patient == null){return null;}
        if (doctorDatabase.get(doctorId) == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patient.getId(), doctorId, expiryDate);
    }
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
