package useCases.managers;

import dataBundles.LogDataBundle;
import dataBundles.PatientData;
import dataBundles.ReportData;
import dataBundles.UserDataBundle;
import database.DataMapperGateway;
import entities.Log;
import entities.Report;
import entities.User;
import utilities.DatabaseQueryUtility;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ReportManager {

    DataMapperGateway<Report> reportDatabase;

    DatabaseQueryUtility databaseUtils = new DatabaseQueryUtility();

    public ReportManager(DataMapperGateway<Report> reportDatabase){
        this.reportDatabase = reportDatabase;
    }

    public boolean deleteReport(Integer reportId){
        return reportDatabase.remove(reportId);
    }

    public ReportData addReport(ZonedDateTime dateNoted, String header, String body, Integer patientId, Integer doctorId){
        Report report = new Report(dateNoted, header, body, patientId, doctorId);
        reportDatabase.add(report);
        return new ReportData(report);
    }

    public ArrayList<ReportData> getReportDataBundlesFromPatientDataBundle(PatientData patientData){
        return databaseUtils.toArrayList(reportDatabase.stream().
                filter(log -> log.getId().equals(patientData.getId())).map(ReportData::new));

    }
}
