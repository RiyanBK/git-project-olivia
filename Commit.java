import java.util.Calendar;
import java.nio.file.*;

public class Commit {
    private String name;
    private String summary;
    private String currentDate;

    public Commit (String author, String message) {
        name = author;
        summary = message;
        currentDate = getDate();
        //System.out.println (currentDate);
    }

    // public Path writeToIndex () {

    // }

    // public String getSHA1OfLatestCommit () {

    // }

    public String getDate () {
        Calendar date = Calendar.getInstance();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        return ((month+1) + "/" + day + "/" + year);
    }
}
