import java.util.Calendar;
import java.io.*;
import java.nio.file.*;

public class Commit {
    // private String name;
    // private String summary;
    private String currentDate;
    private String headHash;
    private String treeHash;

    public Commit(String author, String message) {
        currentDate = getDate();
        findHeadHash();
        if (!headHash.equals("")) { // da39a3ee5e6b4b0d3255bfef95601890afd80709
            // add extra files from head into index
            writeToIndex();
        }
        getTreeContents();
        StringBuffer toCommit = new StringBuffer("");
        toCommit.append("tree: " + treeHash);
        toCommit.append("\nparent: " + headHash);
        toCommit.append("\nauthor: " + author);
        toCommit.append("\ndate: " + currentDate);
        toCommit.append("\nmessage: " + message);
        String commitContent = toCommit.toString();
        try {
        Files.createTempFile ("./contentToHash", null);
        FileWriter writer = new FileWriter(new File ("./contentToHash"));
        writer.write (commitContent);
        String commitHash = Git.sha1(Paths.get("./contentToHash")); 
        writer.close();
        writer = new FileWriter(new File ("./git/HEAD"));
        writer.write (commitHash);
        writer.close();
        writer = new FileWriter(new File ("./git/index"));
        writer.write("");
        writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void findHeadHash() {
        StringBuffer headHashReader = new StringBuffer("");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("./git/HEAD")));
            while (reader.ready()) {
                headHashReader.append(((char) reader.read()));
            }
            reader.close();
            headHash = headHashReader.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToIndex () {

    }

    public void getTreeContents() { //currently only works for index file
        // StringBuffer content = new StringBuffer("");
        // try {
        //     BufferedReader reader = new BufferedReader(new FileReader(new File("./git/index")));
        //     while (reader.ready()) {
        //         content.append((char) reader.read());
        //     }
        //     reader.close();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        try {
            treeHash = Git.sha1(Paths.get("./git/index"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        Calendar date = Calendar.getInstance();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        return ((month + 1) + "/" + day + "/" + year);
    }
}
