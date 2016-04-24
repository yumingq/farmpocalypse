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



import org.junit.Test;


public class HSTest {

    /** spellCheckFiles Runs the spell checker on some test input.  See
     * the description of the inputs below.
     *
     * @param fdict The filename of the dictionary
     * @param dictSize If the int dictSize is -1, it is ignored.
     *    Otherwise we check the size of the dictionary after creating it,
     *    to make sure it was parsed correctly and the tests will work.
     * @param fcorr The filename of the corrections to use, or null
        if the swap corrector should be used.
     * @param fdoc The filename of the document to check
     * @param fout The filename where the output should be written
     * @param finput The filename where the user input should be read from
     */
    public static void checkFiles(String fdoc, String fout, String finput, int score) 
                    throws IOException, HighScores.FormatException
    {
        FileInputStream input = new FileInputStream(finput);
        Reader in = new BufferedReader(new FileReader(fdoc));
        Writer out = new BufferedWriter(new FileWriter(fout));

        HighScores hs = new HighScores(score);

        hs.processDocument(in, input, out);
        in.close();
        input.close();
        out.flush();
        out.close();
    }



    @Test(timeout=500) public void testCheckFoxGood() throws IOException, HighScores.FormatException {
        checkFiles("highscores.txt", "highscores.txt", "highscoretesting.txt", 110);
        compareDocs("foxout.txt", "theFox_expected_output.txt");
    }


//    @Test(timeout=500) public void testCheckMeanInput() throws IOException, HighScores.FormatException {
//        checkFiles();
//        compareDocs("foxout.txt", "theFox_expected_output.txt");
//    }
//
//
//    @Test(timeout=500) public void testCheckGettysburgSwap() throws IOException, HighScores.FormatException {
//        // Use the SwapCorrector instead!
//        checkFiles();
//        compareDocs("Gettysburg-out.txt", "Gettysburg_expected_output.txt");
//    }



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
