package entities;

/**
 * Represents a secretary.
 */
public class Secretary extends User {

    /**
     * Creates an instance of Secretary with contact info.
     * @param username String representing the secretary's username.
     * @param password String representing the secretary's password.
     * @param contactInfoId Integer representing the id of the secretary's Contact object.
     */
    public Secretary(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    /**
     * Creates an instance of Secretary without contact info.
     * @param username String representing the secretary's username.
     * @param password String representing the secretary's password.
     */
    public Secretary(String username, String password){
        super(username, password);
    }

}
