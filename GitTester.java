import java.io.File;
import java.nio.file.Paths;
import java.io.*;

public class GitTester {
    @SuppressWarnings("unused")
    public static void main (String [] args) {
        try {
        //part 1: create initial commit
        Git.deleteEverything(Paths.get("git"));
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
        //do the commit
        Commit commitTestPart1 = new Commit ("Riyan", "part 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //part 2: create second commit
        File newTestFile = new File ("newTestFile.txt");
        newTestFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter ("newTestFile.txt"));
        writer.write ("insert test content here");
        writer.close();
        Git.createBlob(Paths.get("newTestFile.txt"), false);
        //do the commit
        Commit commitTestPart2 = new Commit("Riyan", "part 2");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}