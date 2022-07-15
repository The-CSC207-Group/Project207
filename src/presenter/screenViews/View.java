package presenter.screenViews;

import java.util.Scanner;

public abstract class View {
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

    public void errorMessage(String message) {
        System.out.println("✗ " + message);
    }
}
