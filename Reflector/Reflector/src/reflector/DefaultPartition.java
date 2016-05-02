
package reflector;

import listsandsorts.StringList;

/**
 *
 * @author James Fulford
   @lastEdit 5/1/16
   @jing http://screencast.com/t/isbc5PFNhEb
 */
public class DefaultPartition extends Partition {
    static String documentation = "James Fulford   Reflector Project" + String.valueOf((char)182) + "May 1, 2016.";
    String info;
    /**
     * Constructs a preset partition for general use.
     * @param spire designated memory Pylon
     */
    public DefaultPartition(Pylon spire) {
        super(spire);
        String[] allNames =             {"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"           };
        names = allNames;
        
        Cursor[] allStartingPoints =    {Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)        };
        startingPoints = allStartingPoints;
        
        Cursor[] allEndingPoints =      {Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)	};
        endingPoints = allEndingPoints;
        
        char[] allSymbols=              {'/', 		'$',			'>',			'<',			'?',			'+',			'#'			};
        symbol = allSymbols;
        
        
        master = new Deflector(80, 25, 0, 0);
        masterSymbol = '*';

        master.border(masterSymbol);
        this.spire = spire;
        size = allNames.length;
        assembleDeflectors();
    }
    
    /**
     Displays poem from Pylon memory.

     @param index in philes list which you would like to rea
     */
    @Override
    public void loadFileFromPylon(int index){
        Allocation entry = spire.philes.lookUpAllocation(index);
        String phileContents = entry.read();

        StringList lookUpTitle = new StringList();
        lookUpTitle.push(entry.name);
        
        StringList filler = new StringList();
        filler.push(" ");
        
        StringList documentation = new StringList();
        documentation.push( "James Fulford   Reflector Project");
        

        StringList[] display = {documentation, filler, filler, filler, filler, StringList.parseByLine(phileContents), lookUpTitle};
        displayFeed(display);
    }
    
}
