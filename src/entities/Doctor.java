package entities;

import java.util.ArrayList;

public class Doctor extends User {

    //availability should represent an arrayList of 7 AvailabilityData objects, 1 for each day of the week with the
    //doctor's personalized hours
    private ArrayList<AvailabilityData> availability = new ArrayList<>();
    //absence represents time off booked by the doctor
    private ArrayList<TimeBlock> absence = new ArrayList<>();

    public Doctor(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    public Doctor(String username, String password){
        super(username, password, new Contact().getId());
    }

    public ArrayList<AvailabilityData> getAvailability() {
        return availability;
    }
    public ArrayList<TimeBlock> getAbsence() {
        return absence;
    }

    public void addAbsence(TimeBlock timeBlock) {
        this.absence.add(timeBlock);
    }

    public void removeAbsence(TimeBlock timeBlock) {
        this.absence.remove(timeBlock);
    }

    public void removeAvailability(AvailabilityData availabilityData) {
        availability.remove(availabilityData);
    }
    public void addAvailability(AvailabilityData availabilityData) {
        availability.add(availabilityData);
    }

}
