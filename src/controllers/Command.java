package controllers;

import java.util.ArrayList;

/**
 * Interface for all commands as part of the command pattern.
 */
public interface Command {
    /**
     * Runs the code in execute. to be used as part of the command pattern.
     *
     * @param args ArrayList<String> - the list of arguments passed in to the command
     */
    void execute(ArrayList<String> args);
}
