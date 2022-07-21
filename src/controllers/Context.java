package controllers;

import database.Database;
import presenter.screenViews.TerminalScreenView;

/**
 * Controller class that stores the current controller of the program and allows the program to switch controllers.
 */
public class Context {

    private TerminalController currentController;
    private boolean exit = false;
    private final Database database;

    /**
     * Initializes the context object, which initially stores the sign in controller.
     * @param database Database - the collection of all entity databases in the program.
     */
    public Context(Database database) {
        this.database = database;
        this.currentController = new SignInController(this);
    }

    /**
     * Starts the program loop, running the controller stored in the context until the program is exited.
     */
    public void run(){
        currentController.welcomeMessage();
        while (!exit){
            currentController.run();
        }
    }

    /**
     * Updates the current controller to the one passed into the parameter.
     * @param newController TerminalController - the new controller that the context will store.
     */
    public void changeController(TerminalController newController){
        currentController = newController;
        currentController.welcomeMessage();
    }

    /**
     * Stops the program loop, exits the program.
     */
    void exit(){
        exit = true;
    }

    /**
     * Returns the database associated with this program.
     * @return Database - collection of all the entity databases in the program.
     */
    public Database getDatabase() {
        return this.database;
    }

}
