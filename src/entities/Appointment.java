package entities;

import java.sql.Time;

public class Appointment {

    private TimeBlock timeBlock;
    private Doctor doctor;
    private Patient patient;

    public Appointment(TimeBlock timeBlock, Doctor doctor, Patient patient) {
        this.timeBlock = timeBlock;
        this.doctor = doctor;
        this.patient = patient;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
