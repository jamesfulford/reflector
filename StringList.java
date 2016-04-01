/**
 *
 * @author James
 */

public class StringList{
    String[] map;
    int top = 0; //points to the next available spot.
    
    
                //      INITIATION
    
    public StringList(int size){
        map = new String[size];
        top = 0;
    }
    
    public StringList(String[] s){
        map  = s;
        top = s.length;
    }
    
    public static StringList init(int size){
        StringList temp = new StringList(size);
        return temp;
    }
    
    
    
                //      HASHMAPPING
    
    public int find(String s){
        for(int i=0; i<top; i++){
            if(map[i].equals(s))
                return i;
        }
        add(s);
        return top;
    }
     //returns int value of s' location. Adds location if need be.
    
    public String lookUp(int v){
        return map[v];
    }
    
    
    
                //      STANDARD LIST OPERATIONS
    
    private void grow(StringList l){ //doubles the size of the String array "map".
        StringList temp = new StringList(2*l.map.length);
        for(int i=0; i<l.top; i++)
            temp.map[i] = l.map[i];
        temp.top = l.top;
        l = temp;      
    }
    
    public void add(String s){ //adds string s to the list. Grows if need be.
        if(top >= map.length - 2){
            grow(this); //this should now be bigger!
//            System.out.println("StringList has grown!");
        }
        map[top] = s;
        top++;
    }
    
    public void remove(int v){
        top--;
        for(int i = v; i < top; i++){
            map[i] = map[i+1];
        } //shift all later entries back one.
    }
    //removes entry at index v. Refactors.
    
    public String[] toArray(){
        String[] temp = new String[top];
        for(int i = 0; i<temp.length; i++)
            temp[i] = map[i];
        
        return temp;
    
    
    }
    
    public void print(){
        System.out.println();
        for(int i = 0; i < top; i++)
            System.out.println(map[i]);
    }
}//handles a list of strings. 