package entities;

public class Appointment extends JsonSerializable {

    private TimeBlock timeBlock;
    private int doctorID;
    private int patientID;

    public Appointment(TimeBlock timeBlock, int doctorID, int patientID) {
        this.timeBlock = timeBlock;
        this.doctorID = doctorID;
        this.patientID = patientID;
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
