package reflector;


import listsandsorts.StringList;

//James Fulford
//Memory area

/**

 @author James Fulford
 @lastEdit 5/1/16
 @jing http://screencast.com/t/nNf5rw0umo6
 */

public class Pylon {

    int size;
    Deflector[] buffer;     //maybe make this an AList one day.
    AllocationList philes = new AllocationList();

    //      INITIALIZAITON

    /**
     Constructs a Pylon with given size

     @param size
     */
    public Pylon( int size ) {
        buffer = new Deflector[size];

        for( int i = 0; i < size; i++ ) {
            buffer[i] = new Deflector(80, 25, 0, 0);
        }
        this.size = size;
        philes = new AllocationList();
    }

    /**
     Fills this Pylon with copies of base.

     @param base
     */
    public void fillAllWithSimilar( Deflector base ) {
        for( int i = 0; i < size; i++ ) {
            this.buffer[i] = base.copy();
        }
        this.cleanUpIndexes();
    }


    //      INDEX MANAGEMENT
    private void dogEar( Deflector dish ) {
        if( dish.isEmpty() ) {
            dish.array[0][0] = 0;
        } else {
            dish.array[0][0] = 1;
        }
    }

    /**
     Checks the indexes of all the deflectors in the buffer. Corrects them
     if required.
     */
    public void cleanUpIndexes() {
        for( int i = 0; i < size; i++ ) {
            if( buffer[i].isEmpty() ) {
            } else {
                dogEar(buffer[i]);
            }
        }
    }


    //      SEARCHING METHODS

    /**
     Searches through levels of pylon for exact character. Returns cursor at index i if
     ith level of pylon contains a at some spot.

     @param a

     @return
     */
    public Cursor[] sequentialSearch( char a ) {
        Cursor[] results = new Cursor[size];
        for( int i = 0; i != size; i++ ) {
            results[i] = buffer[i].sequentialSearch(a);
        }
        return results;
    }
    //sequential search through all of the Pylon. Under 40 average through 100 levels.

    /**
     Searches through levels of pylon for exact character. Returns cursor at index i if
     ith level of pylon contains a at some spot.
     Takes advantage of indexes.

     @param a

     @return
     */
    public Cursor[] indexSearch( char a ) {
        Cursor[] results = new Cursor[size];
        for( int i = 0; i < buffer.length; i++ ) {
            if( buffer[i].array[0][0] == 1 ) {
                results[i] = buffer[i].sequentialSearch(a);
            } else {
                results[i] = null;
            }
        }
        return results;
    }
    //sequential search, exclude sequential searching deflectors that are marked empty. 0ms at best, at worst under 40ms (average).

    /**
     Searches for words that contain string s inside the pylon by looking in
     every allocation. Works around hashtables.

     @param s (Search key)

     @return Cursor of first instance of searched s.
     */
    public Cursor allocationSearch( String s ) {
        for( Allocation phile : philes.toArray() ) {
            try {
                return phile.sequentialSearch(s);
            } catch ( Exception e ) {
                //probably a null pointer exception. Happens when allocation didn't have anything.
            }
        }
        return null;
    }

    //      MEMORY ALLOCATION

    /**
     Stores content in the Pylon (encoded by word). Adds allocation to Pylon's philes.

     @param title
     @param content

     @return whether it found a spot or not
     */
    public boolean addAllocation( String title, String content ) {
        return addAllocation(title, StringList.parseByWord(content));
    }

    /**
     Stores wordsOfContent in the Pylon (encoded by word). Adds an Allocation to Pylon's philes.

     @param title          (what should it be named?)
     @param wordsOfContent (what should be saved in the Pylon? In words)

     @return whether it found a spot or not
     */
    public boolean addAllocation( String title, StringList wordsOfContent ) {

        //SETTING UP THE ALLOCATION
        StringList contentWords = new StringList();
        contentWords.push(wordsOfContent.toArray());
        int wordsLength = contentWords.end;

        //WHERE TO START?
        boolean foundASpot = false;
        Cursor starter = new Cursor(0, 0, 0, this);
        if( philes.end == 0 ) {
            starter.next();
            foundASpot = true;
        } else {
            Allocation[] philesArray = philes.toArray();
            for( int i = 0; i < philesArray.length; i++ ) {
                if( ( philesArray[i].end ).distanceBetween(philesArray[( i + 1 ) % philesArray.length].start) <= wordsLength ) { //if there is enough room in this gap
                    foundASpot = true;
                    starter = philesArray[i].end.copy();
                    starter.next();
                    break;
                }
            }
        }

        //ALLOCATION OBJECT
        Allocation theAllocation = new Allocation(starter, contentWords, title); //All the fun stuff happens in here.

        return foundASpot;
    }

}
