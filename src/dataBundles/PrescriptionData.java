package dataBundles;

import entities.Prescription;

import java.time.ZonedDateTime;

/**
 * Wrapper class for Prescription entity.
 */
public class PrescriptionData {
    private Prescription prescription;

    /**
     * Constructor.
     * @param prescription Prescription - prescription to be stored.
     */
    public PrescriptionData(Prescription prescription){
        this.prescription = prescription;
    }

    /**
     * @return ZonedDateTime - date the prescription stored was created.
     */
    public ZonedDateTime getDateNoted() {
        return prescription.getDateNoted();
    }

    /**
     * @return ZonedDateTime - expiry date of the prescription stored.
     */
    public ZonedDateTime getExpiryDate() {
        return prescription.getExpiryDate();
    }

    /**
     * @return Integer - id of the patient who the prescription belongs to.
     */
    public Integer getPatientId() {
        return prescription.getPatientId();
    }

    /**
     * @return Integer - id of the doctor who wrote the prescription stored.
     */
    public Integer getDoctorId() {
        return prescription.getDoctorId();
    }

    /**
     * @return String - body of the stored prescription.
     */
    public String getBody() {
        return prescription.getBody();
    }

    /**
     * @return String - header of the stored prescription.
     */
    public String getHeader() {
        return prescription.getHeader();
    }

    /**
     * @return Integer - id of the prescription stored.
     */
    public Integer getPrescriptionId() {
        return prescription.getId();
    }
}
