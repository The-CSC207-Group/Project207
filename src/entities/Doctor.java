package entities;

import java.util.ArrayList;

/**
 * Represents a doctor.
 */
public class Doctor extends User {

    /**
     * Creates an instance of Doctor with contact info.
     *
     * @param username      String representing the doctor's username.
     * @param password      String representing the doctor's password.
     * @param contactInfoId Integer representing the id of the doctor's Contact object.
     */
    public Doctor(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Doctor without contact info.
     *
     * @param username String representing the doctor's username.
     * @param password String representing the doctor's password.
     */
    public Doctor(String username, String password) {
        super(username, password);
    }
}
