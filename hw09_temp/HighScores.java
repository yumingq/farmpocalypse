import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
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

//    private class Scores {
//        private String name;
//        private String score;
//
//        public Scores(String name, String score) {
//            this.name = name;
//            this.score = score;
//        }
//
//        public String getScore() {
//            return score;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//    }

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

    public Set<String> formatChecker(Reader in) throws IOException, FormatException {
        //check that the reader isn't null
        if (in == null) {
            throw new IllegalArgumentException();
        }

        reader = new BufferedReader(in);
        containerOfLines = new TreeSet<String>();

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
        return (TreeSet<String>) containerOfLines;
    }

    public Collection<Scores> nameScoreIsolate(Set<String> lines) {
        Collection<Scores> scoreList = new ArrayList<Scores>();

        for (String current : lines) {
            int commaPos = current.indexOf(",");
            //isolate the left part of the comma
            String playerName = current.substring(0, (commaPos)); 
            //trim off any white space
            playerName = playerName.trim();
            //isolate right part of comma
            String currScore = current.substring((commaPos + 1), current.length()); 
            currScore = currScore.trim();

            scoreList.add(new Scores(playerName, currScore));
        }

        return scoreList;
    }

    public static Collection<Scores> sortByScore(Collection<Scores> unsortedScores) {
        List<Scores> sortedList = (ArrayList<Scores>) unsortedScores;
        
        Collections.sort(sortedList, new Comparator<Scores>() {
            @Override
            public int compare(Scores o1, Scores o2) {
                return o1.getScore().compareTo(o2.getScore());
            }

        });
        
        return sortedList;
    }

    public void processDocument(Reader in, InputStream input, Writer out) throws IOException, 
    FormatException {
        //check that the reader isn't null
        if (in == null) {
            throw new IllegalArgumentException();
        }

        Set<String> lineContainer = formatChecker(in);

        Scanner sc = new Scanner(input); 

        Collection<Scores> userScores;
        userScores = nameScoreIsolate(lineContainer);

        //add in the new one (ask for the name, use the score input)
        //get userInput for their name!
        String userInput = getNextString(sc);
        //put in the current username and score
        userScores.add(new Scores(userInput, Integer.toString(score)));

        //sort the map by values (ascending)
        Collection<Scores> sortedUsersScores = sortByScore(userScores);

        for(Scores indiv: sortedUsersScores) {
            out.write(indiv.getName() + ", " + indiv.getScore());
            out.write(System.getProperty("line.separator"));
        }


    }


}


