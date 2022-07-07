package entities;

import java.util.Date;

public class Report extends Note {


    // constructor

    public Report(Date dateNoted, String header, String body, Patient patient, Doctor doctor) {
        super(dateNoted, header, body, patient, doctor);
    }
}
