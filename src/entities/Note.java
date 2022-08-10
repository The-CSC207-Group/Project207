package entities;

import utilities.JsonSerializable;

import java.time.LocalDate;

/**
 * Represents a note.
 */
public abstract class Note extends JsonSerializable {

    private final LocalDate dateNoted = LocalDate.now();
    private final String header;
    private final String body;
    private final Integer patientId;
    private final Integer doctorId;

    /**
     * Creates an instance of Note.
     *
     * @param header    String representing the header of the note.
     * @param body      String representing the body of the note.
     * @param patientId Integer representing the id of the patient who the note was created for.
     * @param doctorId  Integer representing the id of the doctor who created the note.
     */
    public Note(String header, String body, Integer patientId, Integer doctorId) {
        this.header = header;
        this.body = body;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    /**
     * @return LocalDate representing the date that the note was created.
     */
    public LocalDate getDateNoted() {
        return dateNoted;
    }

    /**
     * @return String representing the header of the note.
     */
    public String getHeader() {
        return header;
    }

    /**
     * @return String representing the body of the note.
     */
    public String getBody() {
        return body;
    }

    /**
     * @return Integer representing the id of the patient who the note was created for.
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * @return Integer representing the id of the doctor who created the note.
     */
    public Integer getDoctorId() {
        return doctorId;
    }

}
