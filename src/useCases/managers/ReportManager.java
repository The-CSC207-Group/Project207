package useCases.managers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.ReportData;
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
     * Constructor for the reportManager.
     * @param database Collection of all databases.
     */

    public ReportManager(Database database){
        this.reportDatabase = database.getReportDatabase();
    }

    /**
     * Delete a report from the report database.
     * @param reportData ReportData - data associated with the report to be deleted.
     * @return boolean - true if the id associated with the reportData exists in the database. false otherwise.
     */
    public boolean deleteReport(ReportData reportData){
        return reportDatabase.remove(reportData.getReportId());
    }

    /**
     * Add a report to the report database. Assumes all patientData and doctorData represents a valid patient and
     * doctor within the database.
     * @param patientData PatientData - data associated with a patient.
     * @param doctorData DoctorData - data associated with a doctor.
     * @param header String - header of the report.
     * @param body String - body of the report.
     * @return ReportData - the data for the newly created report.
     */
    public ReportData addReport(PatientData patientData, DoctorData doctorData, String header, String body){
        Report report = new Report(header, body, patientData.getId(), doctorData.getId());
        reportDatabase.add(report);
        return new ReportData(report);
    }

    /**
     *
     * @param patientData PatientData - data associated with a patient.
     * @return ArrayList<ReportData> - list of all report data associated with a patient.
     */
    public ArrayList<ReportData> getReportData(PatientData patientData){
        return databaseUtils.toArrayList(reportDatabase.stream().
                filter(log -> log.getId().equals(patientData.getId())).map(ReportData::new));

    }
}
