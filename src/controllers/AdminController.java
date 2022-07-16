package controllers;

import dataBundles.AdminData;
import dataBundles.SecretaryData;
import useCases.accessClasses.AdminAccess;
import useCases.accessClasses.SecretaryAccess;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminController extends TerminalController{
    private AdminAccess adminAccess;
    private AdminData adminData;
    public AdminController(Context parent, AdminData adminData) {
        super(parent);
        this.adminData = adminData;
        this.adminAccess = new AdminAccess(getDatabase());
    }
    @Override
    public HashMap<String, Command> AllCommands() {
        HashMap commands = super.AllCommands();
        commands.put("Create Secretary Account", new CreateSecretaryAccount());
        return commands;
    }

    @Override
    void WelcomeMessage() {

    }
    class CreateSecretaryAccount implements Command{

        @Override
        public boolean execute(ArrayList<String> args) {
            return false;
        }
    }
}
