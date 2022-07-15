import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import utilities.DeleteUtils;

import java.io.File;

public class LogManagerTests {

    @Rule
    public TemporaryFolder databaseFolder = new TemporaryFolder();

    @After
    public void after() {
        DeleteUtils.deleteDirectory(new File(databaseFolder.toString()));
    }
}
