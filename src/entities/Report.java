package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(int id, ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID) {
        super(id, dateNoted, header, body, patientID, doctorID);
    }
}
