package controllers;

import dataBundles.AdminData;
import entities.Admin;
import presenters.screenViews.AdminScreenView;
import useCases.AdminManager;

import java.util.LinkedHashMap;

/**
 * Controller class that processes the commands that an admin passes in.
 */
public class AdminController extends UserController<Admin> {

    private final AdminData adminData;
    private final AdminManager adminManager;
    private final AdminController currentController = this;

    /**
     * Creates an admin controller object that handles the commands used by the current admin user.
     *
     * @param context   Context - a reference to the context object, which stores the current controller and allows for
     *                  switching between controllers.
     * @param adminData AdminData - a data containing the ID and attributes of the current admin user.
     */
    public AdminController(Context context, AdminData adminData) {
        super(context, adminData, new AdminManager(context.getDatabase()), new AdminScreenView());
        this.adminData = adminData;
        this.adminManager = new AdminManager(getDatabase());

    }

    /**
     * Creates a Linked hashmap of all string representations of admin commands mapped to the method that each command
     * calls.
     *
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective admin commands.
     */
    @Override
    public LinkedHashMap<String, Command> allCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("manage users", LoadUserManagement());
        commands.put("change clinic info", ChangeClinicInformation());
        commands.put("delete self", DeleteSelf());

        commands.putAll(super.allCommands());
        return commands;
    }

    private Command LoadUserManagement() {
        return (x) -> changeCurrentController(
                new AdminUserManagementController(getContext(), currentController, adminData)
        );
    }

    private Command DeleteSelf() {
        return (x) -> {
            adminManager.deleteUserByData(adminData);
            changeCurrentController(new SignInController(getContext()));
        };
    }

    private Command ChangeClinicInformation() {
        return (x) -> changeCurrentController(new ClinicController(getContext(), currentController));
    }

}
