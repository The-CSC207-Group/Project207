package entities;

/**
 * Represents a secretary.
 */
public class Secretary extends User {

    /**
     * Creates an instance of Secretary with contact info.
     * @param username The secretary's username.
     * @param password The secretary's password.
     * @param contactInfoId The id of the secretary's Contact object.
     */
    public Secretary(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Secretary without contact info.
     * @param username The secretary's username.
     * @param password The secretary's password.
     */
    public Secretary(String username, String password){
        super(username, password, new Contact().getId());
    }

}
