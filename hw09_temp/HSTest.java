import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class HSTest {

    public static void checkFiles(String fdoc, String fout, int score, String user) 
                    throws IOException, HighScores.FormatException
    {
        Reader in = new BufferedReader(new FileReader(fdoc));
        Writer out = new BufferedWriter(new FileWriter(fout));

        HighScores hs = new HighScores(score);

        hs.processDocument(in, out, user);
        in.close();
        out.flush();
        out.close();
    }



    @Test(timeout=500) public void testHighScoreGood() throws IOException, HighScores.FormatException {
        checkFiles("highscoretesting.txt", "highscorebase2.txt", 110, "TestUserName");
        compareDocs("highscorebase2.txt", "hsExpectedOut.txt");
    }
    //test adding to more than ten scores
    //test reading from file with incorrect format
    //test updateDoc
    //test nameScoreIsolate
    //test formatChecker

    @Test(timeout=500) public void testSortingScores() {
        Collection<Scores> testScores = new ArrayList<Scores>();
        testScores.add(new Scores("Player1", "100"));
        testScores.add(new Scores("Player3", "25"));
        testScores.add(new Scores("Player2", "50"));
        testScores.add(new Scores("Boop", "325"));
        testScores.add(new Scores("Player4", "500"));
        Collection<Scores> correctScores = new ArrayList<Scores>();
        correctScores.add(new Scores("Player4", "500"));
        correctScores.add(new Scores("Boop", "325"));
        correctScores.add(new Scores("Player1", "100"));
        correctScores.add(new Scores("Player2", "50"));
        correctScores.add(new Scores("Player3", "25"));
        HighScores.sortByScore(testScores);
        assertEquals(testScores, correctScores);
    }



    /**
     * This is a helper method that compares two documents.
     */
    public static void compareDocs(String out, String expected) 
            throws IOException, FileNotFoundException 
    {
        BufferedReader f1 = new BufferedReader(new FileReader(out));
        BufferedReader f2 = new BufferedReader(new FileReader(expected));

        try{
            String line1 = f1.readLine();
            String line2 = f2.readLine();
            while(line1 != null && line2 != null){
                assertEquals("Output file did not match expected output.", line2, line1);
                line1 = f1.readLine();
                line2 = f2.readLine();
            }
            if(line1 != null) {
                fail("Expected end of file, but found extra lines in the output.");
            } else if(line2 != null) {
                fail("Expected more lines, but found end of file in the output. ");
            }
        }
        finally{
            f1.close();
            f2.close();

        }
    }

    // Do NOT put your test cases here. Add them to MyTest.java
}
