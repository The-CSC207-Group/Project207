package entities;

import java.util.ArrayList;

/**
 * Represents a doctor.
 */
public class Doctor extends User {

    //availability should represent an arrayList of 7 AvailabilityData objects, 1 for each day of the week with the
    //doctor's personalized hours
    private ArrayList<AvailabilityData> availability = new ArrayList<>();
    //absence represents time off booked by the doctor
    private ArrayList<TimeBlock> absence = new ArrayList<>();

    /**
     * Creates an instance of Doctor with contact info.
     * @param username The doctor's username.
     * @param password The doctor's password.
     * @param contactInfoId The id of the Contact object corresponding to this doctor.
     */
    public Doctor(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Doctor without contact info.
     * @param username The doctor's username.
     * @param password The doctor's password.
     */
    public Doctor(String username, String password){
        super(username, password, new Contact().getId());
    }

    /**
     * @return Returns a list of the doctor's availability.
     */
    public ArrayList<AvailabilityData> getAvailability() {
        return availability;
    }

    /**
     * @return Returns the TimeBlocks that the doctor is absent.
     */
    public ArrayList<TimeBlock> getAbsence() {
        return absence;
    }

    /**
     * Adds a TimeBlock that the doctor is absent in.
     * @param timeBlock The TimeBlock the doctor is absent in.
     */
    public void addAbsence(TimeBlock timeBlock) {
        this.absence.add(timeBlock);
    }

    /**
     * Removes a TimeBlock that the doctor was absent in.
     * @param timeBlock The TimeBlock the doctor was absent in.
     */
    public void removeAbsence(TimeBlock timeBlock) {
        this.absence.remove(timeBlock);
    }

    /**
     * Adds a time the doctor is available in.
     * @param availabilityData The time the doctor is available in.
     */
    public void addAvailability(AvailabilityData availabilityData) {
        availability.add(availabilityData);
    }

    /**
     * Removes a time the doctor was available in.
     * @param availabilityData The time the doctor was available in.
     */
    public void removeAvailability(AvailabilityData availabilityData) {
        availability.remove(availabilityData);
    }

}
