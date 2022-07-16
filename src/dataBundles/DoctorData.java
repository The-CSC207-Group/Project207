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
    public String getUsername(){
        return doctor.getUsername();
    }

    public Integer getContact(){
        return doctor.getContactInfoId();
    }

    public ArrayList<AvailabilityData> getAvailability(){
        return doctor.getAvailability();
    }

    public ArrayList<TimeBlock> getAbsence(){
        return doctor.getAbsence();
    }

    public Integer getId(){
        return doctor.getId();
    }
}
