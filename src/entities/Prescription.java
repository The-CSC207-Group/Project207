package entities;

import java.time.ZonedDateTime;

public class Prescription extends Note {

    private ZonedDateTime expiryDate;

    public Prescription(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID,
                        ZonedDateTime expiryDate) {
        super(dateNoted, header, body, patientID, doctorID);
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
