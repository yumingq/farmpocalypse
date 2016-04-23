import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;



public class HighScores {
    private BufferedReader reader;
    private Set<String> containerOfLines;
    private int score;
    private Collection<String> scores;
    private Map<String, Collection<String>> usersAndScores;

    public HighScores(int score) {
        this.score = score;
        //create a sorted map of scores
        scores = new ArrayList<String>();
    }


    /**
     * Returns the next string input from the Scanner.
     *
     * @param sc
     */
    private String getNextString(Scanner sc) {
        return sc.next();
    }

    @SuppressWarnings("serial")
    public static class FormatException extends Exception {
        public FormatException(String msg) {
            super(msg);
        }
    }

    public void checkDocument(Reader in, InputStream input, Writer out) throws IOException, 
    FormatException {
        
        if (in == null) {
            throw new IllegalArgumentException();
        }
        
        Scanner sc = new Scanner(input); 
        ScoreScanner ss = new ScoreScanner(in);
        reader = new BufferedReader(in);
        containerOfLines = new TreeSet<>();
        
        try {
            String current = ((BufferedReader) reader).readLine();
            //while there's still stuff to read
            while(current != null) {
                //variables to track the correct format
                current = current.trim();
                boolean commaAtCorrectPos = true;
                int onlyOneComma = 0;

                //read each line and see if it is formatted correctly
                for(int i = 0; i < current.length(); i++) {
                    //how many commas are there in this line?
                    if (current.charAt(i) == ',') {
                        onlyOneComma++;
                        //are the commas in plausible positions?
                        if (i == 0 || i == current.length() - 1) {
                            commaAtCorrectPos = false;
                        }
                    }
                }
                
                //if they're not formatted correctly, throw format exceptions
                if (onlyOneComma != 1) {
                    throw new FormatException("wrong # of commas");
                } else if (commaAtCorrectPos == false) {
                    throw new FormatException("wrong position for comma");
                }
                
                containerOfLines.add(current);
                current = ((BufferedReader) reader).readLine();
            }
        } finally {
            reader.close();
        }
        
        
        
        //
        
        for (String current : containerOfLines) {
            int commaPos = current.indexOf(",");
            //isolate the left part of the comma
            String playerName = current.substring(0, (commaPos)); 
            //trim off any white space
            playerName = playerName.trim();
            //isolate right part of comma
            String currScore = current.substring((commaPos + 1), current.length()); 
            currScore = currScore.trim();
            if (!usersAndScores.containsKey(playerName)) {
                Collection<String> scoreList = new ArrayList<String>();
                scoreList.add(currScore);
                usersAndScores.put(playerName, scoreList);
            } else {
                Collection<String> scoreList = usersAndScores.get(playerName);
                scoreList.add(currScore);
                usersAndScores.put(playerName, scoreList);
            }
        }
        
        scores = usersAndScores.values();
        
      //get userInput for their name!
        String userInput = getNextString(sc);
        
        int index = 0;
        for (String currScore : scores) {
            int comp = currScore.compareTo(Integer.toString(score));
            if (comp == 0) {
                
            } else if (comp == 1) {
                
            } else {
                
            }
            index++;
        }
    }
}
