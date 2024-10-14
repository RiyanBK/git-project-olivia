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
        if (!headHash.equals("")) {
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
            // create a file to put the commit in
            // make the commit file
            // get that hash and put it in head

            File tempCommitFile = File.createTempFile("contentToHash", null);
            FileWriter writer = new FileWriter(tempCommitFile);
            writer.write(commitContent);
            writer.close();
            String commitHash = Git.sha1(tempCommitFile.toPath());
            writer = new FileWriter(new File("./git/HEAD"));
            writer.write(commitHash);
            writer.close();
            writer = new FileWriter(new File("./git/objects/" + commitHash));
            writer.write(commitContent);
            writer.close();
            // creates tree snapshot of the tree and puts in objects
            BufferedReader reader = new BufferedReader(new FileReader("./git/index"));
            StringBuffer sb = new StringBuffer();
            while (reader.ready()) {
                sb.append((char) reader.read());
            }
            reader.close();
            writer = new FileWriter(new File("./git/objects/" + treeHash));
            writer.write(sb.toString());
            writer.close();
            // overwrites index once commit is created
            writer = new FileWriter(new File("./git/index"));
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

    public void writeToIndex() {
        // part 1 finds the hash of the previous tree
        // currently we have the hash of the commit
        String oldTreeHash = "";
        try {
            // this finds the commit
            BufferedReader reader = new BufferedReader(new FileReader("./git/objects/" + headHash)); 
            // read just after the first 6 characters of the tree:
            // ("tree: " + hash of tree) 
            // we just want the hash of the tree
            oldTreeHash = reader.readLine();
            oldTreeHash = oldTreeHash.substring(6);
            reader.close();

            if (oldTreeHash.equals("")) {
                return;
            }

            // part 2 actually writes to the file
            reader = new BufferedReader(new FileReader("./git/objects/" + oldTreeHash));
            while (reader.ready()) { // read index list of previous tree
                Git.createBlob(Paths.get(reader.readLine().substring(46)), false); 
                // add all files not already listed in index to index
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getTreeContents() { 
        // StringBuffer content = new StringBuffer("");
        // try {
        // BufferedReader reader = new BufferedReader(new FileReader(new
        // File("./git/index")));
        // while (reader.ready()) {
        // content.append((char) reader.read());
        // }
        // reader.close();
        // } catch (Exception e) {
        // e.printStackTrace();
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
