import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

/** Put your OWN test cases in this file, for all classes in the assignment. */
public class HighScoresTest {

    private Set<String> makeSet(String[] strings) {
        Set<String> mySet = new TreeSet<String>();
        for (String s : strings) {
            mySet.add(s);
        }
        return mySet;
    }

    //input is empty test
    @Test public void testEmpty() {
        assertFalse(HighScores.isWordCharacter(' '));
    }

    //input contains a single word token test
    @Test public void testIsSingleWord() {
        assertTrue(TokenScanner.isWord("amazing"));
    } 

    //input contains a single nonword token test
    @Test public void testIsSingleNonWord() {
        assertFalse(TokenScanner.isWord("????"));
    } 

    //input for token scanner is null
    @Test public void testNullTokenScanner() throws IOException {
        Reader in = null;
        try {
            TokenScanner d = new TokenScanner(in);
        } catch(IllegalArgumentException e) {
            //do nothing
        }
    }

    //input contains both word tokens and nonword tokens and ends with word
    @Test public void testEndsWithWord() throws IOException {
        Reader in = new StringReader("Aren't you \ntired"); 
        TokenScanner d = new TokenScanner(in);
        try {
            assertTrue("has next", d.hasNext());
            assertEquals("Aren't", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals(" ", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals("you", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals(" \n", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals("tired", d.next());

            assertFalse("reached end of stream", d.hasNext());
        } finally {
            in.close();
        }
    }
    //input contains both word tokens and nonword tokens, and ends with nonword
    @Test public void testEndsWithNonword() throws IOException {
        Reader in = new StringReader("Aren't you \ntired?"); 
        TokenScanner d = new TokenScanner(in);
        try {
            assertTrue("has next", d.hasNext());
            assertEquals("Aren't", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals(" ", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals("you", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals(" \n", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals("tired", d.next());

            assertTrue("has next", d.hasNext());
            assertEquals("?", d.next());

            assertFalse("reached end of stream", d.hasNext());
        } finally {
            in.close();
        }
    }

    //test number characters
    @Test public void testNumberIsNotWordCharacter() {
        assertFalse(TokenScanner.isWordCharacter('1'));
    }

    //input contains one word, testing token wrapper
    @Test public void testOneWordToken() throws IOException {
        Reader in = new StringReader("Please"); 
        TokenScanner d = new TokenScanner(in);
        try {
            assertTrue("has next", d.hasNext());
            assertEquals("Please", d.next());

            assertFalse("reached end of stream", d.hasNext());
        } finally {
            in.close();
        }
    }

    //input contains one nonword, testing token wrapper
    @Test public void testOneNonWordToken() throws IOException {
        Reader in = new StringReader("!!?"); 
        TokenScanner d = new TokenScanner(in);
        try {
            assertTrue("has next", d.hasNext());
            assertEquals("!!?", d.next());

            assertFalse("reached end of stream", d.hasNext());
        } finally {
            in.close();
        }
    }

    //test getNumWords
    @Test(timeout=500) public void testSimpleDictionaryNumWords() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertEquals("NumWords should be 32", 32, d.getNumWords());
    }


    //test for a word not in and in dictionary
    @Test(timeout=500) public void testIsWordInDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertTrue("Yours is in small dictionary", d.isWord("Yours"));
        assertFalse("poop is not in small dictionary", d.isWord("poop"));
    }

    //test for different capitalization of input word and dictionary word
    @Test(timeout=500) public void testCapitalizationDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertTrue("YOU is in small dictionary (capitalization)", d.isWord("YOU"));
    }


    //test for a word not in and in dictionary
    @Test(timeout=500) public void testEmptyStringNotInDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("smallDictionary.txt")));
        assertFalse("Empty String should not be in Dictionary", d.isWord(""));
    }

    //dictionary made with blank lines present
    @Test(timeout=500) public void testBlankLinesDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("dictionarytest.txt")));
        //test duplicate word count and blank spaces
        assertEquals("duplicate words # in dictionary", 6, d.getNumWords());
    }

    //dictionary with mixed case words
    @Test(timeout=500) public void testMixedCases() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("dictionarytest.txt")));
        assertTrue("hAMburger is in small dictionary", d.isWord("hamburger"));

    }

    //test when there is random white space in a line in the dictionary
    @Test(timeout=500) public void testWhiteSpace() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("dictionarytest.txt")));
        //test white space
        assertTrue("whitespace is in small dictionary", d.isWord("whitespace"));
    }

    //dictionary made with empty file
    @Test(timeout=500) public void testEmptyDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("empty.txt")));
        assertEquals("empty file # words in dictionary", 0, d.getNumWords());
    }

    //null isn't a word in dictionary
    @Test(timeout=500) public void testNullDictionary() throws IOException {
        Dictionary d = new Dictionary(new TokenScanner(new FileReader("empty.txt")));
        assertFalse("null word attempt to add to dict", d.isWord(null));
    }

    //test a ton of commas (format exception) in FileCorrector
    @Test public void testTooManyCommasFileCorr() throws IOException, 
    FileCorrector.FormatException  {
        try {
            Corrector c = new FileCorrector(new StringReader("too, many, commas"));
            fail("This is a bad format");
        } catch (FileCorrector.FormatException e) {
            // do nothing
        }
    }

    //test a commas in the wrong position in FileCorrector
    @Test public void testCommaAtBeginningFileCorr() throws IOException, 
    FileCorrector.FormatException  {
        try {
            Corrector c = new FileCorrector(new StringReader(",comma position"));
            fail("This is a bad format");
        } catch (FileCorrector.FormatException e) {
            // do nothing
        }
    }

    //test a commas in the wrong position in FileCorrector
    @Test public void testCommaAtEndFileCorr() throws IOException, FileCorrector.FormatException  {
        try {
            Corrector c = new FileCorrector(new StringReader("comma position,"));
            fail("This is a bad format");
        } catch (FileCorrector.FormatException e) {
            // do nothing
        }
    }

    //test a commas in the wrong position in FileCorrector WITH spaces
    @Test public void testCommaAtEndWithSpacesFileCorr() throws IOException, 
    FileCorrector.FormatException  {
        try {
            Corrector c = new FileCorrector(new StringReader("  comma position,    "));
            fail("This is a bad format");
        } catch (FileCorrector.FormatException e) {
            // do nothing
        }
    }

    //test leading or trailing whitespace around misspelled 
    //can I copy over makeSet?
    @Test public void testWhiteSpaceFileCorr() throws IOException, FileCorrector.FormatException  {
        Corrector c = FileCorrector.make("whitespaceCorrectorTest.txt");
        assertEquals("lyon -> lion", makeSet(new String[]{"lion"}), c.getCorrections("lyon"));
        //test leading or trailing whitespace around correct
        assertEquals("chimpanze -> chimpanzee", makeSet(new String[]{"chimpanzee"}), 
                c.getCorrections("chimpanze"));
        //test whitespace around both
        assertEquals("gose -> goose", makeSet(new String[]{"goose"}), c.getCorrections("gose"));
    }

    //test capitalization cases
    @Test public void testCapitalFileCorr() throws IOException, FileCorrector.FormatException  {
        Corrector c = FileCorrector.make("smallMisspellings.txt");
        //test all caps
        assertEquals("LYON -> Lion", makeSet(new String[]{"Lion"}), c.getCorrections("LYON"));
        //test mixed caps, case 1 (first is not cap)
        assertEquals("lYoN -> lion", makeSet(new String[]{"lion"}), c.getCorrections("lYoN"));
        //test mixed caps, case 2 (first is cap)
        assertEquals("LyON -> Lion", makeSet(new String[]{"Lion"}), c.getCorrections("LyON"));
        //test all lowercase
        assertEquals("lyon -> lion", makeSet(new String[]{"lion"}), c.getCorrections("lyon"));
    }

    //test a word that is already correct
    @Test public void testAlreadyCorrectFileCorr() throws IOException, 
    FileCorrector.FormatException  {
        Corrector c = FileCorrector.make("smallMisspellings.txt");
        Set<String> emptySet= new TreeSet<>();
        assertEquals("lion -> empty", emptySet, c.getCorrections("lion"));
    }

    //test multiple corrections, file corrector
    @Test public void testMultipleCorrFileCorr() throws IOException, 
    FileCorrector.FormatException  {
        Corrector c = FileCorrector.make("whitespaceCorrectorTest.txt");
        assertEquals("gase -> {goose,gas,graze}", makeSet(new String[]{"goose", "gas", "graze"}), 
                c.getCorrections("gase"));
    }

    //test swapcorrector with null
    @Test public void testSwapCorrectorNullReader() throws IOException, 
    FileCorrector.FormatException {
        try {
            new SwapCorrector(null);
            fail("Expected an IllegalArgumentException - cannot create SwapCorrector with null.");
        } catch (IllegalArgumentException f) {    
            //Do nothing. It's supposed to throw an exception
        }
    }

    //what if the word already exists, swap
    @Test public void testAlreadyCorrectSwapCorr() throws IOException {
        Reader reader = new FileReader("smalldictionary.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            //should be empty
            assertEquals("cay -> {}", makeSet(new String[]{}), swap.getCorrections("cay"));
        } finally {
            reader.close();
        }
    }

    //can't swap null words
    @Test public void testNullWordsSwapCorr() throws IOException {
        Reader reader = new FileReader("smalldictionary.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            try {
                swap.getCorrections(null);
                fail("Expected an IllegalArgumentException - can't get corrections with null.");
            } catch (IllegalArgumentException f) {    
                //Do nothing. It's supposed to throw an exception
            }
        } finally {
            reader.close();
        }
    }

    //mixed cases input test for swap corrector
    @Test public void testMixedCaseInputSwapCorr() throws IOException {
        Reader reader = new FileReader("smalldictionary.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            //input is mixed case
            assertEquals("cYA -> {cay}", makeSet(new String[]{"cay"}), swap.getCorrections("cYA"));
            //input is all caps
            assertEquals("CYA -> {Cay}", makeSet(new String[]{"Cay"}), swap.getCorrections("CYA"));
        } finally {
            reader.close();
        }
    }

    //mixed cases dictionary test for swap corrector
    @Test public void testMixedCaseDictSwapCorr() throws IOException {
        Reader reader = new FileReader("mixedCaseTest.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            assertEquals("grene -> {green}", makeSet(new String[]{"green"}), 
                    swap.getCorrections("grene"));
            assertEquals("imni -> {mini}", makeSet(new String[]{"mini"}), 
                    swap.getCorrections("imni"));
            //check a different part of the wrong word being swapped
            assertEquals("miin -> {mini}", makeSet(new String[]{"mini"}), 
                    swap.getCorrections("miin"));
        } finally {
            reader.close();
        }
    }

    //nothing close to the word exists in the dictionary
    @Test public void testDoesNotExistSwapCorr() throws IOException {
        Reader reader = new FileReader("mixedCaseTest.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            assertEquals("bloob -> {}", makeSet(new String[]{}), swap.getCorrections("bloob"));
        } finally {
            reader.close();
        }
    }

    //white spaces in the dictionary
    @Test public void testWhiteSpaceDictSwapCorr() throws IOException {
        Reader reader = new FileReader("dictionarytest.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            assertEquals("hmaburger -> {hamburger}", makeSet(new String[]{"hamburger"}), 
                    swap.getCorrections("hmaburger"));
        } finally {
            reader.close();
        }
    }

    //multiple possibilities in the dictionary
    @Test public void testMultipleCorrectionsSwapCorr() throws IOException {
        Reader reader = new FileReader("multipleSwapCorrections.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            assertEquals("solop -> {solpo}", makeSet(new String[]{"sloop", "solpo"}), 
                    swap.getCorrections("solop"));
        } finally {
            reader.close();
        }
    }
}