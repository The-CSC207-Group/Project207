package entities;

import utilities.JsonSerializable;

public class Appointment extends JsonSerializable {

    private TimeBlock timeBlock;
    private Integer doctorId;
    private Integer patientId;

    public Appointment(TimeBlock timeBlock, Integer doctorId, Integer patientId) {
        this.timeBlock = timeBlock;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}
