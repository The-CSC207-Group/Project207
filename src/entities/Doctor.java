package entities;

import java.util.ArrayList;

public class Doctor extends User {

    private ArrayList<TimeBlock> availability = new ArrayList<>();

    public Doctor(String username, String password, int contactInfo) {
        super(username, password, contactInfo);
    }

    public ArrayList<TimeBlock> getAvailability() {
        return availability;
    }

    public void addAvailability(TimeBlock timeBlock) {
        this.availability.add(timeBlock);
    }

    public void removeAvailability(TimeBlock timeBlock) {
        this.availability.remove(timeBlock);
    }
}
