package controllers;

import dataBundles.*;
import entities.Admin;
import presenters.screenViews.AdminScreenView;
import useCases.*;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that an admin passes in.
 */
public class AdminController extends UserController<Admin> {

    private final AdminData adminData;
    private final AdminManager adminManager;
    private final AdminScreenView adminScreenView = new AdminScreenView();
    private final AdminController currentController = this;

    /**
     * Creates an admin controller object that handles the commands used by the current admin user.
     * @param context Context - a reference to the context object, which stores the current controller and allows for
     *                switching between controllers.
     * @param adminData AdminData - a data containing the ID and attributes of the current admin user.
     */
    public AdminController(Context context, AdminData adminData) {
        super(context, adminData, new AdminManager(context.getDatabase()), new AdminScreenView());
        this.adminData = adminData;
        this.adminManager = new AdminManager(getDatabase());

    }

    /**
     * Creates a Linked hashmap of all string representations of admin commands mapped to the method that each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective admin commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("manage users", LoadUserManagement());
        commands.put("change clinic info", changeClinicInformation());
        commands.put("delete self", deleteSelf());

        commands.putAll(super.AllCommands());
        return commands;
    }

    private Command LoadUserManagement(){
        return (x) -> {
            changeCurrentController(new AdminUserManagementController(getContext(), currentController, adminData));
            adminScreenView.showUserManagementWelcomeMessage();
        };
    }
    private Command deleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(getContext()));
        };
    }

    private Command changeClinicInformation() {
        return (x) -> changeCurrentController(new ClinicController(getContext(), currentController));
    }
}
