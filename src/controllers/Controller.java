package controllers;

import database.DataMapperGateway;
import entities.User;

public class Controller {
    TerminalController currentController = new ApplicationController(this);
    boolean exit = false;

    DataMapperGateway<User> database;

    public Controller(DataMapperGateway<User> database){
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
