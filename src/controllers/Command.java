package controllers;

import java.util.ArrayList;

public interface Command {

    /**
     * @return true to continue in the current controller.
     *         false to leave the current controller and go back to the
     *         controller the current controller was instantiated at.
     */
    void execute(ArrayList<String> args);
}
