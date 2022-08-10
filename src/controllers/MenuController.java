package controllers;

import java.util.LinkedHashMap;

abstract public class MenuController extends TerminalController {

    private final UserController<?> previousController;

    public MenuController(Context context, UserController<?> previousController) {
        super(context);
        this.previousController = previousController;
    }

    /**
     * Creates a Linked hashmap of all string representations of menu commands mapped to the method that each
     * command calls.
     * @return LinkedHashMap<String, Command> - ordered HashMap of strings mapped to their respective menu commands.
     */
    @Override
    public LinkedHashMap<String, Command> AllCommands() {
        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("back", Back(previousController));
        commands.putAll(super.AllCommands());
        return commands;
    }

    protected Command Back(TerminalController previousController) {
        return (x) -> changeCurrentController(previousController);
    }

}
