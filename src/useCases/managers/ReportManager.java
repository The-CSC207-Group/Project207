package useCases.managers;

import useCases.dataBundles.DoctorData;
import useCases.dataBundles.PatientData;
import useCases.dataBundles.ReportData;
import database.DataMapperGateway;
import database.Database;
import entities.Report;
import utilities.DatabaseQueryUtility;

import java.util.ArrayList;

/**
 * Use case class meant for handling doctor reports.
 */
public class ReportManager {

    private final DataMapperGateway<Report> reportDatabase;
    private final DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    /**
     * Initializes the report manager.
     *
     * @param database Database - collection of all entity databases in the program.
     */
    public ReportManager(Database database) {
        this.reportDatabase = database.getReportDatabase();
    }

    /**
     * Delete a report from the report database.
     *
     * @param reportData ReportData - data associated with the report to be deleted.
     */
    public void deleteReport(ReportData reportData) {
        reportDatabase.remove(reportData.getId());
    }

    /**
     * Add a report to the report database. Assumes all patientData and doctorData represents a valid patient and
     * doctor within the database.
     *
     * @param patientData PatientData - data associated with a patient.
     * @param doctorData  DoctorData - data associated with a doctor.
     * @param header      String - header of the report.
     * @param body        String - body of the report.
     */
    public void addReport(PatientData patientData, DoctorData doctorData, String header, String body) {
        Report report = new Report(header, body, patientData.getId(), doctorData.getId());
        reportDatabase.add(report);
    }

    /**
     * Creates and returns a list of all report data associated with a patient.
     *
     * @param patientData PatientData - data associated with a patient.
     * @return ArrayList<ReportData> - list of all report data associated with a patient.
     */
    public ArrayList<ReportData> getReportData(PatientData patientData) {
        return databaseUtils.toArrayList(reportDatabase.stream().
                filter(p -> p.getPatientId().equals(patientData.getId())).map(ReportData::new));
    }

}
