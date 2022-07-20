package entities;

/**
 * Represents a patient.
 */
public class Patient extends User {

    private String healthNumber;

    /**
     * Creates an instance of Patient with contact info.
     * @param username The patient's username.
     * @param password The patient's password.
     * @param contactInfoId The id of the patient's Contact object.
     * @param healthNumber The patient's health number.
     */
    public Patient(String username, String password, Integer contactInfoId, String healthNumber) {
        super(username, password, contactInfoId);
        this.healthNumber = healthNumber;
    }

    /**
     * Creates an instance of Patient without contact info.
     * @param username The patient's username.
     * @param password The patient's password.
     */
    public Patient(String username, String password){
        super(username, password, new Contact().getId());
    }

    /**
     * @return Returns the patient's health number.
     */
    public String getHealthNumber() {
        return healthNumber;
    }

    /**
     * Sets the patient's health number.
     * @param healthNumber The patient's new health number.
     */
    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

}
