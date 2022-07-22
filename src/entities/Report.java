// PHASE 2 FILE

package entities;

/**
 * Represents a report.
 */
public class Report extends Note {

    /**
     * Creates an instance of Report.
     * @param header String representing the header of the report.
     * @param body String representing the body of the report.
     * @param patientId Integer representing the id of the patient who the report was created for.
     * @param doctorId Integer representing the id of the doctor who created the report.
     */
    public Report(String header, String body, Integer patientId, Integer doctorId) {
        super(header, body, patientId, doctorId);
    }

}
