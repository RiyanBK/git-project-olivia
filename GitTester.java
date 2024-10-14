import java.io.File;
import java.nio.file.Paths;
import java.io.*;

public class GitTester {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            // part 1: create initial commit
            Git.deleteEverything(Paths.get("git"));
            // Testing git creation and repo initialization:
            File testFile = new File("testFile.txt");
            File testDir = new File("testDir");
            File testFile2 = new File("testDir/testFile2.txt");
            testFile.createNewFile();
            testDir.mkdir();
            testFile2.createNewFile();
            // do the commit
            Git dir = new Git();
            dir.stage("testFile.txt");
            dir.stage("testDir");
            dir.commit("Riyan", "part 1");

            // part 2: create second commit
            File newTestFile = new File("newTestFile.txt");
            newTestFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("newTestFile.txt"));
            writer.write("insert test content here");
            writer.close();
            // do the commit
            dir.stage("newTestFile.txt");
            dir.commit("Riyan", "part 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}