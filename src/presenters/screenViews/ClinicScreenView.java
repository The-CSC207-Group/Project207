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

    public void showSuccessfullyChangedAvailability(){
        successMessage("Successfully changed the clinic's availability.");
    }

    public void showEnteredInvalidAvailabilityInfoError(){
        errorMessage("New availability data is incorrect.");
    }

    public void showClinicHours(ClinicData clinicData){
        ClinicView clinicView = new ClinicView();
        clinicView.viewClinicHours(clinicData);
    }
    public AvailabilityDetails showChangeClinicHoursPrompt(){
        infoMessage("Select day of week:");
        DayOfWeek dayOfWeek = showDayOfWeekPrompt();
        if (dayOfWeek == null){return null;}
        infoMessage("Start Time:");
        LocalTime localStartTime = showLocalTimePrompt();
        if (localStartTime == null){return null;}
        infoMessage("End Time:");
        LocalTime localEndTime = showLocalTimePrompt();
        if (localEndTime == null){return null;}
        return new AvailabilityDetails(dayOfWeek, localStartTime, localEndTime);
    }
    private DayOfWeek showDayOfWeekPrompt(){
        ArrayList<DayOfWeek> dayOfWeeks = new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
        for (int i = 0; i < dayOfWeeks.size(); i++){
            infoMessage(dayOfWeekUtils.dayOfWeekToString(dayOfWeeks.get(i)) + "(" + (i + 1) + ")");
        }
        Integer response = inputInt("Response (1-7):");

        if (response != null){
            return dayOfWeeks.get(response - 1);
        }
        return null;

    }




}
