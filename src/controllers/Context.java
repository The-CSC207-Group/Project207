package controllers;

import database.Database;

public class Context {
    TerminalController currentController;
    boolean exit = false;

    Database database;

    public Context(Database database){
        this.database = database;
        this.currentController = new SignInController(this);
    }
    public void run(){
        while (!exit){
            currentController.ProcessCommands();
        }
    }
    void changeController(TerminalController new_controller){
        currentController = new_controller;
        currentController.WelcomeMessage();
    }
    void exit(){
        exit = true;
    }
}
