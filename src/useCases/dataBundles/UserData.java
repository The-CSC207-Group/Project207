package useCases.dataBundles;

import entities.User;

/**
 * Abstract user data class that provides general getters to different user data bundles.
 *
 * @param <T> extends User - to be specified within each end user data class.
 */
public abstract class UserData<T extends User> extends DataBundle {

    /**
     * User being stored
     */
    private final T user;

    /**
     * Constructor. Calls the DataBundle constructor which stores the user in the parent class and
     * allows for methods from DataBundle to be used.
     *
     * @param user User - user to be stored.
     */
    public UserData(T user) {
        super(user);
        this.user = user;
    }

    /**
     * @return String - username of a user.
     */
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * @return Integer - id of the contact info object associated with the user.
     */
    public Integer getContactInfoId() {
        return user.getContactInfoId();
    }

}
