package presenter.screenViews;

import presenter.response.UserCredentials;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public abstract class ScreenView {

    protected String input(String prompt) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print(prompt);
        return inputScanner.nextLine().strip();
    }

    protected Integer inputInt(String prompt) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print(prompt);
        try {
            return inputScanner.nextInt();
        } catch (Exception ignored) {
            return null;
        }
    }

    protected void infoMessage(String message) {
        System.out.println(message);
    }

    protected void successMessage(String message) {
        System.out.println("✓ " + message);
    }

    protected void warningMessage(String message) {
        System.out.println("⚠ " + message);
    }

    protected void errorMessage(String message) {
        System.out.println("✗ " + message);
    }

    protected UserCredentials registerAccountPrompt(String accountType) {
        infoMessage("You are about to create a new " + accountType + " account.");
        String username = enterUsernamePrompt(accountType);
        String password = enterPasswordPrompt(accountType);
        return new UserCredentials(username, password);
    }

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


    protected String enterUsernamePrompt(String accountType) {
        return input("Enter " + accountType + " username: ");
    }

    protected String enterPasswordPrompt(String accountType) {
        return input("Enter " + accountType + " password: ");
    }

    public void showHelpPrompt() {infoMessage("Type 'help' to see a list of all possible commands.");}

}
