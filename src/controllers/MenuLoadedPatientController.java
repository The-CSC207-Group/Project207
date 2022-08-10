package controllers;

import java.util.LinkedHashMap;

/**
 * Controller class for processing commands that a user passes in while having loaded a patient.
 */
abstract public class MenuLoadedPatientController extends controllers.MenuController {

    private final UserController<?> previousController;

    /**
     * Creates a new controller for handling the state of when a user has loaded a patient.
     */
    public MenuLoadedPatientController(Context context, UserController<?> previousController) {
        super(context, previousController);
        this.previousController = previousController;
    }

    /**
     * Creates a Linked hashmap of all string representations of menu loaded patient commands mapped to the method that
     * each command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective menu loaded
     * patient commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("unload patient", Back(previousController));
        commands.putAll(super.AllCommands());
        return commands;
    }

}
