package useCases.managers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.ReportData;
import database.DataMapperGateway;
import database.Database;
import entities.Log;
import entities.Report;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ReportManager {

    DataMapperGateway<Report> reportDatabase;

    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    public ReportManager(Database database){
        this.reportDatabase = database.getReportDatabase();
    }

    public boolean deleteReport(ReportData reportData){
        return reportDatabase.remove(reportData.getReportId());
    }

    public ReportData addReport(PatientData patientData, DoctorData doctorData, String header, String body){
        Report report = new Report(header, body, patientData.getId(), doctorData.getId());
        reportDatabase.add(report);
        return new ReportData(report);
    }

    public ArrayList<ReportData> getReportDataBundlesFromPatientDataBundle(PatientData patientData){
        return databaseUtils.toArrayList(reportDatabase.stream().
                filter(log -> log.getId().equals(patientData.getId())).map(ReportData::new));

    }
}
