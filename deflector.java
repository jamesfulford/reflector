public class deflector {
	public int x;
	public int y;
	public int height; //number of rows
	public int width; //number of columns
	public char[][] array = new char[width][height]; //(x, y) format for arrays
//	public deflector parent = null; //the deflector being written on top of, if applicable.
        language lang = null; //the language the deflector is encrypted in, if applicable.
        
	
        
				//		AESTHETICS METHODS
	
	public void print(){
		for(int i=0; i<this.height; i++){
			for(int j=0; j<this.width; j++)
				System.out.print(this.array[j][i]);
			System.out.println("");
		}
		
	}
	//prints out the entire deflector.
	public void border(char a){
		Cursor cursor = Cursor.at(x, y); //local cursor for doing the borders is built
		while(cursor.inBounds(this)){
                        this.write(cursor, a);
                        cursor.right();
		}
		cursor.left();
		while(cursor.inBounds(this)){
			this.write(cursor, a);
			cursor.down();
		}
		cursor.up();
		while(cursor.inBounds(this)){
			this.write(cursor, a);
			cursor.left();
		}
		cursor.right();
		while(cursor.inBounds(this)){
			this.write(cursor, a);
			cursor.up();
		}
                cursor.down();
                this.array[x][y]=a; //for some reason, the top left corner as not been working. This is my fix.
	}
	//lines the borders of a deflector with a character.
	
	
        
				//		INITIALIZE	
	
	public static deflector initialize(int width, int height, int x, int y){
		deflector dish = new deflector();
		dish.x = x;
		dish.y = y;
		dish.height = height;
		dish.width = width;	
		dish.array = new char[width][height]; //just making sure that changes actually occur.
		
		for(int i=0; i<dish.array.length; i++)
			for(int j=0; j<dish.array[i].length; j++)
				dish.array[i][j] = ' '; //making all the spots start as spaces.
		
		return dish;
	}
	//initializes a deflector with height, width, and position.
        
        
        
                                //              DEFLECTOR INTERACTIONS
        
	public static boolean bounce(deflector dish, deflector main){
		//We want to find out if "dish" is entirely inside of "main".
		if(main.x>dish.x) return false; //fails if the left side of main is right of dish.
		if(main.x+main.width-1 < dish.x+dish.width-1) return false; //fails if right side of main is left of dish
		if(main.y > dish.y)return false; //fails if top of main is below dish
		if(main.y+main.height-1 < dish.y+dish.height-1) return false; //fails if bottom of main is above dish
		return true; //succeeds if doesn't fail.
	}
	//Answers the question: is the first deflector inside the second deflector?	
	public void impressTo(deflector dish){
            Cursor ptr = Cursor.at(this.x - 1, this.y);
            while(ptr.progress(this)){
                if(ptr.inBounds(dish)){
                    dish.write(ptr, this.read(ptr));
                }
            }
	}
	//if given deflector has a parent, all information is sent downstream from deflector to it's parent deflector. It then forces the parent to do the same.
	public void compile(deflector[] dishes){
		for(int i = 0; i<dishes.length; i++)
			dishes[i].impressTo(this);
	}
	//sends data in deflector arrays into this deflector.
	public void uplift(deflector[] dishes){
		for(int i = 0; i<dishes.length; i++)
			this.impressTo(dishes[i]);
	}
	//sends data in this array into appropriate spots in deflector array.
	public deflector copy(){
            deflector copied = initialize(this.width, this.height, this.x, this.y);
            this.impressTo(copied);
            return copied;
        }
        //due to the desire to prevent confusion with pointers, this method will return a replica of this deflector.
        
        
        
				//		EDITING METHODS
	
	public void write(Cursor cursor, char a){
		this.array[cursor.cursor[0]-this.x][cursor.cursor[1]-this.y] = a;
                if(a!=' '){
                    this.array[0][0] = 1;
                }
	}
	//overwrites the cursor position's data on the dish with character a.
	public void write(Cursor cursor, String s){
		char[] array = s.toCharArray();
		for(int i=0; i<array.length; i++){
			this.write(cursor, array[i]);
			if(cursor.progress(this)==false) break;
		}
	}
	//writes a string into a reflector at the cursor's position. Stops if ran out of room in deflector.
	public void fill(char a){
		Cursor cursor = Cursor.at(this.x, this.y);
		while(cursor.progress(this))
                    this.write(cursor, a);
                this.array[0][0] = '1';
	}
        //fills all entries of this deflector with character a
	public void clear(){
		this.fill(' ');
                this.array[0][0] = '0';
        }
	
	public void resizeInto(deflector target){
		Cursor arg = Cursor.at(this.x - 1, this.y);
		Cursor tar = Cursor.at(target.x - 1, target.y);
		while(arg.progress(this) && tar.progress(target)){
			target.write(tar, this.read(arg));
		}
		
	}
	//takes argument deflector's data and puts it into target deflector. DOES NOT RESPECT BORDERS. Stops when target is full or data is exhausted.
	public static void resizeIntoBorders(deflector argument, deflector target){
		Cursor arg = Cursor.at(0, 0);
		Cursor tar = Cursor.at(0, 0);
		boolean allWell = true;
		while(allWell){
			target.write(tar, argument.read(arg));
			allWell = arg.typewrite(argument) && tar.typewrite(target);
		}
	}
	//takes argument deflector's data and puts it into target deflector. Respects borders. Stops when target is full or data is exhausted.
	
	
        
				//		READING METHODS
	
	public char read(Cursor cursor){
		return(this.array[cursor.cursor[0]-this.x][cursor.cursor[1]-this.y]);
	}
	//prints out the character at the cursor's position
	
	
        
				//		SEARCHING METHODS
	
	public boolean isEmpty(){
            Cursor ptr = Cursor.at(this.x+1, this.y);
            while(ptr.progress(this)){
                if(this.read(ptr) != ' ' && this.read(ptr) != (char)0) return false;
            } 
            return true;
        }
        //goes through deflector, returns false when it finds a non-blank entry. Otherwise returns true.
        //spaces and null entries are considered empty.
        //skips the first entry due to pylon considerations.
        public Cursor sequentialSearch(char a){
            Cursor head = Cursor.at(x-1, y);
            while(head.progress(this)){
                if(this.read(head)==a){
                    System.out.println("              FOUND IT!!!");
                    return head;
                }
            }
//            System.out.println("Character not found");
            return null;
        }
        //returns first result found in a deflector.
        
        
        
        

}