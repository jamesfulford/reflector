import java.util.Random;

/**
 *
 * @author James Fulford
 * @dateEdit 4/8/16
 */
public class IntegerList extends AList { //all like counter-cascad-y stuffs. 
    //Call it on a subclass and it isn't caught, it flows down river to the superclass and runs there.

    //why does netbeans want this thing here?
    public IntegerList createNew() {
        IntegerList cart = new IntegerList();
        return cart;
    }
    
    public void load(Object[] a){
        array = a;
    }
    
    @Override
    public boolean beats(Object a, Object b) {
        return ( (int) a > (int) b ); //OMG casting is so useful!!! 
        //Took me 10 minutes, then it just came to mind. Wow. That is so cool.
    }

    @Override
    void printElement(Object a) {
        System.out.println( (int) a );
    }
    
    
    public int[] toArray(){
        int[] temp = new int[end];
        for(int i = 0; i < end; i++){
            temp[i] = (Integer) array[i];
        }
        return temp;
    }
    
    public int max(){
        int[] theArray = toArray();
        int max = 0;
        for(int i = 0; i < theArray.length; i++)
            if(theArray[i] > max) 
                max = theArray[i];
        return max;
    }
    //returns the value of the largest int value in the list.
    
    public void bucketSort(boolean weakestToStrongest){
        
                    //      FIRST, we will sort the numbers using StringList's bucketSort method.
                    
        StringList theSorter = new StringList();
        for(int element : toArray()){
            theSorter.push( String.valueOf( element ) );
        }
        theSorter.bucketSort(weakestToStrongest); //sort the numbers as Strings
        String[] sortedNumbers = theSorter.toArray(); //array of the sorted numbers as Strings
        
                    //      SECOND, we will import the results.
        clear(); //make room for the import
        for(String element : sortedNumbers){
            push( Integer.valueOf(element) );
        }
    }
    
    public Object[] hashFromIntList(AList hashtable){
        return hashtable.lookUp( toArray() );
    }
    
    public void generateRandomIntegers(int quantity){
        clear();
        Random rnd = new Random();
        for(int i = 0; i < quantity; i++)
            push( rnd.nextInt() );
    }
    
}
