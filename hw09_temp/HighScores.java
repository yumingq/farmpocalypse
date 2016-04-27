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

    public HighScores(int score) {
        this.score = score;
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

    public void updateDoc(Reader in, Writer out) throws IOException,FormatException {
        if (in == null) {
            throw new IllegalArgumentException();
        }
        Set<String> lineContainer = formatChecker(in);
        for(String indiv: lineContainer) {
            out.write(indiv);
            out.write(System.getProperty("line.separator"));
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Collection<Scores> sortByScore(Collection<Scores> unsortedScores) {
        List<Scores> sortedList = (ArrayList<Scores>) unsortedScores;
        Collections.sort((List<Scores>) sortedList);
        
        return sortedList;
    }

    public void processDocument(Reader in, Writer out, String user) throws IOException, 
    FormatException {
        //check that the reader isn't null
        if (in == null) {
            throw new IllegalArgumentException();
        }
        
        //check formatting
        Set<String> lineContainer = formatChecker(in);
        
        //isolate the name vs the score
        Collection<Scores> userScores;
        userScores = nameScoreIsolate(lineContainer);

        //put in the current username and score
        userScores.add(new Scores(user, Integer.toString(score)));

        //sort the map by values (ascending)
        Collection<Scores> sortedUsersScores = sortByScore(userScores);

        int maxScores = 10;
        for(Scores indiv: sortedUsersScores) {
            if (maxScores > 0) {
            out.write(indiv.getName() + ", " + indiv.getScore());
            out.write(System.getProperty("line.separator"));
            } 
            maxScores--;
        }


    }


}


