package presenter.screenViews;

import presenter.response.UserCredentials;

import java.util.Scanner;

public abstract class ScreenView {

    protected String input(String prompt) {
        Scanner input = new Scanner(System.in);
        infoMessage(prompt);
        return input.nextLine();
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
        String username = input("Enter " + accountType + " username: ");
        String password = input("Enter " + accountType + " password: ");
        return new UserCredentials(username, password);
    }

    protected String enterPatientUsernamePrompt() {
        return input("Enter patient username: ");
    }

    protected String enterDoctorUsernamePrompt() {
        return input("Enter doctor username: ");
    }
}
