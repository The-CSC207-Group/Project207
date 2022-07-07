package entities;

import java.util.Date;

public class Note {

    // variables

    private Date dateNoted;
    private String header;
    private String body;
    private Patient patient;
    private Doctor doctor;

    // constructor

    public Note(Date dateNoted, String header, String body, Patient patient, Doctor doctor) {
        this.dateNoted = dateNoted;
        this.header = header;
        this.body = body;
        this. patient = patient;
        this.doctor = doctor;
    }

    // methods

    public Date getDateNoted() {
        return dateNoted;
    }

    public void setDateNoted(Date dateNoted) {
        this.dateNoted = dateNoted;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
