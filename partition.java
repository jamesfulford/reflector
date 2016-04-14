
public class partition {
	deflector master;
	char masterSymbol;
	
	int size;
	String [] names;
	Cursor [] startingPoints; 
	Cursor [] endingPoints; 
	deflector [] slaves;
	char[] symbol;
	
	pylon spire;
	
	
	
                    //      CREATION METHODS
					
        private static class partitionSpecs{
            deflector master;
            char masterSymbol;
            
            String [] names;
            Cursor [] startingPoints; 
            Cursor [] endingPoints;
            //do not set up slaves
            char[] symbol;
            
            public partitionSpecs(deflector master, char masterSymbol, String[] nameArray, Cursor[] startingPointArray, Cursor[] endingPointArray, char[] symbolArray) throws Exception{
		if(nameArray.length == startingPointArray.length && startingPointArray.length == endingPointArray.length && endingPointArray.length == symbolArray.length){	
                    this.master = master;
                    this.masterSymbol = masterSymbol;
                    names = nameArray;
                    startingPoints = startingPointArray;
                    endingPoints = endingPointArray;
                    symbol = symbolArray;
                } else {
                    throw new Exception("Bad specifications for partition. Please make sure lengths of arrays line up.");
                }
            }
            //constructs object filled with all this data.
            
            public void populate(partition me){
                me.master = this.master;
                me.masterSymbol = this.masterSymbol;
                
                me.size = this.endingPoints.length;
                me.names = this.names;
                me.startingPoints = this.startingPoints;
                me.endingPoints = this.endingPoints;
                //do not set up slaves
                me.symbol = this.symbol;
                
            }
	}
        
        public partition(partitionSpecs specifications, pylon spire) {
            specifications.populate(this);
            this.spire = spire;
            this.assembleDeflectors();
    }
	//constructs a partition with partitionCount different slave/interior boxes.
	
        private void assembleDeflectors(){
			for(int i=0; i<this.size; i++){
				int width = endingPoints[i].cursor[0]-startingPoints[i].cursor[0] + 1;
				int height = endingPoints[i].cursor[1]-startingPoints[i].cursor[1] + 1;
				this.slaves[i] = deflector.initialize(width, height, startingPoints[i].cursor[0], startingPoints[i].cursor[1]);
                                this.slaves[i].border(this.symbol[i]);
			}
	}
	//takes all the starting and ending points in pylon p and puts together the slave deflectors in the slave deflector array.
        //also throws on the appropriate borders to the slave deflectors.
	
        
                    //      MANAGEMENT METHODS
	
	public boolean validity(){
		if(size!=names.length || size!=startingPoints.length || size!=endingPoints.length || size!=slaves.length || size!=symbol.length){
			System.out.println("Error in Partition validity: parameter sizes for partition are not equal.");
			return false;
		}	
		return true;
	}
	//Makes sure partition is well-formed. DOES NOT CHECK FOR OVERLAPPING SLAVES.
	
	public void compile(){
		if(this.validity()){
			this.master.compile(slaves);
		}
	}
	//If valid, compiles all slave deflectors into master deflector.
        
        
        
                    //      PRINTING AND EDITING METHODS
        
	public void print(){
		this.compile(); //impresses slaves onto master dish
		this.master.print(); //prints master dish
	}
	//Compiles. Prints master deflector.
        
	public void paint(){
		if(this.validity()){
			master.fill(masterSymbol);
			for(int i=0; i<size; i++){
				slaves[i].fill(symbol[i]);
			}
		}
	}
	//fills master with masterSymbol, fills each slave with their symbol.
	
        
	
                    //      PRESET PARTITIONS
	
	public static partition constructDefault(pylon spire){
		String[] names = 		{"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"           };
		Cursor[] startingPoints = 	{Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)        };
		Cursor[] endingPoints = 	{Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)	};
		char[] symbols = 		{'/', 			'$',			'>',			'<',			'?',			'+',			'#'			};
		deflector master = deflector.initialize(80, 25, 0, 0);
                char masterSymbol = '*';
                
                master.border(masterSymbol);
                
                try {
                    partitionSpecs temp = new partitionSpecs(master, masterSymbol, names, startingPoints, endingPoints, symbols);
                    return new partition(temp, spire);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
		
		
		return null;
	}
	//returns a default partition.
        
        //<editor-fold defaultstate="collapsed" desc="resetDefault method is no longer supported.">
        /*
        public void resetDefault(){
        String[] names = 		{"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"           };
        Cursor[] startingPoints = 	{Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)        };
        Cursor[] endingPoints = 	{Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)	};
        char[] symbols = 		{'/', 			'$',			'>',			'<',			'?',			'+',			'#'			};
        deflector master = deflector.initialize(80, 25, 0, 0);
        this.assemblePartition(master, '*', names, startingPoints, endingPoints, symbols);
        }
        //overwrites existing deflector with a blank default deflector. */
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="castToDefault Method - it's a mess and is no longer supported.">
        /*
        public void castToDefault(){
        
        //		INITIAL VALUES FOR DEFAULT PARTITION
        String[] names = 		{"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"       };
        Cursor[] startingPoints = 	{Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)    };
        Cursor[] endingPoints = 	{Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)   };
        char[] symbols = 		{'/', 			'$',			'>',			'<',			'?',			'+',			'#'                 };
        
        deflector master = deflector.initialize(80, 25, 0, 0);
        
        //		CREATING, DATA-TRANSFER, AND REASSIGNMENT OF SLAVES
        for(int i = 0; i<slaves.length || i<this.slaves.length; i++){
        int width = endingPoints[i].cursor[0]-startingPoints[i].cursor[0] + 1;
        int height = endingPoints[i].cursor[1]-startingPoints[i].cursor[1] + 1;
        deflector partitionDeflector = deflector.initialize(width, height, startingPoints[i].cursor[0], startingPoints[i].cursor[1]);
        
        this.slaves[i].resizeInto(partitionDeflector); //send data in old partition deflector to new partition deflector
        this.slaves[i] = partitionDeflector; //relieve old deflector as slave.
        }//end for loop
        
        //		ASSIGNMENT OF INITIAL VALUES TO PARTITION
        //This step is necessary because initializing array brackets are not permitted when reassigning values.
        this.master = master;
        this.masterSymbol = '*';
        this.names = names;
        this.startingPoints = startingPoints;
        this.endingPoints = endingPoints;
        this.symbol = symbols;
        }
        //casts an existing partition to a filled default partition. */
//</editor-fold>
	

                    //      PYLON INTEGRATION
	
	
        
        //need a load-into method to take memory and put it into a slave.
        
        
        
        
        
        
        
        
        
}//end partition class
