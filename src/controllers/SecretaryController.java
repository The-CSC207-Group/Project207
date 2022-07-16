package controllers;

import dataBundles.SecretaryDataBundle;
import entities.Secretary;
import useCases.accessClasses.DoctorAccess;
import useCases.accessClasses.SecretaryAccess;

import java.util.HashMap;

public class SecretaryController extends TerminalController {


    private SecretaryAccess secretaryAccess;
    private SecretaryDataBundle secretaryDataBundle;

    public SecretaryController(Context context, SecretaryDataBundle secretaryData) {
        super(context);
        this.secretaryAccess = new SecretaryAccess(getDatabase());
        this.secretaryDataBundle = secretaryData;
    }

    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap commands = super.AllCommands();
        return commands;
    }

    @Override
    void WelcomeMessage() {

    }
}
