package entities;

import utilities.JsonSerializable;

/**
 * Represents a user.
 */
public abstract class User extends JsonSerializable {

    private final String username;
    private String password;
    private Integer contactInfoId;

    /**
     * Creates an instance of User.
     * @param username The user's username.
     * @param password The user's password.
     * @param contactInfoId The id of the user's Contact object.
     */
    public User(String username, String password, Integer contactInfoId) {
        this.username = username;
        this.password = password;
        this.contactInfoId = contactInfoId;
    }

    /**
     * @return Returns the user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Compares this user's password to comparedPassword.
     * @param comparedPassword The password to be compared.
     * @return Returns true if this user's password is the same as comparedPassword. Returns false otherwise.
     */
    public boolean comparePassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    /**
     * Sets the user's password.
     * @param password The user's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the id of this user's Contact object.
     */
    public Integer getContactInfoId() {
        return contactInfoId;
    }

    /**
     * Updates this user's Contact object id to a new Contact object id.
     * @param contactInfoId The id of this user's new Contact object.
     */
    public void updateContactInfo(Integer contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

}
