package presenters.screenViews;


import dataBundles.ClinicData;
import presenters.entityViews.ClinicView;
import presenters.response.AvailabilityDetails;
import utilities.DayOfWeekUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;


/**
 * The Clinic's presenter class.
 */
public class ClinicScreenView extends ScreenView {
    DayOfWeekUtils dayOfWeekUtils = new DayOfWeekUtils();
    /**
     * Shows the welcome message that is presented at the start of the screen.
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

    /**
     * Shows a success message when clinic hours are changed.
     */
    public void showSuccessfullyChangedAvailability(){
        successMessage("Successfully changed the clinic's availability.");
    }

    /**
     * Shows an error message when a user incorrectly enters a field when changing clinic hours.
     */
    public void showEnteredInvalidAvailabilityInfoError(){
        errorMessage("Invalid entry.");
    }

    /**
     * Shows an error when a user selects an invalid day of week when removing availability.
     */
    public void showInvalidSelectionError(){
        errorMessage("Invalid selection.");
    }

    /**
     * Shows a success message when availability is removed from clinic hours.
     */
    public void showSuccessfullyDeletedAvailability(){
        successMessage("Availability removed successfully.");
    }

    /**
     * Shows the clinic's hours throughout the week given the clinic data.
     * @param clinicData
     */

    public void showClinicHours(ClinicData clinicData){
        ClinicView clinicView = new ClinicView();
        clinicView.viewClinicHours(clinicData);
    }

    /**
     * Shows a prompt for changing the hours of a clinic on a certain day.
     * @return AvailabilityDetails - a response containing DayOfWeek, StartTime and EndTime or returns null
     * if the user enters any invalid info.
     */
    public AvailabilityDetails showChangeClinicHoursPrompt(){
        DayOfWeek dayOfWeek = showDayOfWeekPrompt();
        if (dayOfWeek == null){return null;}
        infoMessage("Start Time: ");
        LocalTime localStartTime = showLocalTimePrompt();
        if (localStartTime == null){return null;}
        infoMessage("End Time: ");
        LocalTime localEndTime = showLocalTimePrompt();
        if (localEndTime == null){return null;}
        return new AvailabilityDetails(dayOfWeek, localStartTime, localEndTime);
    }

    /**
     * Prompt for getting the day of week from a user. Numbers the days of the week and returns the DayOfWeek
     * object associated with the number.
     * @return DayOfWeek - the day of the week (Ex: MONDAY, TUESDAY, etc.) the user chose or null if the user enters a
     * number that is not associated with a DayOfWeek or doesn't enter a number.
     */
    public DayOfWeek showDayOfWeekPrompt(){
        infoMessage("Select day of week: ");
        ArrayList<DayOfWeek> dayOfWeeks = new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
        for (int i = 0; i < dayOfWeeks.size(); i++){
            infoMessage(dayOfWeekUtils.dayOfWeekToString(dayOfWeeks.get(i)) + "(" + (i + 1) + ")");
        }
        Integer response = inputInt("Response (1-7): ");

        if (response == null || response < 1 || response > dayOfWeeks.size()){return null;}
        return dayOfWeeks.get(response - 1);
    }

}
