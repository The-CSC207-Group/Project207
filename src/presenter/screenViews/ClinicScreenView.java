package presenter.screenViews;


/**
 * The Clinic's presenter class.
 */
public class ClinicScreenView extends ScreenView {
    /**
     * Shows the welcome message that is presented at the start of the program.
     */
    public void showWelcomeMessage() {
        infoMessage("Entered clinic details menu.");
    }

    /**
     * Shows the clinic name input, taking in text from the user.
     * @return String representing the text inputted.
     */
    public String showClinicNamePrompt() {return input("Enter the name of the clinic: ");}

    /**
     * Shows an error related to name formatting.
     */
    public void showClinicNameFormatError() {
        errorMessage("The clinic's name is not in valid format.");
    }

    /**
     * Shows a success message for changing the clinic name.
     */
    public void showSuccessfullyChangedClinicName() {successMessage("Successfully changed the clinic's name.");}

    /**
     * Shows the clinic address input, taking in text from the user.
     * @return a String representing the text inputted.
     */
    public String showClinicAddressPrompt() {
        return input("Enter the clinic's address: ");
    }

    /**
     * Shows an error related to address formatting.
     */
    public void showClinicAddressFormatError() {
        errorMessage("The clinic's address is not in valid format.");
    }

    /**
     * Shows a success message for changing a clinic address.
     */
    public void showSuccessfullyChangedClinicAddress() {successMessage("Successfully changed the clinic's address.");}

    /**
     * Shows the clinic phone number input, taking in text from the user.
     * @return a String representing the text inputted.
     */
    public String showClinicPhoneNumberPrompt() {return input("Enter the clinic's phone number: ");}

    /**
     * Shows the clinic email input, taking in text from the user.
     * @return a String representing the text inputted.
     */
    public String showClinicEmailPrompt() {
        return input("Enter the clinic's email: ");
    }

    /**
     * Shows an error related to email formatting.
     */
    public void showClinicEmailFormatError() {
        errorMessage("The clinic's email is not in valid format.");
    }

    /**
     * Shows a success message for changing a clinic email address.
     */
    public void showSuccessfullyChangedClinicEmail() {successMessage("Successfully changed the clinic's email.");}

    /**
     * Shows an error related to phone number formatting.
     */
    public void showClinicPhoneNumberFormatError() {
        errorMessage("The clinic's phone number is not in valid format: ^([0-9])+$");
    }

    /**
     * Shows a success message regarding changing a clinic phone number.
     */
    public void showSuccessfullyChangedClinicPhoneNumber() {
        successMessage("Successfully changed the clinic's phone number.");
    }

}
