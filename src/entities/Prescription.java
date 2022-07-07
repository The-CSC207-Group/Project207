package entities;

import java.util.Date;

public class Prescription extends Note {

    // variables

    private Date expiryDate;

    // constructor

    public Prescription(Date dateNoted, String header, String body, Patient patient, Doctor doctor, Date expiryDate) {
        super(dateNoted, header, body, patient, doctor);
        this.expiryDate = expiryDate;
    }

    // methods

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
