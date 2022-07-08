package entities;

public class Appointment {

    private final int id;
    private TimeBlock timeBlock;
    private int doctorID;
    private int patientID;

    public Appointment(int id, TimeBlock timeBlock, int doctorID, int patientID) {
        this.id = id;
        this.timeBlock = timeBlock;
        this.doctorID = doctorID;
        this.patientID = patientID;
    }

    public int getId() {
        return id;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
