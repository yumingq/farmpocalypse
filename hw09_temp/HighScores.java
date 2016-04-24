import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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

    public Map<String, String> nameScoreIsolate(Set<String> lines) {
        for (String current : lines) {
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

        return usersAndScores;
    }

    public void processDocument(Reader in, InputStream input, Writer out) throws IOException, 
    FormatException {
        //check that the reader isn't null
        if (in == null) {
            throw new IllegalArgumentException();
        }

        Set<String> lineContainer = formatChecker(in);

        Scanner sc = new Scanner(input); 

        usersAndScores = nameScoreIsolate(lineContainer);

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
        //        int listIndex = scoreList.size() - 1;
        int listIndex = 0;
        //iterate through max of ten of them for high scores
        for (int i = 0; i <= 10; i++) {
            if (listIndex < scoreList.size()) {
                out.write(playerList.get(listIndex) + ", " + scoreList.get(listIndex));
                out.write(System.getProperty("line.separator"));
            }
            listIndex++;
        }



    }
    
    //sorts a map by value
    public static <K, V extends Comparable<? super V>> Map<K, V> 
    sortByValue( Map<K, V> map )
    {
        System.out.println("Input: ");//debugging
        for (Map.Entry<K, V> entry : map.entrySet()) { //debugging
            System.out.println(entry.getKey() + " " + entry.getValue()); //debugging
        }//debugging
        System.out.println("----------------");//debugging
        
        //convert map to list of map entries
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        //sort the map with a new comparator
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            //compare using values
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        //create a new map
        Map<K, V> result = new LinkedHashMap<K, V>();
        //put the ordered list of map entries into the map
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        
        System.out.println("Output: ");//debugging
        for (Map.Entry<K, V> entry : result.entrySet()) {//debugging
            System.out.println(entry.getKey() + " " + entry.getValue());//debugging
        }//debugging
        System.out.println("----------------");//debugging
        return result;
    }

//    //sorts a map by value
//    public static <K, V extends Comparable<? super V>> Map<K, V> 
//    sortByValue( Map<K, V> map )
//    {
//        for (Map.Entry<K, V> entry : map.entrySet()) { //debugging
//            System.out.println(entry.getKey() + " " + entry.getValue()); //debugging
//        }//debugging
//        System.out.println("----------------");//debugging
//        
//        List<Map.Entry<K, V>> list =
//                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
//        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
//        {
//            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
//            {
//                return (o1.getValue()).compareTo( o2.getValue() );
//            }
//        } );
//
//        Map<K, V> result = new LinkedHashMap<K, V>();
//        for (Map.Entry<K, V> entry : list)
//        {
//            result.put( entry.getKey(), entry.getValue() );
//        }
//        
//        for (Map.Entry<K, V> entry : result.entrySet()) {//debugging
//            System.out.println(entry.getKey() + " " + entry.getValue());//debugging
//        }//debugging
//        return result;
//    }
}
