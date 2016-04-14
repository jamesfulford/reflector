/*

https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
http://www.coderanch.com/t/376318/java/java/calling-abstract-method-abstract-class
https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html
https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=object%20as%20a%20parameter%20in%20java
http://www.tutorialspoint.com/java/java_overriding.htm
http://www.tutorialspoint.com/java/java_string_compareto.htm

 */

/**
 *
 * @author James
 */
abstract class AList {
    
    
    public Object[] array = null; //use toArray() to access Object[]
    //annoyingly, subclasses run into problems because Object[] must be downcast.
    //subclasses never explicitly state that these objects have to be a specific object.
    //that statement is implied, typically in the title of the subclass i.e. StringList, IntegerList.
    public int end = 0; //points at next open spot. Is also number of elements.
    
    
    
                //      INITIATION METHODS
    
    abstract AList createNew();
    
    public AList createNew(Object[] starterKit){
        AList cart = createNew();
        push(starterKit);
        return cart;
    }
    
    public AList(){
        array = null;
        end = 0;
    }
    public AList(Object[] a ){
        array = a;
        end = a.length;
    }
    
    
    
                //      ADDITION METHODS
    
    public void push(Object a){
        if(array == null)
            array = new Object[4];
        
        try {
            array[end] = a;
            end++;
        } catch (ArrayIndexOutOfBoundsException excep){
            grow();
            push(a);
        }
    }
    public void push(Object[] a){
        for(int i = 0; i<a.length; i++){
            push(a[i]);
        }
    }
    //puts object(s) in next available spot(s) (at the end)
    
    public void push(Object a, int index){
        try {
            for(int i = end; i > index; i--) {
                array[i] = array[i - 1];
            } //shift everything over 1 spot
            array[index] = a;
            
        } catch (ArrayIndexOutOfBoundsException excep){
            grow();
            push(a, index);
        }
    }
    public void push(Object[] a, int index){
        try {
            for(int i = end + a.length - 1; i > index + a.length - 1; i--) {
                array[i] = array[i - a.length];
            } //shift everything to the right a.length spots
            //reason this for loop runs backward is so it throws an out of bounds exception
            //before anything really happens.
            
            for(int j = 0; j < a.length; j++ ){
                array[index + j] = a[j];
            } //fill in "empty" spots with entry parts
            
        } catch (ArrayIndexOutOfBoundsException excep){
            grow();
            push(a, index);
        }
    }
    //puts object(s) into specified index (and after, if multiple)
    
    
    
                //      REMOVING METHODS
    
    public Object pop(){
        end--;
        if(end+1 < array.length/2) 
            shrinkIfCan(); 
        
        return array[end];
    }
    public Object pop(int index){
        Object temp = array[index];
        
        end--;
        for(int i = index; i < end; i++){
            array[i] = array[i + 1];
        } //shift everybody over
        
        shrinkIfCan();  
        
        
        return temp;
    }
    //returns value stored at specified index. Deletes the value.
    
    
                //      MEMEORY ALLOCATION METHODS
    
    private void grow(){
        Object[] temp = new Object[2*array.length];
        for(int i = 0; i < array.length; i++){
            temp[i] = array[i];
        }
        array = temp;
    }
    //doubles the space in the array. New spaces are blank
    private boolean shrinkIfCan(){
        if(end + 1 < array.length/2){ //if we can shrink without losing data (plus a little wiggle space), we shrink.
            
            Object[] temp = new Object[array.length/2];
            for(int i = 0; i < temp.length; i++){
                temp[i] = array[i];
            }
            array = temp;
            return true;
            
        } else {
            return false;
        }
    }
    //If no data will be lost, halves the array and returns true. Otherwise returns false.
    public void clear(){
        end = 0;
    }
    
    
                //      SORTING METHODS
    
    abstract boolean beats(Object a, Object b); 
    //returns true if a is stronger than b. (a > b, a succeeds/comes after b, etc.)
    //returns false if a is equal to or weaker than b. (a <= b, a is b | a preceeds/comes before b, etc.)
    
    public void bubbleSort(boolean weakestToStrongest){
        for(int i = end - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(weakestToStrongest) { //sort weakest to strongest=
                    if( beats(array[j], array[j+1]) ) {
                        Object temp = array[j];
                        array[j] = array[j+1];
                        array[j+1] = temp;
                    } //checks if swap
                    
                } else { //sort strongest to weakest
                    
                    if( !beats(array[j], array[j+1]) ) {
                        Object temp = array[j];
                        array[j] = array[j+1];
                        array[j+1] = temp;
                    } //checks if swap
                    
                }
            }
        }
    }
    //sorts via bubbling data.
    //weakestToStrongest means alphabetical or reverse alphabetical,
    //numerical or reverse numerical
    
    public void frequencySort(boolean weakestToStrongest){
        AList hashtable = this.uniqueize(); //table for hashing through
        int[] frequencies = new int[ hashtable.end ]; //sets up frequency table
        
        for(Object thing : toArray()){
            frequencies[ hashtable.findPutIn(thing) ]++;
        } //tallys up how many times each object shows up in the list     
        
        int max = 0;
        for(int i = 0; i < frequencies.length; i++)
            if(frequencies[i] > max) 
                max = frequencies[i];
        //finding the maximum frequency
        
        clear(); //empties this AList. Now, we can refill.
        
        
                    //      CAUTION!!!
                    //      THAR BE DRAGONS BEYOND THIS POINT IN THE METHOD!!!
                    //
                    //
                    //
        
        if(weakestToStrongest){
            
            //sort from least frequent to most frequent
            for(int i = 1; i < max; i++){ //from the least frequent to the most (frequency i)
                for(int j = 0; j < frequencies.length; j++){ 
                    if(frequencies[j] == i){ //find those entries with frequency i (at index j)
                        for(int l = 0; l < i; l++)
                            push( hashtable.lookUp(j) ); //and add them to the list i times.
                    }
                }
            }
            
            
            
        } else {
            
            //sort from most frequent to least frequent
            for(int i = max; i > 0; i--){ //from the most frequent to the least (frequency i)
                for(int j = 0; j < frequencies.length; j++){ 
                    if(frequencies[j] == i){ //find those entries with frequency i (at index j)
                        for(int l = 0; l < frequencies[j]; l++)
                            push( hashtable.lookUp(j) ); //and add them to the list i times.
                    }
                }
            }
            
            
            
        }
        
        
        
        
                
        
    }
    
    //bin sort is implemented in StringList
    //because it otherwise doesn't make sense -
    //requires all objects to have predefineable buckets.
    
                //      USER INTERFACING AND DATATYPE CONVERSIONS
    
    abstract void printElement(Object a); 
    //specifies how to print this object.
    public void print(){
        for(int i = 0; i < end; i++){
            printElement(array[i]);
        }
    }
    //prints all the objects in the list.
    
    private Object[] toArray(){
        Object[] temp = new Object[end];
        for(int i = 0; i < temp.length; i++)
            temp[i] = array[i];
        return temp;    
    }
    //returns a well-shaped array
    
    
    
                //      HASHMAPPING
    
    public int findPutIn(Object a){
        for(int i = 0; i < end; i++){
            if(array[i].equals(a))
                return i;
        }
        push(a);
        return end;
    }
    public int[] findPutIn(Object[] a){
        int[] cart = new int[a.length];
        for(int i = 0; i < a.length; i++){
            cart[i] = findPutIn(a[i]);
        }
        return cart;
    }
    //returns int value(s) of a location(s). Adds location if need be.
    
    public Object lookUp(int v){
        return array[v];
    }
    public Object[] lookUp(int[] v){
        Object[] cart = new Object[v.length];
        for(int i = 0; i < v.length; i++){
            cart[i] = lookUp(v[i]);
        }
        return cart;
    }
    //returns object(s) at index(es) v
    
    public AList uniqueize(){
        AList uniques = createNew();
        uniques.findPutIn( this.toArray() ); //this is a bit subtle...
        // if Object in this.array IS NOT in uniques, it is added to uniques.
        // if Object in this.array IS in uniques, nothing happens.
        // does the above for all Objects in this.array.
        
        return uniques;
    }
    //returns a new AList filled with unique elements (flatlines the frequency)
    //ideal for making hashtables
    
    public int howFrequent(Object queryObject){
        int frequency = 0;
        for(Object thing : array){
            if(thing.equals(queryObject)) 
                frequency++;
        }
        return frequency;
    }
    //returns how frequent a given object appears in this list.
    public int[] frequencies(){
        AList hashtable = this.uniqueize(); //table for hashing through
        int[] frequencies = new int[ hashtable.end ]; //sets up frequency table
        
        for(Object thing : toArray()){
            frequencies[ hashtable.findPutIn(thing) ]++;
        } //tallys up how many times each object shows up in the list 
        
        return frequencies;
    }
    //returns frequency table of how frequent each element in the hashtable is.
    //indexes line up with the hashtable.
    
    public IntegerList hashToIntList(AList hashmap){
        IntegerList hashbrown = new IntegerList();
        for( Object element : toArray() ){
            hashbrown.push( hashmap.findPutIn( element ) );
        }
        return hashbrown;
    }
    
}
