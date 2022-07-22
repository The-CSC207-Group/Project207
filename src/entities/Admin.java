package entities;

/**
 * Represents an admin.
 */
public class Admin extends User {

    /**
     * Creates an instance of Admin with contact info.
     * @param username String representing the admin's username.
     * @param password String representing the admin's password.
     * @param contactInfoId Integer the id of the admin's Contact object.
     */
    public Admin(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Admin without contact info,
     * @param username String representing the admin's username.
     * @param password String representing the admin's password.
     */
    public Admin(String username, String password){
        super(username, password);
    }

}
