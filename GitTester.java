import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitTester {
    public static void main (String [] args) {
        try {
        //Testing git creation and repo initialization:
        File testFile = new File ("testFile.txt");
        File testDir = new File("testDir");
        File testFile2 = new File ("testDir/testFile2.txt");
        testFile.createNewFile();
        testDir.mkdir();
        testFile2.createNewFile();
        Git.initRepo();
        Git.createBlob(Paths.get("testFile.txt"), false);
        Git.createBlob(Paths.get("testDir"), false);
        //
        Commit commitTest = new Commit ("Riyan", "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
