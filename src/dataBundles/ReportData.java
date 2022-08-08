package dataBundles;

import entities.Report;

import java.time.LocalDate;

/**
 * Wrapper class for Report entity.
 */
public class ReportData {

    private final Report report;

    /**
     * Constructor.
     *
     * @param report Report - report to be stored.
     */
    public ReportData(Report report) {
        this.report = report;
    }

    /**
     * @return LocalDate - date that the stored report was created.
     */
    public LocalDate getDateNoted() {
        return report.getDateNoted();
    }

    /**
     * @return String - header of the stored report.
     */
    public String getHeader() {
        return report.getHeader();
    }

    /**
     * @return String - body of the stored report.
     */
    public String getBody() {
        return report.getBody();
    }

    /**
     * @return Integer - patient id attribute associated with the report.
     */
    public Integer getPatientId() {
        return report.getPatientId();
    }

    /**
     * @return Integer - doctor id attribute associated with the report.
     */
    public Integer getDoctorId() {
        return report.getDoctorId();
    }

    /**
     * @return Integer - id of the report stored.
     */
    public Integer getReportId() {
        return report.getId();
    }

}
