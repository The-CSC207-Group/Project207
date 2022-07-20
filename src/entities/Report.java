package entities;

import java.time.ZonedDateTime;

/**
 * Represents a report.
 */
public class Report extends Note {

    /**
     * Creates an instance of Report.
     * @param header The header of the report.
     * @param body The body of the report.
     * @param patientId The id of the patient who the report was created for.
     * @param doctorId The id of the doctor who created the report.
     */
    public Report(String header, String body, Integer patientId, Integer doctorId) {
        super(header, body, patientId, doctorId);
    }
}
