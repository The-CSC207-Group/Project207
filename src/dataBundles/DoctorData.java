package dataBundles;

import entities.AvailabilityData;
import entities.Doctor;
import entities.TimeBlock;

import java.util.ArrayList;

public class DoctorData extends UserData<Doctor> {
    private final Doctor doctor;

    public DoctorData(Doctor doctor) {
        super(doctor);
        this.doctor = doctor;
    }

    public ArrayList<AvailabilityData> getAvailability(){
        return doctor.getAvailability();
    }

    public ArrayList<TimeBlock> getAbsence(){
        return doctor.getAbsence();
    }

}
