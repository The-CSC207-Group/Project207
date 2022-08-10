package entities;

/**
 * Represents a patient.
 */
public class Patient extends User {

    private String healthNumber;

    /**
     * Creates an instance of Patient with contact info.
     *
     * @param username      String representing the patient's username.
     * @param password      String representing the patient's password.
     * @param contactInfoId Integer representing the id of the patient's Contact object.
     * @param healthNumber  String representing the patient's health number.
     */
    public Patient(String username, String password, Integer contactInfoId, String healthNumber) {
        super(username, password, contactInfoId);
        this.healthNumber = healthNumber;
    }

    /**
     * Creates an instance of Patient with contact info.
     *
     * @param username      String representing the patient's username.
     * @param password      String representing the patient's password.
     * @param contactInfoId Integer representing the id of the patient's Contact object.
     */
    public Patient(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Patient without contact info.
     *
     * @param username String representing the patient's username.
     * @param password String representing the patient's password.
     */
    public Patient(String username, String password) {
        super(username, password);
    }

    /**
     * @return String representing the patient's health number.
     */
    public String getHealthNumber() {
        return healthNumber;
    }

    /**
     * Sets the patient's health number.
     *
     * @param healthNumber String representing the patient's new health number.
     */
    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

}
