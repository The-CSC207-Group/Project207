package entities;

import java.time.ZonedDateTime;

/**
 * Represents a prescription.
 */
public class Prescription extends Note {

    private ZonedDateTime expiryDate;

    /**
     * Creates an instance of Prescription.
     * @param header The header of the prescription.
     * @param body The body of the prescription.
     * @param patientId The id of the patient who this prescription belongs to.
     * @param doctorId The id of the doctor who created this prescription.
     * @param expiryDate The prescription's expiry date.
     */
    public Prescription(String header, String body, Integer patientId, Integer doctorId,
                        ZonedDateTime expiryDate) {
        super(header, body, patientId, doctorId);
        this.expiryDate = expiryDate;
    }

    /**
     * @return Returns the prescription's expiry date.
     */
    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the prescription.
     * @param expiryDate The new expiry date of the prescription.
     */
    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
