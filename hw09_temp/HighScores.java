import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;



public class HighScores {
    private BufferedReader reader;
    private Set<String> containerOfLines;
    private int score;
    private Map<String, String> usersAndScores;

    public HighScores(int score) {
        this.score = score;
        usersAndScores = new HashMap<String, String>();
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

    public void processDocument(Reader in, InputStream input, Writer out) throws IOException, 
    FormatException {
        //check that the reader isn't null
        if (in == null) {
            throw new IllegalArgumentException();
        }
        
        Scanner sc = new Scanner(input); 
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
                usersAndScores.put(playerName, currScore);
            } else {
                if (currScore.compareTo(usersAndScores.get(playerName)) > 0) {
                    usersAndScores.put(playerName, currScore);
                }
            }
        }

        //add in the new one (ask for the name, use the score input)
        //get userInput for their name!
        String userInput = getNextString(sc);
        //put in the current username and score
        usersAndScores.put(userInput, Integer.toString(score));

        //sort the map by values (ascending)
        Map<String, String> sortedUsersScores = sortByValue(usersAndScores);
        //add the scores and names to lists with indices
        ArrayList<String> scoreList = new ArrayList<String>();
        ArrayList<String> playerList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : sortedUsersScores.entrySet())
        {
            scoreList.add(entry.getValue());
            playerList.add(entry.getKey());
        }

        //take the ending indices
        int listIndex = scoreList.size() - 1;
        //iterate through max of ten of them for high scores
        for (int i = 0; i <= 10; i++) {
            if (listIndex >= 0) {
                out.write(playerList.get(listIndex) + ", " + scoreList.get(listIndex));
            }
        }



    }

    //sorts a map by value
    public static <K, V extends Comparable<? super V>> Map<K, V> 
    sortByValue( Map<K, V> map )
    {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted( Map.Entry.comparingByValue() )
        .forEachOrdered( e -> result.put(e.getKey(), e.getValue()) );

        return result;
    }
}
