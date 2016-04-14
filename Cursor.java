public class Cursor {
	int[] cursor = {0, 0}; //x and y values of the absolute position of the cursor
        
        int level;
        
        
        
        
                            //      PYLON INTEGRATED METHODS
        
        pylon home;
        
                //      CURSOR INITIATION
        
	public Cursor(int x, int y, int level, pylon home){
            cursor[0] = x;
            cursor[1] = y;
            this.level = level;
            this.home = home;
        }
        //constructs cursor with specified input
        
        public Cursor copy(){
            return new Cursor(this.cursor[0], this.cursor[1], this.level, this.home);
        }
	//gives cursor with same specifications.
        
        
        
                //      CURSOR POSITIONING
        
        public boolean inBounds(){
            return inBounds( home.buffer[level] ) && level >= 0 && level < home.buffer.length;
        }
	//checks if the cursor is inside the bounds of the current deflector and pylon.
	
        public boolean atTopLeft(){
            if( thisLevel().y == cursor[1] && thisLevel().x == cursor[0]) return true;
            return false;
        }
        //is this cursor at the top left corner of the current deflector?
        
        public deflector thisLevel(){
            return home.buffer[level];
        }
        //what deflector is this cursor at?
        
        public void setAtStart(){
            deflector thislevel = thisLevel();
            cursor[0] = thislevel.x;
            cursor[1] = thislevel.y;
            if( atTopLeft() ) 
                next(); //avoid the top left corner
        }
        //puts cursor at the start of a deflector.
        
        public void setAtEnd(){
            deflector thislevel = thisLevel();
            cursor[0] = thislevel.x + thislevel.width - 1;
            cursor[1] = thislevel.y + thislevel.height - 1;
            if( atTopLeft() ) 
                next(); //avoid the top left corner - should not be a problem if deflector is not 1x1
        }
        //puts cursor at the end of a deflector.
        
        
        
                //      CURSOR TRAVERSING
        
        private void descend(){
            level = (level - 1)%home.buffer.length;
        }
        //bring cursor down a level (wrap around in pylon)
        
        private void ascend(){
            level = (level + 1)%home.buffer.length;
        }
        //bring cursor up a level (wrap around in pylon)
	
        public void next(){
            progress( thisLevel() );
        }
        //moves the cursor to the next spot in pylon.
        
        public void back(){
            back ( thisLevel() );  
        }
        //move sthe cursor back a spot in pylon.
        
        
                //      CURSOR COMPARISON
        
        
	public boolean sameSpotAndLevel(Cursor compare){
		if(this.cursor[0]==compare.cursor[0] && this.cursor[1]==compare.cursor[1] && this.level == compare.level) return true;
		
		return false;
	}
        //returns whether two cursors are at the sameSpot position and level.
        
        public boolean beats(Cursor latter){
            if(this.level < latter.level){
                return false;
            } //if on lower level, automatically out.
            if(this.cursor[1] < latter.cursor[1]){
                return false;
            } //if on earlier line, automatically out.
            if(this.cursor[0] <= latter.cursor[0]){
                return false;
            } //if before or at sameSpot spot, automatically out. (Tie is not a beat)
            
            return true; //every other option is a win.
        }
        //returns whether one cursor is before or after another cursor. Does not account for wrap around in the pylon.
        
        public boolean between(Cursor start, Cursor end){
            if(start.beats(end)){
                return this.beats(start) && !this.beats(end);
            } else if(end.beats(start)){
                return this.beats(end) && !this.beats(start);
            } else {
                return false;
            }
        }
        //returns whether a cursor is between two other cursors. Order of parameters doesn't matter.
        //returns false if two parameters "tie" - don't beat each other.
        
        public int distanceBetween(Cursor latter){
            if(!this.beats(latter)){ //this cursor comes before latter.
                int sum = -1;
                Cursor runner = this.copy();
                
                do {
                    sum++; //this one is clear,
                    runner.next(); //so let's move to the next spot.
                } while (!runner.sameSpot(latter));
                return sum;
                
            } else if(this.beats(latter)){
                int sum = -1;
                Cursor runner = latter.copy();
                
                do {
                    sum++; //this one is clear
                    runner.next(); //next spot.
                } while (!runner.sameSpot(this));
                return sum;
                
            } else {
                return 0; //the two cursors point to the same position.
            }
        }
        //determines how many empty spaces there are between this and latter (order not concern, non-negative answer)
        
        
                //      DEFLECTOR DATA INTERACTIONS
        
        public void write(char a){
            thisLevel().write(this, a);
        }
        //writes a into this spot.
        
        public char read(){
            return (thisLevel().read(this));
        }
        //returns character at this spot.
        
        
        
        
        
        
        
        
        
        
        
        

                            //          PYLON IGNORANCE METHODS ****************
        
                //      CURSOR INITIATION
        
        public static Cursor at(int x, int y){
            return new Cursor(x, y, 0, null);
        }
        //returns a cursor with specified x and y positons.
                     
        
        
                //      CURSOR POSITIONING
        
	public boolean inBounds (deflector dish){
		if(cursor[0] >= dish.width + dish.x || cursor[0] < dish.x) return false;
		if(cursor[1] >= dish.height + dish.y || cursor[1] < dish.y) return false;		
		return true;
	}
        //checks if cursor is inside the bounds of specified deflector. No regard to pylon or level.
        
        public void setAtStart (deflector dish){
            cursor[0] = dish.x;
            cursor[1] = dish.y;
            progress(dish); //avoid the top left corner
        }
        //puts cursor at the beginning of dish. (avoids index position.)
        
        
        
                //      CURSOR TRAVERSING
        
	private void right(){
		cursor[0]++;
	}
	//moves the cursor to the right
	private void left(){
		cursor[0]--;
	}
	//moves the cursor to the left
	private void up(){
		cursor[1]--;
	}
	//moves the cursor up
	private void down(){
		cursor[1]++;
	}
	//moves the cursor down
        
        
	public boolean progress(deflector dish){
		right();
		if(cursor[0]>=dish.width+dish.x){
			cursor[0]=dish.x; //avoids the border
			down();
			if(cursor[1]>=dish.height+dish.y){
                                
                                setAtStart();
				return false;
			}
		}
		return true;		
	}
	//Moves cursor right. If out of space on line, moves to start of next line. (doesn't respect the border)
	//If next line is out of bounds, moves to start and returns false. (doesn't respect the border)
	//Else, returns true.
        
        public boolean back(deflector dish){
            left();
            if(cursor[0]<dish.x){
                cursor[0]=dish.x; //avoids the border
                up();
                if(cursor[1]<dish.y){
                        cursor[1]=dish.y; //set back to beginning.
                        descend();
                        return false;
                }
            }
            return true;
        }
	
	public boolean typewrite(deflector dish){
		right();
		if(cursor[0]>=dish.width-1){
			cursor[0]=1; //avoids the border
			down();
			if(cursor[1]>=dish.height-1){
				cursor[1]=1; //set back to beginning.
				return false;
			}
		}
		return true;		
	}
	//Moves cursor right. If out of space on line, moves to start of next line. (respects the border)
	//If next line is out of bounds, moves to start and returns false. (respects the border)
	//Else, returns true.
        
        
        
                    //      CURSOR COMPARISION
	
	public boolean sameSpot(Cursor compare){
		if(this.cursor[0]==compare.cursor[0] && this.cursor[1]==compare.cursor[1]) return true;
		
		return false;
	}
        //returns whether two cursors are at the sameSpot position.
        
        
        
                    //      DEFLECTOR DATA INTERACTIONS
        
        public void write(char a, deflector dish){
            dish.write(this, a);
        }
        //writes a into dish at this spot.
        
        public char read(deflector dish){
            return (dish.read(this));
        }
        //returns character at this spot.
        
        public static void border(char a, deflector dish){
		Cursor cursor = Cursor.at(dish.x, dish.y); //local cursor for doing the borders is built
		while(cursor.inBounds(dish)){
                        cursor.write(a, dish);
                        cursor.right();
		}
		cursor.left();
		while(cursor.inBounds(dish)){
			cursor.write(a, dish);
			cursor.down();
		}
		cursor.up();
		while(cursor.inBounds(dish)){
			cursor.write(a, dish);
			cursor.left();
		}
		cursor.right();
		while(cursor.inBounds(dish)){
			cursor.write(a, dish);
			cursor.up();
		}
                cursor.setAtStart(dish); //skips over index spot.
                cursor.left(); //undoes the skipping.
                cursor.write(a, dish); //for some reason, the top left corner as not been working. This is my fix.
	}
        
} //Cursor data structure is used to hold the position in a pylon or deflector.
