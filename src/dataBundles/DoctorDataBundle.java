package dataBundles;

import entities.AvailabilityData;
import entities.Doctor;
import entities.TimeBlock;

import java.util.ArrayList;

public class DoctorDataBundle {
    private final Doctor doctor;

    public DoctorDataBundle(Doctor doctor) {
        this.doctor = doctor;
    }
    public String getUsername(){
        return doctor.getUsername();
    }

    public int getContact(){
        return doctor.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return doctor.getLogs();
    }

    public ArrayList<AvailabilityData> getAvailability(){
        return doctor.getAvailability();
    }

    public ArrayList<TimeBlock> getAbsence(){
        return doctor.getAbsence();
    }


}
