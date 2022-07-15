package controllers;

import database.Database;

public class Context {
    TerminalController currentController = new SignInController(this);
    boolean exit = false;

    Database database;

    public Context(Database database){
        this.database = database;
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
