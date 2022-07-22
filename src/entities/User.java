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
     * @param username String representing the user's username.
     * @param password String representing the user's password.
     * @param contactInfoId Integer representing the id of the user's Contact object.
     */
    public User(String username, String password, Integer contactInfoId) {
        this(username, password);
        this.contactInfoId = contactInfoId;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return String representing the user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Compares this user's password to comparedPassword.
     * @param comparedPassword String representing the password to be compared.
     * @return boolean true if this user's password is the same as comparedPassword. Returns false otherwise.
     */
    public boolean comparePassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    /**
     * Sets the user's password.
     * @param password String representing the user's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Integer representing the id of this user's Contact object.
     */
    public Integer getContactInfoId() {
        return contactInfoId;
    }

    /**
     * Updates this user's Contact object id to a new Contact object id.
     * @param contactInfoId Integer representing the id of this user's new Contact object.
     */
    public void setContactInfoId(Integer contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

}
