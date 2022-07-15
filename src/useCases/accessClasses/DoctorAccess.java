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

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DoctorAccess {

    private DataMapperGateway<Doctor> doctorDatabase;
    private DataMapperGateway<Patient> patientDatabase;
    private DataMapperGateway<Report> reportDatabase;
    private DataMapperGateway<Prescription> prescriptionDatabase;

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
    public ArrayList<PrescriptionDataBundle> getActivePrescriptions(Integer patientId){
        if (patientDatabase.get(patientId) == null){return null;}
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(patientId);
    }
    public ArrayList<PrescriptionDataBundle> getAllPrescriptions(Integer patientId){
        if (patientDatabase.get(patientId) == null){return null;}
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(patientId);
    }
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID,
                                                     ZonedDateTime expiryDate){
        if (patientDatabase.get(patientID) == null){return null;}
        if (doctorDatabase.get(doctorID) == null){return null;}
        return prescriptionManager.createPrescription(dateNoted, header, body, patientID, doctorID, expiryDate);
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
    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (doctorManager.getDoctor(userId) != null){return logManager.getLogDataBundlesFromLogIDs(doctorManager.getDoctor(userId).getLogIds());}
        return null;
    }


}
