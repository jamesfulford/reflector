
public class partition {
	deflector master;
	char masterSymbol;
	
	int size = 0;
	String [] names = null;
	Cursor [] startingPoints = null; //top left corners of all boxes
	Cursor [] endingPoints = null; //bottom right corners of all boxes
	deflector [] slaves = null;
	char[] symbol;
	
	pylon pylon = null;
        Cursor.CursorMap controlTower = null;
	
	
	
                    //      CREATION METHODS
					
	private static partition newPartition(int partitionCount){
		partition temp = new partition();
		temp.names = new String[partitionCount];
		temp.startingPoints = new Cursor[partitionCount];
		temp.endingPoints = new Cursor[partitionCount];
		temp.slaves = new deflector[partitionCount];
		temp.size = partitionCount;
		return temp;
	}
	//creates a partition with partitionCount different interior boxes.
	private void assembleDeflectors(){
			for(int i=0; i<this.size; i++){
				int width = endingPoints[i].cursor[0]-startingPoints[i].cursor[0] + 1;
				int height = endingPoints[i].cursor[1]-startingPoints[i].cursor[1] + 1;
				this.slaves[i] = deflector.initialize(width, height, startingPoints[i].cursor[0], startingPoints[i].cursor[1]);
			}
	}
	//takes all the starting and ending points in pylon p and puts together the slave deflectors in the slave deflector array.
	private void assemblePartition(deflector master, char masterSymbol, String[] nameArray, Cursor[] startingPointArray, Cursor[] endingPointArray, char[] symbolArray){
			this.master = master;
			this.masterSymbol = masterSymbol;
			names = nameArray;
			startingPoints = startingPointArray;
			endingPoints = endingPointArray;
			symbol = symbolArray;
			this.assembleDeflectors();
	}
	//puts parameters into this partition. Use after newPartition function.
	
	
        
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
        
        
        
                    //      PRINTING METHODS
        
	public void print(){
		this.compile(); //impresses slaves onto master dish
                
                System.out.print("\033[H\033[2J");
                System.out.flush();
                //Internet claims that this clears the console... if I run from the command line.
                
		this.master.print(); //prints master dish
	}
	//Compiles. Prints master deflector.
        
        
        
                    //      EDITING METHODS
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
	
	public static partition constructDefault(){
		partition temp = newPartition(7);
		String[] names = 		{"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"           };
		Cursor[] startingPoints = 	{Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)        };
		Cursor[] endingPoints = 	{Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)	};
		char[] symbols = 		{'/', 			'$',			'>',			'<',			'?',			'+',			'#'			};
		deflector master = deflector.initialize(80, 25, 0, 0);
		
		temp.assemblePartition(master, '*', names, startingPoints, endingPoints, symbols);
		
		temp.master.border('*');
		
		return temp;
	}
	//returns a default partition.
	public void resetDefault(){
		String[] names = 		{"Documentation",	"Screen Title", 	"Purpose", 		"Dialogue", 		"User Input", 		"Utility Space", 	"Information"           };
		Cursor[] startingPoints = 	{Cursor.at(1, 1),	Cursor.at(1, 3), 	Cursor.at(1, 4),	Cursor.at(1, 7),	Cursor.at(31, 7), 	Cursor.at(61, 7),	Cursor.at(1, 19)        };
		Cursor[] endingPoints = 	{Cursor.at(78, 2),	Cursor.at(78, 3),	Cursor.at(78, 5),	Cursor.at(30, 17),	Cursor.at(60, 17),	Cursor.at(78, 17), 	Cursor.at(78, 23)	};
		char[] symbols = 		{'/', 			'$',			'>',			'<',			'?',			'+',			'#'			};
		deflector master = deflector.initialize(80, 25, 0, 0);
		this.assemblePartition(master, '*', names, startingPoints, endingPoints, symbols);
	}
	//overwrites existing deflector with a blank default deflector.
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
	//casts an existing partition to a filled default partition.
	

                    //      PYLON INTEGRATION
	
	public void push(){
		deflector pushTemp = this.master.copy();
		this.pylon.add(pushTemp);
                //because adding only moves along the pointer, I need to ensure
                //that I only transfer the data. That is why I make a copy.
	} 
	//saves the master deflector into the pylon. Moves the pylon index 1 ahead.
	public void load(int v){
            deflector newDeflector = this.pylon.buffer[v];
            this.master = this.pylon.buffer[v];
	}
	//overwrites the next spot on the pylon with the current master deflector. Typically used after compiling.
        
        
        //need a load-into method to take memory and put it into a slave.
        
}//end partition class
