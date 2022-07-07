package entities;

import java.time.ZonedDateTime;

public class Report extends Note {

    public Report(ZonedDateTime dateNoted, String header, String body, Patient patient, Doctor doctor) {
        super(dateNoted, header, body, patient, doctor);
    }
}
