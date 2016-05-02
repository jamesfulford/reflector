package listsandsorts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author James Fulford
 * @dateEdit 5/1/2016
 * @jing http://screencast.com/t/zkWDGjcA5
 */
public class StringList extends AList { //all like counter-cascad-y stuffs. 
    //Call it on a subclass and it isn't caught, it flows down river to the superclass and runs there.

    @Override
    public StringList createNew() {
        StringList cart = new StringList();
        return cart;
    }

    @Override
    public boolean beats(Object a, Object b) {
        return ((String) a).compareTo((String) b) < 0;
        //compareTo is negative if 
        //a comes before b.
    }

    @Override
    public void printElement(Object a) {
        System.out.println((String) a);
    }

    @Override
    public void extract(File extractionPoint) throws FileNotFoundException {
        extractWords(extractionPoint);
    }

    /**
     * Fills this list with each line from this file. Does not pilcrow each line. Does not clear list.
     *
     * @param extractionPoint file to extract lines from
     * @throws FileNotFoundException
     */
    public void extractLines(File extractionPoint) throws FileNotFoundException {
        Scanner type = new Scanner(extractionPoint);
        while (type.hasNextLine()) {
            push(type.nextLine() + String.valueOf((char)182)); //collect the string from the file. Include line breaks.
        }
        type.close();
    }

    /**
     * Extracts words from file into this list. Does pilcrow. Does not clear list.
     *
     * @param extractionPoint file to extract from
     * @throws FileNotFoundException
     */
    public void extractWords(File extractionPoint) throws FileNotFoundException {
        Scanner type = new Scanner(extractionPoint);
        String s = "";
        while (type.hasNextLine()) {
            s = s + type.nextLine() + String.valueOf((char) 182); //collect the string from the file. Include line breaks.
        }
        type.close();
        push(parseByWord(s).toArray());
    }

    //      CONVERSIONS
    /**
     *
     * @return String[] of list contents. Nondynamic.
     */
    public String[] toArray() {
        String[] temp = new String[end];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = (String) array[i];
        }
        return temp;
    }

    /**
     * Useful for extracting from a file using StringList, then converting to IntegerList. Should be obsolete due to Abstract requirement for extraction
     * methods.
     *
     * @return IntegerList using Integer.getInteger(String) method for conversion.
     */
    public IntegerList toIntegerList() {
        IntegerList cart = new IntegerList();
        for (String element : toArray()) {
            push(Integer.getInteger(element));
        }

        return cart;
    }

    @Override
    public String toString() {
        String result = "";

        for (String word : toArray()) {
            result = result + word;
        }
        return result;
    }

    @Override
    public String lookUp(int v) {
        return (String) this.array[v];
    }

    //      PARSING
    /**
     * Static method that returns list of words in s.
     *
     * @param s String to parse and add to return list
     * @return List of all words in provided String. Separates words based on endOfWord method. Delimiters are stored as their own words. I.E. "Sam! Sam." are
     * stored as "Sam", "!", " ", "Sam", "."
     */
    public static StringList parseByWord(String s) {
        StringList words = new StringList();

        char[] array = s.toCharArray();
        int d = 0;

        CharList workStack = new CharList();

        while (d < array.length) {
            if (endOfWord(array[d])) {
                words.push(workStack.flush());
                words.push(String.valueOf(array[d]));
                d++;
            } else {
                workStack.push(array[d]);
                d++;
            }

        }
        return words;
    }
    //parses a string, returns StringList of all words (not unique).

    /**
     *
     * @param s
     * @return List of strings, each being a line or a pilcrow. Lines are delimited by pilcrows.
     */
    public static StringList parseByLine(String s) {
        StringList words = new StringList();

        char[] array = s.toCharArray();
        int d = 0;

        CharList workStack = new CharList();

        while (d < array.length) {
            if (endOfLine(array[d])) {
                words.push(workStack.flush());
                words.push(String.valueOf(array[d]));
                d++;
            } else {
                workStack.push(array[d]);
                d++;
            }

        }
        return words;
    }

    private static boolean endOfWord(char a) {
        if ((int) a < 127 && (int) a > 32) {
            if (a == '"' || a == '.' || a == ',' || a == '?' || a == '!') {
                return true;
            }

            return false;
        }
        return true;
    }
    //returns true if the character indicates the end of a word.

    private static boolean endOfLine(char a) {
        return a == (char) 182;
    }

    private static char cast(char a) {
        int v = (int) a;
        if (v == 13) {
            return (char) 182;
        }
        if (v <= 32) {
            return (char) '_';
        }
//       if( v == 32) return '_';
        if (v > 32 && v <= 126) {
            return a;
        }

        return '_';
    }
    //returns a more visible character for invisible characters. Otherwise returns input.

    /**
     * Removes all elements in List that contain one of the provided delimiters.
     *
     * @param delimiters if a string contains one of these elements, is removed. Useful for parsing commands
     */
    public void strip(String[] delimiters) {
        for (String word : toArray()) {
            boolean pop = false;
            if (word.length() == 0) {
                pop = true;
            } else {
                for (String delimiter : delimiters) {
                    if (word.contains(delimiter)) {
                        pop = true;
                        break;
                    }
                }
            }

            if (pop) {
                pop(findPutIn(word));
            }
        }
    }

    //      SORTING METHODS
    //<editor-fold defaultstate="collapsed" desc="The old binSort method. It's evil. It's aweful.">
    /*
    public static StringList binSort(StringList list, boolean weakestToStrongest){
    String[] theList = list.toArray();
    String[] binNames = {
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
    "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    }; //could let it generate its own bin names...
    String endOfWord = "@atTheEnd";
    StringList[] bins = new StringList[binNames.length + 1]; //26 letters, 10 numbers, 1 for all the others
    StringList finalResult = new StringList(); //this is what we return in the end.
    //done initialize the sorting
    
    //put things into bins.
    for(String element : theList){
    boolean allotted = false;
    if(element.length() == 0){
    finalResult.push(endOfWord);
    break;
    } //if this element is at the end of its word, then leave an endOfWord marker.
    
    //don't add anything if element is endOfWord marker.
    
    
    for(int i = 0; i< binNames.length-1; i++){
    if(element.startsWith(binNames[i].toLowerCase())){
    bins[i].push(element.substring(1).toLowerCase());
    allotted = true;
    break;
    }
    } //goes through all but the last bin. If element belongs, puts the rest of the word in the bin.
    if(allotted == false && element.compareToIgnoreCase(endOfWord) != 0){
    bins[bins.length-1].push( element.substring(1) );
    } //if element has no bin, it is put in last bin.
    }
    //all elements in the list to sort are put in their bins.
    
    
    
    
    //now to sort the sub bins filled with substrings.
    //add those strings to the final list in the correct order.
    if(weakestToStrongest){
    
    for(int i = 0; i < bins.length; i++){
    finalResult.push(  concat(binNames[i], binSort( bins[i], weakestToStrongest ).toArray() ) );
    //woah! Slow down, son!
    //binSort(bins[i], weakestToStrongest).toArray()        sorts the smaller bins and makes it an array. Puts in requested order.
    // concat(binNames[i], binSort...       is how to account for the front of the strings being cut off
    //finalResult.push                  adds the resulting String[] to the result.
    }
    
    } else {
    finalResult.push( binSort( bins[bins.length-1], weakestToStrongest).toArray() );
    
    for(int i = bins.length - 2; i >= 0; i--){
    if(bins[i].end > 0)
    finalResult.push(  concat(binNames[i], binSort( bins[i], weakestToStrongest ).toArray() ) );
    //woah! Slow down, son!
    //binSort(bins[i], weakestToStrongest).toArray()        sorts the smaller bins and makes it an array. Puts in requested order.
    // concat(binNames[i], binSort...       is how to account for the front of the strings being cut off
    //finalResult.push                  adds the resulting String[] to the result.
    }
    
    }
    finalResult.demarkize(endOfWord);
    return finalResult;
    
    }
    //sorts recursively based upon what a string's first character is.
     */
//</editor-fold>
    /**
     * Public face for bucket sorting your list
     *
     * @param weakestToStrongest false is alphabetical order
     */
    public void bucketSort(boolean weakestToStrongest) {
        bucketSort(0, weakestToStrongest);
    }

    private void bucketSort(int level, boolean weakestToStrongest) {
        //      ACCOUNTING FOR REDUNDANT WORDS
        StringList hashtable = (StringList) this.uniqueize(); //table for hashing through
        int[] frequencies = this.frequencies();

        //      MAKING THE BUCKETS
        StringList binNames = new StringList();
        for (String word : hashtable.toArray()) {
            if (word.length() > 0) {
                try {
                    binNames.findPutIn(word.substring(level, level + 1)); //put in the letters for each of the bins.
                } catch (Exception e) {
                }
            } else {
                pop(findPutIn(word));                   //take out empty words
            }
        }
        binNames.bubbleSort(weakestToStrongest); //sort the bin names
        StringList[] bins = new StringList[binNames.end]; //allocate the bins, respective to the sorted binNames.
        for (int i = 0; i < bins.length; i++) {
            bins[i] = new StringList();
        } //start them all.

        //      POPULATING THE BUCKETS
        for (String element : hashtable.toArray()) {
            try {
                String key = element.substring(level, level + 1);
                for (int i = 0; i < binNames.end; i++) {
                    if (key.equalsIgnoreCase(binNames.toArray()[i])) {
                        bins[i].push(element);
                    }
                }
            } catch (Exception e) {
                push(element);
            }
        }

        //      SORTING THE BUCKETS
        for (int i = 0; i < bins.length; i++) {
            if (bins[i].end > 1) {
                bins[i].bucketSort(level + 1, weakestToStrongest);
            }
        }

        //      RESTORING BACK TO THE LIST
        this.clear();
        for (int i = 0; i < bins.length; i++) //for each bin
        {
            for (String word : bins[i].toArray()) //for each word in each bin
            {
                for (int f = 0; f < frequencies[hashtable.findPutIn(word)]; f++) //how ever frequent the word is
                {
                    push(word); //add it to the list.
                }
            }
        }
        //      ALL DONE BUCKETSORT
    }
    // recursively sorts strings and stuff.

}
