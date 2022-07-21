package utilities;

import java.io.File;

/* deleteDirectory method source: https://www.baeldung.com/java-delete-directory*/

/**
 * Utility class of methods relating to deleting files, used in removing folders created by unit tests.
 */
public class DeleteUtils {

    /**
     * Deletes a local file.
     * @param directoryToBeDeleted File - the file object of the file that is to be deleted.
     */
    public static void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
    }

}
