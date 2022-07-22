package presenter.screenViews;

import dataBundles.ClinicData;
import presenter.entityViews.ClinicView;

/**
 * The Clinic's presenter class.
 */
public class ClinicScreenView extends ScreenView {

    public void showWelcomeMessage() {
        infoMessage("Entered clinic details menu.");
    }

    public String showClinicNamePrompt() {return input("Enter the name of the clinic: ");}

    public void showClinicNameFormatError() {
        errorMessage("The clinic's name is not in valid format.");
    }

    public void showSuccessfullyChangedClinicName() {successMessage("Successfully changed the clinic's name.");}

    public String showClinicAddressPrompt() {
        return input("Enter the clinic's address: ");
    }

    public void showClinicAddressFormatError() {
        errorMessage("The clinic's address is not in valid format.");
    }

    public void showSuccessfullyChangedClinicAddress() {successMessage("Successfully changed the clinic's address.");}

    public String showClinicPhoneNumberPrompt() {return input("Enter the clinic's phone number: ");}

    public String showClinicEmailPrompt() {
        return input("Enter the clinic's email: ");
    }

    public void showClinicEmailFormatError() {
        errorMessage("The clinic's email is not in valid format.");
    }

    public void showSuccessfullyChangedClinicEmail() {successMessage("Successfully changed the clinic's email.");}

    public void showClinicPhoneNumberFormatError() {
        errorMessage("The clinic's phone number is not in valid format: ^([0-9])+$");
    }

    public void showSuccessfullyChangedClinicPhoneNumber() {
        successMessage("Successfully changed the clinic's phone number.");
    }

}
