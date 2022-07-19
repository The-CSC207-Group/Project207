package entities;

/**
 * Represents an admin.
 */
public class Admin extends User {

    /**
     * Creates an instance of Admin with contact info.
     * @param username The username of the admin being instantiated.
     * @param password The password of the admin being instantiated.
     * @param contactInfoId The id of the Contact object corresponding to the admin being instantiated.
     */
    public Admin(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Admin without contact info,
     * @param username The username of the admin being instantiated.
     * @param password The password of the admin being instantiated.
     */
    public Admin(String username, String password){
        super(username, password, new Contact().getId());
    }

}
