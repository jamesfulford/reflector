package reflector;

import listsandsorts.StringList;
import commons.AskFor;
import commons.DIAG;

/*
@author James Fulford
@lastEdit 5/1/16
@jing http://screencast.com/t/isbc5PFNhEb
*/
abstract class Partition {

    public Deflector master;
    public char masterSymbol;

    public int size;
    public String[] names;
    public Cursor[] startingPoints;
    public Cursor[] endingPoints;
    public Deflector[] slaves;
    public char[] symbol;

    public Pylon spire;

    //      CREATION METHODS
    protected Partition(Pylon spire) {
        this.spire = spire;
    }
    //constructs a Partition with partitionCount different slave/interior boxes.

    protected void assembleDeflectors() {
        this.slaves = new Deflector[this.size];
        for (int i = 0; i < this.size; i++) {
            int width = endingPoints[i].cursor[0] - startingPoints[i].cursor[0] + 1;
            int height = endingPoints[i].cursor[1] - startingPoints[i].cursor[1] + 1;

            int x = startingPoints[i].cursor[0];
            int y = startingPoints[i].cursor[1];

            this.slaves[i] = new Deflector(width, height, x, y);
            this.slaves[i].border(this.symbol[i]);
        }
    }
    //takes all the starting and ending points in Pylon p and puts together the slave deflectors in the slave Deflector array.
    //also throws on the appropriate borders to the slave deflectors.

    //      MANAGEMENT METHODS
    protected void compile() {
        this.master.compile(slaves);
    }
    //Compiles all slave deflectors into master Deflector.

    public boolean save(String title, StringList content) {
        return spire.addAllocation(title, content);
    }

    

    //      PRINTING AND EDITING METHODS
    protected void displayFeed(StringList[] words) {
        try {
            int lesserNumber = Math.min(size, words.length);
            String[] content = new String[lesserNumber];
            for (int i = 0; i < lesserNumber; i++) {
                content[i] = words[i].toString();
            }
            

            boolean allFinished = false;
            int[] bookmark = new int[lesserNumber];
            for (int i = 0; i < lesserNumber; i++) {
                bookmark[i] = 0;
            }

            while (!allFinished) {
                allFinished = true;
                for (int i = 0; i < lesserNumber; i++) { //for each slave
                    
                    if (bookmark[i] < content[i].length()) { //if there is more to print
                        slaves[i].fill(' '); //clear the slave
                        
                        bookmark[i] = slaves[i].feed(content[i], bookmark[i]); //feed it more content
                        
                        if (bookmark[i] < content[i].length()) { //if there is still more to do, 
                            allFinished = false; //say false.
                        }
                        
                    }
                }
                this.print();
                if (!allFinished) {
                    AskFor.readKey();
                }
            }
        } catch (NullPointerException e) {

        }

    }
    
    abstract void loadFileFromPylon(int index);

    public void print() {
        this.compile(); //impresses slaves onto master dish
        this.master.print(); //prints master dish
    }
    //Compiles. Prints master Deflector.

    public void paint() {
        master.fill(masterSymbol);
        for (int i = 0; i < size; i++) {
            slaves[i].fill(symbol[i]);
        }
    }
    //fills master with masterSymbol, fills each slave with their symbol.

}//end Partition class
