package entities;

import utilities.JsonSerializable;

import java.time.ZonedDateTime;

public abstract class Note extends JsonSerializable {

    private ZonedDateTime dateNoted;
    private String header;
    private String body;
    private Integer patientId;
    private Integer doctorId;

    public Note(ZonedDateTime dateNoted, String header, String body, Integer patientId, Integer doctorId) {
        this.dateNoted = dateNoted;
        this.header = header;
        this.body = body;
        this.patientId = patientId;
        this.doctorId = doctorId;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
