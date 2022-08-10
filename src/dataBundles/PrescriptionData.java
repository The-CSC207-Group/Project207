package dataBundles;

import entities.Prescription;

import java.time.LocalDate;

/**
 * Wrapper class for Prescription entity.
 */
public class PrescriptionData {

    private final Prescription prescription;

    /**
     * Constructor.
     *
     * @param prescription Prescription - prescription to be stored.
     */
    public PrescriptionData(Prescription prescription) {
        this.prescription = prescription;
    }

    /**
     * @return LocalDate - date the prescription stored was created.
     */
    public LocalDate getDateNoted() {
        return prescription.getDateNoted();
    }

    /**
     * @return LocalDate - expiry date of the prescription stored.
     */
    public LocalDate getExpiryDate() {
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
