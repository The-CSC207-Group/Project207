package controllers;

import java.util.ArrayList;

/**
 * interface for all commands as part of the command pattern.
 */
public interface Command {
    /**
     * runs the code in execute. to be used as part of the command pattern.
     * @param args
     */
    void execute(ArrayList<String> args);
}
