package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(ZonedDateTime dateNoted, String header, String body, String patientUserID, String doctorUserID) {
        super(dateNoted, header, body, patientUserID, doctorUserID);
    }
}
