package entities;

import Utilities.JsonSerializable;

import java.time.ZonedDateTime;

public abstract class Note extends JsonSerializable {

    private ZonedDateTime dateNoted;
    private String header;
    private String body;
    private int patientID;
    private int doctorID;

    public Note(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID) {
        this.dateNoted = dateNoted;
        this.header = header;
        this.body = body;
        this.patientID = patientID;
        this.doctorID = doctorID;
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

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
