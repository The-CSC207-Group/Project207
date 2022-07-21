package entities;

import java.time.ZonedDateTime;

/**
 * Represents a prescription.
 */
public class Prescription extends Note {

    private ZonedDateTime expiryDate;

    /**
     * Creates an instance of Prescription.
     * @param header String representing the header of the prescription.
     * @param body String representing the body of the prescription.
     * @param patientId Integer representing the id of the patient who the prescription was created for.
     * @param doctorId Integer representing the id of the doctor who created the prescription.
     * @param expiryDate ZonedDateTime representing the prescription's expiry date.
     */
    public Prescription(String header, String body, Integer patientId, Integer doctorId,
                        ZonedDateTime expiryDate) {
        super(header, body, patientId, doctorId);
        this.expiryDate = expiryDate;
    }

    /**
     * @return ZonedDateTime representing the prescription's expiry date.
     */
    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the prescription.
     * @param expiryDate ZonedDateTime representing the new expiry date of the prescription.
     */
    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

}
