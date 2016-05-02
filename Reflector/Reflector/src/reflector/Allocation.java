package reflector;


import listsandsorts.StringList;


/**
 @author James Fulford
 @lastEdit 5/1/16
 @jing http://screencast.com/t/cp2Yxbjs

 */
public class Allocation {

    String name;
    Cursor start;
    Cursor end;
    StringList hashmap;
    Pylon home;

    /**

     @param latter Allocation

     @return whether this allocation comes before the latter one in the Pylon
     */
    public boolean beats( Allocation latter ) {
        return ( this.end ).beats(latter.start);
    }

    /**
     Constructs an Allocation. Allocates and distributes to memory in starter's pylon

     @param starter      cursor where to start Allocation of memory
     @param contentWords StringList of words to write (encoded)
     @param name         String name of allocation
     */
    public Allocation( Cursor starter, StringList contentWords, String name ) {
        this.name = name;
        this.home = starter.home;

        //PLACE THE START
        start = starter.copy();
        if( start.atTopLeft() ) {
            start.next(); //if starter is in the top left spot, move along one spot.
        }

        //FIND THE END
        end = starter.copy();
        for( int i = 0; i < contentWords.end; i++ ) {
            end.next();
        }

        //CREATE HASHMAP
        hashmap = new StringList(); //make the hashmap from the content
        hashmap.push(contentWords.toArray());
        hashmap.uniqueize();
        hashmap.frequencySort(false); //most frequent words are easier to find in hashmap.

        //ADD TO PYLON's ALLOCATIONLIST
        home.philes.push(this);

        //WRITE INTO PYLON
        Cursor typist = start.copy();
        for( String word : contentWords.toArray() ) {
            typist.write(( char ) hashmap.findPutIn(word)); //possible lossy conversion from int to char... :(
            typist.next();
        } //write into array
    }

    /**

     @return string of all data stored in the Pylon
     */
    public String read() {
        Cursor indexFinger = this.start.copy();
        String s = String.valueOf(indexFinger.read());

        while( !this.end.sameSpotAndLevel(indexFinger) ) {
            s = s + deHashFromChar(indexFinger.read());
            indexFinger.next();
        }

        return s;
    }

    /**

     @param a character to be hashed

     @return the string corresponding to the ASCII integer value of a. Uses hashmap of this Allocation.
     */
    public String deHashFromChar( char a ) {
        return ( String ) hashmap.lookUp(( int ) a);
    }

    /**

     @param a String to look for

     @return cursor of where in encoded Pylon this string can be found.
     returns null if there is no string that contains parameter a.
     */
    public Cursor sequentialSearch( String a ) {
        char key;
        boolean haveKey = false;
        for( String word : hashmap.toArray() ) {
            if( word.contains(a) ) {
                key = ( char ) hashmap.findPutIn(word);

                Cursor query = start.copy();
                while( !query.sameSpotAndLevel(end) ) {
                    if( query.read() == key ) {
                        return query;
                    }
                    query.next();
                }//end while
            }//end if
        }//end for
        return null;
    }
}
