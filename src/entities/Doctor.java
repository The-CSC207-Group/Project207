package entities;

import java.util.ArrayList;

/**
 * Represents a doctor.
 */
public class Doctor extends User {

    //availability should represent an arrayList of 7 Availability objects, 1 for each day of the week with the
    //doctor's personalized hours
    private final ArrayList<Availability> availability = new ArrayList<>();
    //absence represents time off booked by the doctor
    private final ArrayList<TimeBlock> absence = new ArrayList<>();

    /**
     * Creates an instance of Doctor with contact info.
     * @param username String representing the doctor's username.
     * @param password String representing the doctor's password.
     * @param contactInfoId Integer representing the id of the doctor's Contact object.
     */
    public Doctor(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

//    /**
//     * Creates an instance of Doctor without contact info.
//     * @param username String representing the doctor's username.
//     * @param password String representing the doctor's password.
//     */
//    public Doctor(String username, String password){
//        super(username, password, new Contact().getId());
//    }

    public ArrayList<Availability> getAvailability() {
        return availability;
    }

    /**
     * @return ArrayList containing the TimeBlocks that the doctor is absent.
     */
    public ArrayList<TimeBlock> getAbsence() {
        return absence;
    }

    /**
     * Adds a TimeBlock that the doctor is absent in.
     * @param timeBlock TimeBlock the doctor is absent in.
     */
    public void addAbsence(TimeBlock timeBlock) {
        this.absence.add(timeBlock);
    }

    /**
     * Removes a TimeBlock that the doctor was absent in.
     * @param timeBlock TimeBlock the doctor was absent in.
     */
    public void removeAbsence(TimeBlock timeBlock) {
        this.absence.remove(timeBlock);
    }

    /**
     * Adds a time the doctor is available in.
     * @param Availability Availability time the doctor is available in.
     */
    public void addAvailability(Availability Availability) {
        availability.add(Availability);
    }

    /**
     * Removes a time the doctor was available in.
     * @param Availability Availability time the doctor was available in.
     */
    public void removeAvailability(Availability Availability) {
        availability.remove(Availability);
    }

}
