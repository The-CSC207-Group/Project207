package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(ZonedDateTime dateNoted, String header, String body, Integer patientId, Integer doctorId) {
        super(dateNoted, header, body, patientId, doctorId);
    }
}
