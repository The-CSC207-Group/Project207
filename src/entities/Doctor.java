package entities;

import java.util.ArrayList;

public class Doctor extends User {

    // variables

    private ArrayList<TimeBlock> availability = new ArrayList<>();

    // constructors

    public Doctor(String username, String password, Contact contactInfo) {
        super(username, password, "doctor", contactInfo);
    }

    // methods

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
