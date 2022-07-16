package dataBundles;

import entities.Report;

import java.time.ZonedDateTime;

public class ReportData {

    Report report;

    public ReportData(Report report) {
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

    public Integer getPatientId() {
        return report.getPatientId();
    }

    public Integer getDoctorId() {
        return report.getDoctorId();
    }

    public Integer getReportId() {
        return report.getId();
    }

}
