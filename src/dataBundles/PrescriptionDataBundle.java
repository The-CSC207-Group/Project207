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
    private ZonedDateTime expiryDate;
    private ZonedDateTime dateNoted;
    private String header;
    private String body;
    private Integer patient;
    private Integer doctor;
    public PrescriptionDataBundle(Prescription prescription){
        this.expiryDate = prescription.getExpiryDate();
        this.dateNoted = prescription.getDateNoted();
        this.header = prescription.getHeader();
        this.body = prescription.getBody();
        this.patient = prescription.getPatientID();
        this.doctor = prescription.getDoctorID();
    }

    public ZonedDateTime getDateNoted() {
        return dateNoted;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public Integer getPatient() {
        return patient;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public String getBody() {
        return body;
    }

    public String getHeader() {
        return header;
    }
}
