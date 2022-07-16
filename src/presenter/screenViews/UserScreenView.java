package presenter.screenViews;

import presenter.response.PasswordResetDetails;

public abstract class UserScreenView extends ScreenView{

    public PasswordResetDetails resetPasswordPrompt() {
        infoMessage("You are about to reset your password.");
        String username = input("Enter new password: ");
        String password = input("Confirm new password: ");
        return new PasswordResetDetails(username, password);
    }

    public void showResetPasswordMismatchError() {
        errorMessage("Cannot reset password: new password and confirmed new password do not match.");
    }
}
