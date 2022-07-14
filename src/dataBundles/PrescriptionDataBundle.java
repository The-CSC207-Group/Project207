package dataBundles;

import entities.Doctor;
import entities.Patient;
import entities.Prescription;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Class solely to bundle patient data and send it around to different controllers
 */
public class PrescriptionDataBundle extends DataBundle{
    private Prescription prescription;
    public PrescriptionDataBundle(Integer id, Prescription prescription){
        super(id);
        this.prescription = prescription;
    }

    public ZonedDateTime getDateNoted() {
        return prescription.getDateNoted();
    }

    public ZonedDateTime getExpiryDate() {
        return prescription.getExpiryDate();
    }

    public Integer getPatientId() {
        return prescription.getPatientID();
    }

    public Integer getDoctorId() {
        return prescription.getDoctorID();
    }

    public String getBody() {
        return prescription.getBody();
    }

    public String getHeader() {
        return prescription.getHeader();
    }
}
