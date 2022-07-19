package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(String header, String body, Integer patientId, Integer doctorId) {
        super(header, body, patientId, doctorId);
    }
}
