package listsandsorts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

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
 * @author James Fulford
 * @lastEdit 5/1/2016
 * @jing http://screencast.com/t/5V63ZUdYzjfY
 */
public abstract class AList {
    
    
    public Object[] array = null; //use toArray() to access Object[]
    //annoyingly, subclasses run into problems because Object[] must be downcast.
    //subclasses never explicitly state that these objects have to be a specific object.
    //that statement is implied, typically in the title of the subclass i.e. StringList, IntegerList.
    
    public int end = 0; //points at next open spot. Is also number of elements.
    
    
    
                //      INITIATION METHODS

    /**
     * Because AList cannot be constructed (but would be nice to do so), required for all extensions to have this abstract method.
     * @return new AList
     */
    public abstract AList createNew();
    
    /**
     * Constructs empty AList. null array by default
     */
    public AList(){
        array = null;
        end = 0;
    }

    /**
     * Creates new AList based around object[] a
     * @param a object array
     */
    public AList(Object[] a ){
        array = a;
        end = a.length;
    }
    
    /**
     *
     * @return an identical, but separate, AList object.
     */
    public AList copy(){
        AList cart = createNew();
        push ( toArray() );
        return cart;
    }
    
    /**
     * Pulls data out of File in correct format. Appends to list.
     * Does not clear the list before doing so.
     * @param phile File to extract from
     * @throws FileNotFoundException
     */
    public abstract void extract(File phile) throws FileNotFoundException;
    
                //      ADDITION METHODS

    /**
     * Adds object a to top of this list.
     * @param a Object to add to top
     */
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

    /**
     * pushes all elements of a, from first to last. (Last will be at end of list)
     * @param a Object[] of objects to put to top of list
     */
    public void push(Object[] a){
        for (Object a1 : a) {
            push(a1);
        }
    }
    
    /**
     *
     * @param a Object to add to list
     * @param index where to add a in the list.
     */
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

    /**
     *
     * @param a objects to add to list
     * @param index where to add objects in the list
     */
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
    
    
    
                //      REMOVING METHODS

    /**
     * removes last object in list. Returns object.
     * @return bottom object in list
     */
    public Object pop(){
        end--;
        if(end+1 < array.length/2) 
            shrinkIfCan(); 
        
        return array[end];
    }

    /**
     * Removes object at given index. Returns object.
     * @param index
     * @return object at given index in this list
     */
    public Object pop(int index){
        Object temp = array[index];
        
        end--;
        for(int i = index; i < end; i++){
            array[i] = array[i + 1];
        } //shift everybody over
        
        shrinkIfCan();  
        
        
        return temp;
    }
    
    
                //      MEMEORY ALLOCATION METHODS
    
    private void grow(){
        Object[] temp = new Object[2*array.length];
        System.arraycopy(array, 0, temp, 0, array.length); //cool
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

    /**
     * Clears list.
     */
    public void clear(){
        end = 0;
    }
    
    
                //      SORTING METHODS

    /**
     * returns true if a is stronger than b. (a > b, a succeeds/comes after b, etc.)
       returns false if a is equal to or weaker than b. (a <= b, a is b | a preceeds/comes before b, etc.)
    
     * @param a
     * @param b
     * @return a > b (tie is false)
     */
    public abstract boolean beats(Object a, Object b); 
    
    /**
     * Bubble Sorts the list.
     * @param weakestToStrongest whether to sort from weakest to strongest (A-Z, 0-9) 
     * or to sort from strongest to weakest (9-0, Z-A, etc.)
     */
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
    
    /**
     * Sorts data based on how often it occurs in list.
     * @param weakestToStrongest least frequent to most frequent
     * false means most frequent to least frequent
     */
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
    
    //bin sort is implemented in StringList (and referenced by IntegerList)
    //because it otherwise doesn't make sense -
    //requires all objects to have predefineable buckets.
    
                //      USER INTERFACING AND DATATYPE CONVERSIONS

    /**
     * Forces extensions to specify how to print their objects
     * @param a Object to print
     */
    public abstract void printElement(Object a); 
    
    /**
     * Prints contents of this array.
     */
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
    
    /**
     * 
     * @return StringList filled with Stringified elements of this list
     * Through String.valueOf() overloading.
     */
    public StringList toStringList(){
        StringList cart = new StringList();
        
        for(Object element : toArray()){
            if(element.getClass().toString().equalsIgnoreCase("String")){
                push(element);
            } else {
                push ( String.valueOf(element) );
            }
        }
        
        return cart;
    }
    
    
                //      HASHMAPPING

    /**
     * If object a is not in the list, a is added to the list.
     * @param a object to find. 
     * @return integer index of provided object.
     */
    public int findPutIn(Object a){
        for(int i = 0; i < end; i++){
            if(array[i].equals(a))
                return i;
        }
        push(a);
        return end;
    }

    /**
     * Performs findPutIn on every element of array a
     * @param a
     * @return indexes where each object in a pairs with.
     */
    public int[] findPutIn(Object[] a){
        int[] cart = new int[a.length];
        for(int i = 0; i < a.length; i++){
            cart[i] = findPutIn(a[i]);
        }
        return cart;
    }
    
    /**
     *
     * @param v
     * @return object paired to integer v
     */
    public Object lookUp(int v){
        return array[v];
    }

    /**
     *
     * @param v array of integers
     * @return objects paired with each integer in v
     */
    public Object[] lookUp(int[] v){
        Object[] cart = new Object[v.length];
        for(int i = 0; i < v.length; i++){
            cart[i] = lookUp(v[i]);
        }
        return cart;
    }
    //returns object(s) at index(es) v
    
    /**
     * Ideal for making hashtables/maps.
     * @return new AList where every element has only one entry and does not repeat.
     */
    public AList uniqueize(){
        AList uniques = createNew();
        uniques.findPutIn( this.toArray() ); //this is a bit subtle...
        // if Object in this.array IS NOT in uniques, it is added to uniques.
        // if Object in this.array IS in uniques, nothing happens.
        // does the above for all Objects in this.array.
        
        return uniques;
    }
    
    /**
     *
     * @param queryObject
     * @return how frequent queryObject is
     */
    public int howFrequent(Object queryObject){
        int frequency = 0;
        for(Object thing : array){
            if(thing.equals(queryObject)) 
                frequency++;
        }
        return frequency;
    }

    /**
     *
     * @return frequencies of every element corresponding to hashtable (unsorted)
     */
    public int[] frequencies(){
        AList hashtable = this.uniqueize(); //table for hashing through
        int[] frequencies = new int[ hashtable.end ]; //sets up frequency table
        
        for(Object thing : toArray()){
            frequencies[ hashtable.findPutIn(thing) ]++;
        } //tallys up how many times each object shows up in the list 
        
        return frequencies;
    }

                //      IO STORAGE

    /**
     * Writes file into a new txt file
     * @param name of file
     * @throws IOException
     */
    public void writeToTxt(String name) throws IOException{
        String path = "src/resources/" + name + ".txt";
        File phile = new File(path);
        if(phile.exists()){
            
        } else {
            phile.createNewFile();
        }
        PrintWriter wr = new PrintWriter(path);
        
            
        for(Object element : toArray()){
            if(element.getClass().toString().equalsIgnoreCase("String")){
                wr.print( element );
                wr.println();
            } else {
            wr.print( String.valueOf( element ) );
            wr.println();
            }
            wr.flush();
        }
    }
}
