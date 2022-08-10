package presenters.response;

import java.util.Objects;

/**
 * A password's reset details as a response.
 */
public final class PasswordResetDetails {
    private final String password;
    private final String confirmedPassword;

    /**
     * Creates an instance of PasswordResetDetails.
     *
     * @param password          String representing the user's inputted password.
     * @param confirmedPassword String representing the user's inputted confirmed password.
     */
    public PasswordResetDetails(String password, String confirmedPassword) {
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    /**
     * @return String representing the user's inputted password.
     */
    public String password() {
        return password;
    }

    /**
     * @return String representing the user's inputted confirmed password.
     */
    public String confirmedPassword() {
        return confirmedPassword;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of PasswordResetDetails.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PasswordResetDetails) obj;
        return Objects.equals(this.password, that.password) &&
                Objects.equals(this.confirmedPassword, that.confirmedPassword);
    }

    /**
     * @return int representing the hash code value for this instance of PasswordResetDetails.
     */
    @Override
    public int hashCode() {
        return Objects.hash(password, confirmedPassword);
    }

    /**
     * @return String representation of this instance of PasswordResetDetails.
     */
    @Override
    public String toString() {
        return "PasswordResetDetails[" +
                "password=" + password + ", " +
                "confirmedPassword=" + confirmedPassword + ']';
    }

}
