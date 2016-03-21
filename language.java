/**
 *
 * @author James
 */
public class language {
    StringList words; //a language is composed of words.
    

//will make a sorting and unique identifying method. Eventually.
    
                //      LANGUAGE INITIATION
    
                //      LANGUAGE EDITING AND EXPANSION
    
                //      STRING LIST TO LANGUAGE
                        //UNIQUE selections from String List
                        //Sort a String List
    
                //      STRING LISTS
    
    private class StringList{
    String[] map = null;
    int top = 0; //points to the next available spot.
    //this is implemented as an array based list.
    
    public StringList init(int size){
        StringList temp = new StringList();
        temp.map = new String[size];
        temp.top = 0;
        return temp;
    }
    
    public int giveLocation(String s){
        for(int i=0; i<top; i++){
            if(map[i].equals(s))
                return i;
        }
        add(s);
        return top;
    }
     //returns int value of s' location. Adds location if need be.
    
    public String lookUpGiveString(int v){
        return map[v];
    }
    
    private void grow(StringList l){ //doubles the size of the String array "map".
        StringList temp = init(2*l.map.length);
        for(int i=0; i<l.map.length; i++)
            temp.map[i] = l.map[i];
        l = temp;      
    }
    
    public void add(String s){ //adds string s to the list. Grows if need be.
        if(top >= map.length){
            grow(this);
        }
        map[top] = s;
        top++;
    }
    
    }
     //handles a list of strings. 
    private StringList initStringList(int size){
        StringList temp = new StringList();
        temp.map = new String[size];
        temp.top = 0;
        return temp;
    } 
    //create a StringList of size capacity
    
    
                //      CHAR STACKS
   
    private class CharStack{
        char[] stack = new char[40];
        int top = 0; //points to next available position
        
        public CharStack init(){
            return new CharStack();
        }
        
        public void push(char a){
            if(top>=stack.length){
              System.out.println("Stack overflow! Did not push character " + a);  
            } else {
            stack[top] = a;
            top++;
            }
        } //if there is room in the stack, adds character onto the stack.
        
        public char pop(){
            if(top > 0){
                    return stack[--top];
            } else {
                    return (char) 0;
            }
        } //if there is something, returns highest character in stack. Else, returns (char) 0 (null).
        
        public String flush(){
            String result = "";
            boolean notNull = true;
            while(top > 0)
                result = pop() + result; //tack on the stack entries to the front. Puts things in the right order.
            return result;     
        } //returns and empties the entire stack. Puts the characters in the inverse order, top being last.
        
        public void funnel(String word){
            if(word.length() == 0){
                //do nothing. This is here for logical inversion.
            } else {
                char[] arrayWord = word.toCharArray();
                for(int i = 0; i < arrayWord.length; i++)
                    push(arrayWord[i]);  
            }
        } //pushes the entire string into the stak. Does nothing if the string has no length.
        
    }
    
    public CharStack initCharStack(){
        return new CharStack();
    }
    
    
                //      PARSING METHODS
    
    
    private StringList discriminate(String s){
        StringList words = initStringList(100);
        
        char[] array = s.toCharArray();
        int d = 0;
        
        CharStack workStack = initCharStack();
        
        while(d < array.length){
            if(endOfWord(array[d])){
               words.add( workStack.flush() );
            }
            
            workStack.push(cast(array[d]));
            d++;
        }
        return words;
    }
    //returns a StringList of every word in the given String.
    
    private char cast(char a){
       int v = (int) a;
       if( v == 13 ) return (char) 182;
       if( v < 32 ) return (char) 172;
       if( v == 32) return '_';
       if( v > 32 && v <=126 ) return a;
       
       return (char) 175;
    }
    //makes the invisible characters visible.
    
    private boolean endOfWord(char a){
        if((int) a < 127 && (int) a > 32){
            if(a == '"' || a == '.' || a == ',' || a == '?' || a == '!') return true;
        
            return false;
        }
        return true;  
    }
    
    
    
}
