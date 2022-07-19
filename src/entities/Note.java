package entities;

import utilities.JsonSerializable;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a note.
 */
public abstract class Note extends JsonSerializable {

    private final ZonedDateTime dateNoted = ZonedDateTime.now();
    private String header;
    private String body;
    private Integer patientId;
    private Integer doctorId;

    /**
     * Creates an instance of Note.
     * @param header The header of the note.
     * @param body The body of the note.
     * @param patientId The id of the patient who the note was created for.
     * @param doctorId The id of the doctor who created the note.
     */
    public Note(String header, String body, Integer patientId, Integer doctorId) {
        this.header = header;
        this.body = body;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    /**
     * @return Returns the date and time that the note was created.
     */
    public ZonedDateTime getDateNoted() {
        return dateNoted;
    }

    /**
     * @return Returns the header of the note.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the note.
     * @param header The new header of the note.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @return Returns the body of the note.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body of the note.
     * @param body The new body of the note.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return Returns the id of the patient who the note was created for.
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * Sets the id of the patient who the note was created for.
     * @param patientId The new id of the patient who the note was created for.
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * @return Returns the id of the doctor who created the note.
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the id of the doctor who created the note.
     * @param doctorId The new id of the doctor who created the note.
     */
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
