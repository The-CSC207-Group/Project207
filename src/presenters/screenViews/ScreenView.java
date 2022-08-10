package presenters.screenViews;

import presenters.response.UserCredentials;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * An abstract class that all other ScreenView classes pull from.
 */
public abstract class ScreenView {
    /**
     * Create an input that will allow users to type text for the program to receive.
     * @param prompt the specific prompt to guide users on what to input.
     * @return a String represented the text inputted by the user.
     */
    protected String input(String prompt) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print(prompt);
        return inputScanner.nextLine().strip();
    }

    /**
     * Create an input that will allow users to type integers for the program to receive.
     * @param prompt the specific prompt to guide users on what to input.
     * @return an Integer represented the text inputted by the user.
     */
    protected Integer inputInt(String prompt) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return inputScanner.nextInt();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Print a message in the terminal.
     * @param message the text to be printed.
     */
    protected void infoMessage(String message) {
        System.out.println(message);
    }

    /**
     * prints a success message in the terminal.
     * @param message the text to be printed.
     */
    protected void successMessage(String message) {
        System.out.println("✓ " + message);
    }

    /**
     * prints a warning message in the terminal.
     * @param message the text to be printed.
     */
    protected void warningMessage(String message) {
        System.out.println("⚠ " + message);
    }

    /**
     * prints an error message in the terminal.
     * @param message the text to be printed.
     */
    protected void errorMessage(String message) {
        System.out.println("✗ " + message);
    }

    /**
     * Creates a new account by getting a unique username and password inputted by the user.
     * @param accountType a String that represents the type of account to be created. Ex: doctor, admin, patient, etc.
     * @return the UserCredentials of the newly created account.
     */
    protected UserCredentials registerAccountPrompt(String accountType) {
        infoMessage("You are about to create a new " + accountType + " account.");
        String username = enterUsernamePrompt(accountType);
        String password = enterPasswordPrompt(accountType);
        return new UserCredentials(username, password);
    }

    /**
     * Shows that an action cannot be undone with a warning.
     */
    protected void showIrreversibleActionWarning() {
        warningMessage("This action cannot be undone!");
    }


    /**
     * Ask user to enter a local date.
     * @return LocalDate if inputted date is valid.
     *         null if inputted date is invalid.
     */
    protected LocalDate showLocalDatePrompt() {
        Integer year = inputInt("Enter year (YYYY): ");
        if (year == null) {return null;}
        Integer month = inputInt("Enter month (MM): ");
        if (month == null) {return null;}
        Integer day = inputInt("Enter day (DD): ");
        if (day == null) {return null;}

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

    /**
     * Ask the user to enter a local time.
     * @return LocalTime if inputted time is valid. null if inputted time is invalid.
     */
    protected LocalTime showLocalTimePrompt(){
        Integer hour = inputInt("Enter 24hr Time (HH): ");
        if (hour == null){return null;}
        Integer minute = inputInt("Enter minute: (MM): ");
        if (minute == null){return null;}

        try{
            return LocalTime.of(hour, minute);
        }catch (DateTimeException ignored){
            return null;
        }
    }

    /**
     * Shows a username input, taking in text from the user.
     * @param accountType a String that represents the type of account to be created. Ex: doctor, admin, patient, etc.
     * @return a String representing the text inputted from the user.
     */
    protected String enterUsernamePrompt(String accountType) {
        return input("Enter " + accountType + " username: ");
    }

    /**
     * Shows a password input, taking in text from the user.
     * @param accountType a String that represents the type of account to be created. Ex: doctor, admin, patient, etc.
     * @return a String representing the text inputted from the user.
     */
    protected String enterPasswordPrompt(String accountType) {
        return input("Enter " + accountType + " password: ");
    }

    /**
     * Show a help message, instructing users on how to use the help command.
     */
    public void showHelpPrompt() {infoMessage("Type 'help' to see a list of all possible commands.");}

}
