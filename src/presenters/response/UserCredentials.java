package presenters.response;

import java.util.Objects;

/**
 * A user's credentials as a response.
 */
public final class UserCredentials {

    private final String username;
    private final String password;

    /**
     * Creates an instance of UserCredentials.
     *
     * @param username String representing this user's username.
     * @param password String representing this user's password.
     */
    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return String representing this user's username.
     */
    public String username() {
        return username;
    }

    /**
     * @return String representing this user's password.
     */
    public String password() {
        return password;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of UserCredentials.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserCredentials) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password);
    }

    /**
     * @return int representing the hash code value for this instance of UserCredentials.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    /**
     * @return String representation of this instance of UserCredentials.
     */
    @Override
    public String toString() {
        return "UserCredentials[" +
                "username=" + username + ", " +
                "password=" + password + ']';
    }

}
