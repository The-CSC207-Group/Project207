package entities;

import java.time.ZonedDateTime;

public class Note {

    private ZonedDateTime dateNoted;
    private String header;
    private String body;
    private Patient patient;
    private Doctor doctor;

    public Note(ZonedDateTime dateNoted, String header, String body, Patient patient, Doctor doctor) {
        this.dateNoted = dateNoted;
        this.header = header;
        this.body = body;
        this. patient = patient;
        this.doctor = doctor;
    }

    public ZonedDateTime getDateNoted() {
        return dateNoted;
    }

    public void setDateNoted(ZonedDateTime dateNoted) {
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
