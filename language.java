
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Mapping between integers and words. (This is a hash map)
 * @author James
 */
public class language {
    StringList words;
    
    
    
                //      INITIATION
    
    public language(File extractionPoint) throws FileNotFoundException{
        Scanner type = new Scanner ( extractionPoint );
        String s = "";
        while(type.hasNextLine()){
            s = s + String.valueOf( (char) 182 ) + type.nextLine();
        }
        StringList temp = listAllWords(s);
        words = temp;
        type.close();
    }
    // constructs a language from a file.
    
    public language(String[] s){
        words.map = s;
    }
    // constructs a language from a given array of Strings.
    
     public void print(){
        System.out.println();
        for (String word : words.map) {
            System.out.println(word);
        }
    }
    // prints all words.
    
     
     
                //      HASHMAPPING
    
    public language hashMap(){
        language hashMap = new language( this.words.toArray() );
        hashMap.uniqueize();
        //hashMap.sort();
        return hashMap;
    } 
    //takes language and returns a hashmapping language with it.
     
     
    public void uniqueize(){
        StringList uniques = new StringList(1000);
        
        for(int i = 0; i < words.top; i++){ //go through all the words
            boolean addToList = true;
            for(int k = 0; k < uniques.top; k++){ //check for same
                
                if( words.map[i].equalsIgnoreCase( uniques.map[k] ) ){
                    addToList=false;
                    break;
                }
            }
            if(addToList) uniques.add(words.map[i]);
        }
        
        words = uniques;
    }
    // makes language's list of Strings have no duplicates.
    
    public String hash(int v){
        return words.lookUp(v);
    }
    // converts from int to String
    public String[] hash(int[] v){
        String[] temp = new String[v.length];
        for(int i = 0; i < v.length; i++)
            temp[i] = hash( v[i] );
        return temp;
    }
    // converts int[] to String[]
    
    public int hash(String s){
        return words.find(s);
    }
    // converts from String to int
    public int[] hash(String[] v){
        int[] temp = new int[v.length];
        for(int i = 0; i < v.length; i++)
            temp[i] = hash( v[i] );
        return temp;
    }
    // converts String[] to int[]
    public int[] hash(StringList sl){
        return hash( sl.toArray() );
    }
    
    
                //      PARSING
    
    public StringList listAllWords(String s){
        StringList words = StringList.init(10000);
        
        char[] array = s.toCharArray();
        int d = 0;
        
        CharStack workStack = CharStack.init();
        
        while(d < array.length){
            if(endOfWord(array[d])){
               words.add( workStack.flush() );
            }
            
            workStack.push(cast(array[d]));
            d++;
            }
        return words;
    }
    //parses a string, returns StringList of all words (not unique).
    
    private boolean endOfWord(char a){
        if((int) a < 127 && (int) a > 32){
            if(a == '"' || a == '.' || a == ',' || a == '?' || a == '!') return true;
        
            return false;
        }
        return true;  
    }
    //returns true if the character indicates the end of a word.

    private char cast(char a){
       int v = (int) a;
       if( v == 13 ) return (char) 182;
       if( v <= 32 ) return (char) '_';
//       if( v == 32) return '_';
       if( v > 32 && v <=126 ) return a;
       
       return '_';
    }
    //returns a more visible character for invisible characters. Otherwise returns input.
}