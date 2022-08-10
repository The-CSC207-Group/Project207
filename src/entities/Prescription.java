package entities;

import java.time.LocalDate;

/**
 * Represents a prescription.
 */
public class Prescription extends Note {

    private LocalDate expiryDate;

    /**
     * Creates an instance of Prescription.
     *
     * @param header     String representing the header of the prescription.
     * @param body       String representing the body of the prescription.
     * @param patientId  Integer representing the id of the patient who the prescription was created for.
     * @param doctorId   Integer representing the id of the doctor who created the prescription.
     * @param expiryDate LocalDate representing the prescription's expiry date.
     */
    public Prescription(String header, String body, Integer patientId, Integer doctorId,
                        LocalDate expiryDate) {
        super(header, body, patientId, doctorId);
        this.expiryDate = expiryDate;
    }

    /**
     * @return LocalDate representing the prescription's expiry date.
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the prescription.
     *
     * @param expiryDate LocalDate representing the new expiry date of the prescription.
     */
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
