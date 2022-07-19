package entities;

import java.time.ZonedDateTime;

public class Prescription extends Note {

    private ZonedDateTime expiryDate;

    public Prescription(String header, String body, Integer patientId, Integer doctorId,
                        ZonedDateTime expiryDate) {
        super(header, body, patientId, doctorId);
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
