package Utilities;

import java.io.File;

/* deleteDirectory method source: https://www.baeldung.com/java-delete-directory*/

public class DeleteUtils {
    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
