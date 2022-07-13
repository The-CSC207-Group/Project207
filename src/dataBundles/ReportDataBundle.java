package dataBundles;

import entities.Report;

import java.time.ZonedDateTime;

public class ReportDataBundle {

    Report report;

    public ReportDataBundle(Report report){
        this.report = report;
    }

    public ZonedDateTime getDateNoted() {
        return report.getDateNoted();
    }
    public String getHeader() {
        return report.getHeader();
    }
    public String getBody() {
        return report.getBody();
    }
    public int getPatientID() {
        return report.getPatientID();
    }
    public int getDoctorID() {
        return report.getDoctorID();
    }

}
