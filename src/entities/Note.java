package entities;

import java.time.ZonedDateTime;

public class Note {

    private ZonedDateTime dateNoted;
    private String header;
    private String body;
    private String patientUserID;
    private String doctorUserID;

    public Note(ZonedDateTime dateNoted, String header, String body, String patientUserID, String doctorUserID) {
        this.dateNoted = dateNoted;
        this.header = header;
        this.body = body;
        this.patientUserID = patientUserID;
        this.doctorUserID = doctorUserID;
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

    public String getPatientUserID() {
        return patientUserID;
    }

    public void setPatientUserID(String patientUserID) {
        this.patientUserID = patientUserID;
    }

    public String getDoctorUserID() {
        return doctorUserID;
    }

    public void setDoctorUserID(String doctorUserID) {
        this.doctorUserID = doctorUserID;
    }
}
