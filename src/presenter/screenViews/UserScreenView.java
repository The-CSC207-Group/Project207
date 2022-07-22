package presenter.screenViews;

import dataBundles.*;
import presenter.entityViews.*;
import presenter.response.PasswordResetDetails;

import java.util.List;

public abstract class UserScreenView extends ScreenView {

    /**
     * Show user a password reset prompt with a confirmation.
     * @return PasswordResetDetails containing new password and confirmed new password.
     */
    public PasswordResetDetails resetPasswordPrompt() {
        infoMessage("You are about to reset your password.");
        String username = input("Enter your new password: ");
        String password = input("Confirm your new password: ");
        return new PasswordResetDetails(username, password);
    }

    /**
     * Show error when password and confirmed password don't match.
     */
    public void showResetPasswordMismatchError() {
        errorMessage("Cannot reset password: new password and confirmed new password do not match.");
    }

    /**
     * Show success message when password is reset.
     */
    public void showResetPasswordSuccessMessage() {
        successMessage("Password reset successfully");
    }

    /**
     * View logs specific to the current user.
     */
    public void viewUserLogs(List<LogData> items) {
        LogView logView = new LogView();
        String output = logView.viewFullFromList(items);
        infoMessage("Logs:");
        infoMessage(output);
    }

    public void displayAdminInfo(AdminData adminData) {
        AdminView adminView = new AdminView();
        infoMessage(adminView.viewFull(adminData));
    }

    public void displayDoctorInfo(DoctorData doctorData) {
        DoctorView doctorView = new DoctorView();
        infoMessage(doctorView.viewFull(doctorData));
    }

    public void displayPatientInfo(PatientData patientData) {
        PatientView patientView = new PatientView();
        infoMessage(patientView.viewFull(patientData));
    }

    public void displaySecretaryInfo(SecretaryData secretaryData) {
        SecretaryView secretaryView = new SecretaryView();
        infoMessage(secretaryView.viewFull(secretaryData));
    }


    protected Integer deleteItemFromEnumerationPrompt(String itemType) {
        warningMessage("This action cannot be undone!");
        Integer index = inputInt("Input " + itemType + " number to delete: ");
        if (index != null) {
            return index - 1; // -1 to be the index of the list, not number inputted.
        } else {
            return null;
        }
    }

}
