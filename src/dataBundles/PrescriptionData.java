package dataBundles;

import entities.Prescription;

import java.time.ZonedDateTime;

/**
 * Class solely to bundle patient data and send it around to different controllers
 */
public class PrescriptionData {
    private Prescription prescription;

    public PrescriptionData(Prescription prescription) {
        this.prescription = prescription;
    }

    public ZonedDateTime getDateNoted() {
        return prescription.getDateNoted();
    }

    public ZonedDateTime getExpiryDate() {
        return prescription.getExpiryDate();
    }

    public Integer getPatientId() {
        return prescription.getPatientId();
    }

    public Integer getDoctorId() {
        return prescription.getDoctorId();
    }

    public String getBody() {
        return prescription.getBody();
    }

    public String getHeader() {
        return prescription.getHeader();
    }

    public Integer getPrescriptionId() {
        return prescription.getId();
    }
}
