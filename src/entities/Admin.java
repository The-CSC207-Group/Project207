package entities;

/**
 * Represents an admin.
 */
public class Admin extends User {

    /**
     * Creates an instance of Admin with contact info.
     * @param username The admin's username.
     * @param password The admin's password.
     * @param contactInfoId The id of the admin's Contact object.
     */
    public Admin(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Admin without contact info,
     * @param username The admin's username.
     * @param password The admin's password.
     */
    public Admin(String username, String password){
        super(username, password, new Contact().getId());
    }

}
