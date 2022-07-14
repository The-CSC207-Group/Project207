package dataBundles;

import entities.Report;

import java.time.ZonedDateTime;

public class ReportDataBundle extends DataBundle{

    Report report;

    public ReportDataBundle(Integer id, Report report){
        super(id);
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
    public int getPatientId() {
        return report.getPatientId();
    }
    public int getDoctorId() {
        return report.getDoctorId();
    }

}
