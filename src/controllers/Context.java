package controllers;

import database.DataMapperGateway;
import entities.User;

public class Context {
    TerminalController currentController = new SignInController(this);
    boolean exit = false;

    DataMapperGateway<User> database;

    public Context(DataMapperGateway<User> database){
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
