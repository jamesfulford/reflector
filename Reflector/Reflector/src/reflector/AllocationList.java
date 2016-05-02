
package reflector;

import java.io.File;
import java.io.FileNotFoundException;
import listsandsorts.AList;


/**

 @author James Fulford
 @lastEdit 5/1/16
 @jing http://screencast.com/t/cp2Yxbjs
 */
public class AllocationList extends listsandsorts.AList {

    /**
     Satisfies Abstract requirement

     @return new AllocationList
     */
    @Override
    public AList createNew() {
        return new AllocationList();
    }

    /**

     @param a Allocation that wins
     @param b Allocation that loses

     @return whether a beats b.
     Tie means false.
     */
    @Override
    public boolean beats( Object a, Object b ) {
        return ( ( Allocation ) a ).beats(( Allocation ) b);
    }

    /**
     Prints the name of the allocation.
     Fulfills Abstract requirement

     @param a Allocation to print
     */
    @Override
    public void printElement( Object a ) {
        Allocation theObject = ( Allocation ) a;
        System.out.println(theObject.name);
    }

    /**

     @return array of Allocations in this list
     */
    public Allocation[] toArray() {
        Allocation[] temp = new Allocation[end];
        for( int i = 0; i < temp.length; i++ ) {
            temp[i] = ( Allocation ) array[i];
        }
        return temp;
    }

    /**
     Does nothing for now.
     Cannot pull from or store in a file with an AllocationList
     Fulfills (oddly enough) Abstract requirement

     @param phile File to extract

     @throws FileNotFoundException
     */
    @Override
    public void extract( File phile ) throws FileNotFoundException {
    }

    /**

     @param index

     @return allocation at given index
     */
    public Allocation lookUpAllocation( int index ) {
        return ( Allocation ) lookUp(index);
    }
}
