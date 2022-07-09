package dataBundles;

import entities.Doctor;
import entities.Patient;
import entities.Prescription;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Class solely to bundle patient data and send it around to different controllers
 */
public class PrescriptionDataBundle {
    private Prescription prescription;
    public PrescriptionDataBundle(Prescription prescription){
        this.prescription = prescription;
    }

    public ZonedDateTime getDateNoted() {
        return prescription.getDateNoted();
    }

    public ZonedDateTime getExpiryDate() {
        return prescription.getExpiryDate();
    }

    public Integer getPatient() {
        return prescription.getPatientID();
    }

    public Integer getDoctor() {
        return prescription.getDoctorID();
    }

    public String getBody() {
        return prescription.getBody();
    }

    public String getHeader() {
        return prescription.getHeader();
    }
}
