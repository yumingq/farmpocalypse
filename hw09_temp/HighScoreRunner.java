import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class HighScoreRunner {

    public static void addHighScore(String fileToRead, String fileToWrite, int score) {
        try {
            Reader in = new BufferedReader(new FileReader(fileToRead));
            Writer out = new BufferedWriter(new FileWriter(fileToWrite));
            HighScores hs = new HighScores(score);
            hs.processDocument(in, System.in, out);
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("error while checking document: " + e.getMessage());
        } catch (HighScores.FormatException e) {
            System.out.println("format error while checking document: " + e.getMessage());
        }
    }
}
