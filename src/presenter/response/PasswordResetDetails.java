package presenter.response;

import java.util.Objects;

public final class PasswordResetDetails {
    private final String password;
    private final String confirmedPassword;

    public PasswordResetDetails(String password, String confirmedPassword) {
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String password() {
        return password;
    }

    public String confirmedPassword() {
        return confirmedPassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PasswordResetDetails) obj;
        return Objects.equals(this.password, that.password) &&
                Objects.equals(this.confirmedPassword, that.confirmedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, confirmedPassword);
    }

    @Override
    public String toString() {
        return "PasswordResetDetails[" +
                "password=" + password + ", " +
                "confirmedPassword=" + confirmedPassword + ']';
    }

}
