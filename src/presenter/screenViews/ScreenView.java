package presenter.screenViews;

import presenter.response.UserCredentials;

import java.util.List;
import java.util.Scanner;

public abstract class ScreenView {
    Scanner input = new Scanner(System.in);

    protected String input(String prompt) {
        System.out.println(prompt);
        return input.nextLine().strip();
    }

    protected Integer inputInt(String prompt) {
        System.out.println(prompt);
        String string = input(prompt);
        return Integer.getInteger(string) - 1; // -1 since Integer is one more than index, and we want to return index.
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

    protected String enterUsernamePrompt(String accountType) {
        return input("Enter " + accountType + " username: ");
    }

    protected String enterPasswordPrompt(String accountType) {
        return input("Enter " + accountType + " password: ");
    }

}
