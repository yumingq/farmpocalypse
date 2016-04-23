
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;

/**
 * Provides a token Iterator for a given Reader.
 * <p>
 * Hint: See the code for the WordScanner from Lecture.
 */
public class ScoreScanner implements Iterator<String> {
    private BufferedReader reader;
    private int n = -1;
    /**
     * Creates a TokenScanner for a given Reader.
     * <p>
     * As an Iterator, the TokenScanner should only read from the Reader as much
     * as is necessary to determine getNext() and next(). The TokenScanner should
     * NOT read the entire stream and compute all of the tokens in advance.
     * <p>
     *
     * @param in the source Reader for character data
     * @throws IOException if there is an error in reading
     * @throws IllegalArgumentException when the provided Reader is null
     */
    public ScoreScanner(java.io.Reader in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException();
        }
        reader = (BufferedReader) in;
        try {
            n = reader.read();
        } catch (IOException e) {
            n = -1;
        }
    }

    

    /**
     * Determines whether a given character is a valid word character.
     * <p>
     * Valid word characters are letters (according to
     * Character.isLetter) and single quote '\''.
     *
     * @param c the character to check
     * @return true if the character is a word character
     */
    public static boolean isWordCharacter(int c) {
        return (Character.isLetter(c) || (c == '\''));
    }


    /**
     * Determines whether a given String is a valid word
     * <p>
     * Valid words are not null or the empty string. They 
     * only contain word characters.
     *
     * @param s the string to check
     * @return true if the string is a word
     */
    public static boolean isWord(String s) {
        //empty strings and null are not words
        if (s == null || s == "") {
            return false;
        }

        boolean wordOrNot = true;

        //go through each character and see if it's a word character
        for(int i = 0; i < s.length(); i++) {
            if (!ScoreScanner.isWordCharacter(s.charAt(i))) {
                wordOrNot = false;
            }
        }
        return wordOrNot;
    }

    /**
     * Determines whether there is another token available.
     */
    public boolean hasNext() {
        return (n != -1);
    }

    /**
     * Returns the next token, or throws a NoSuchElementException if none remain.
     *
     * @throws NoSuchElementException when the end of stream is reached
     */
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        //initiate a string to put the answer in
        String answer = "";

        try {
            if (isWordCharacter(n)){
                //keep adding chars until you reach a non word char or the end
                while (isWordCharacter(n) && hasNext()) {
                    answer = answer + (char)n;
                    n = reader.read();
                }
            } else {
                //or keep adding chars until you reach a word char or the end
                while ((!isWordCharacter(n) && hasNext())) {
                    answer = answer + (char)n;
                    n = reader.read();
                }
            }
        } catch (IOException e) {
            throw new NoSuchElementException();
        } 
        return answer;
    }

    /**
     * We don't support this functionality with TokenScanner, but since
     * the method is required if implementing Iterator, we just
     * <code>throw new UnsupportedOperationException();</code>
     *
     * @throws UnsupportedOperationException since we do not support 
     * this functionality
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
