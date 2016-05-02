
package reflector;

import listsandsorts.StringList;


/**

 @author James Fulford
 @lastEdit 5/1/16
 @jing http://screencast.com/t/isbc5PFNhEb
 */
public class PoemPartition extends Partition {

    /**
     Constructs a Partition designed for displaying poems.

     @param spire designated memory Pylon.
     */
    public PoemPartition( Pylon spire ) {
        super(spire);
        String[] Allnames = {"Title", "Poem"};
        names = Allnames;

        Cursor[] AllstartingPoints = {Cursor.at(1, 1), Cursor.at(1, 4)};
        startingPoints = AllstartingPoints;

        Cursor[] AllendingPoints = {Cursor.at(119, 2), Cursor.at(119, 23)};
        endingPoints = AllendingPoints;

        char[] Allsymbols = {'^', '|'};
        symbol = Allsymbols;

        master = new Deflector(121, 25, 0, 0);
        masterSymbol = '*';

        master.border(masterSymbol);
        this.spire = spire;
        size = 2;
        assembleDeflectors();
    }

    /**
     Displays poem from Pylon memory.

     @param index in philes list which you would like to read.
     */
    @Override
    public void loadFileFromPylon( int index ) {
        Allocation entry = spire.philes.lookUpAllocation(index);
        String phileContents = entry.read();

        StringList lookUpTitle = new StringList();
        lookUpTitle.push(entry.name);

        StringList[] display = {lookUpTitle, StringList.parseByLine(phileContents)};
        displayFeed(display);
    }



}
