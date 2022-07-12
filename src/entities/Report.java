package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID) {
        super(dateNoted, header, body, patientID, doctorID);
    }
}