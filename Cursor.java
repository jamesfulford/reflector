public class Cursor {
	int[] cursor = {0, 0}; //x and y values of the absolute position of the cursor
        
        
        
                //      CURSOR INITIATION
        
	public static Cursor at(int x, int y){
		Cursor temp = new Cursor();
		temp.cursor[0]=x;
		temp.cursor[1]=y;
		return temp;
	}
	
        
        
                //      CURSOR MOVEMENT
        
	public boolean inBounds (deflector dish){
		if(cursor[0] >= dish.width + dish.x || cursor[0] < dish.x) return false;
		if(cursor[1] >= dish.height + dish.y || cursor[1] < dish.y) return false;		
		return true;
	}
	//checks if the cursor is inside the bounds of the deflector
	
	public void right(){
		cursor[0]++;
	}
	//moves the cursor to the right
	
	public void left(){
		cursor[0]--;
	}
	//moves the cursor to the left
	
	public void up(){
		cursor[1]--;
	}
	//moves the cursor up
	
	public void down(){
		cursor[1]++;
	}
	//moves the cursor down
	
	public boolean progress(deflector dish){
		right();
		if(cursor[0]>=dish.width+dish.x){
			cursor[0]=dish.x; //avoids the border
			down();
			if(cursor[1]>=dish.height+dish.y){
				cursor[1]=dish.y; //set back to beginning.
				return false;
			}
		}
		return true;		
	}
	//Moves cursor right. If out of space on line, moves to start of next line. (doesn't respect the border)
	//If next line is out of bounds, moves to start and returns false. (doesn't respect the border)
	//Else, returns true.
	
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
	
	public boolean equivalent(Cursor compare){
		if(this.cursor[0]==compare.cursor[0] && this.cursor[1]==compare.cursor[1]) return true;
		
		return false;
	}
        
        
        
                    //      CURSOR TUPLE
        
        private class CursorTuple{
            Cursor[] tuple;
            
            public CursorTuple initCursorTuple(int n){
                CursorTuple temp = new CursorTuple();
                temp.tuple = new Cursor[n];
                return temp;
            }
        } //multiple cursors bound together. (A pair of cursors to show start and end, for example.)
        
        
        
                    //      CURSOR MAP
        
        public class CursorMap{
            CursorTuple[] map = null; //first position is start, last position is end.
            int top = 0; //points to the next available spot.
            //this is implemented as an array based list.
    
            public CursorMap initCursorMap(int size){
              CursorMap temp = new CursorMap();
              temp.map = new CursorTuple[size];
              temp.top = 0;
              return temp;
            }
            //returns a list of cursors with capacity "size."
            
            //public int giveLocation(Cursor c){
            //    for(int i=0; i<top; i++){
            //        if(map[i].equals(c))
            //           return i;
            //    }
            //    add(c);
            //    return top;
            //}
            //returns int value of s' location. Adds location if need be.
            //useful for unique entry listings.
    
            public CursorTuple lookUpCursorPair(int v){
                return map[v];
            }
    
            private void grow(CursorMap l){ //doubles the size of the String array "map".
                CursorMap temp = initCursorMap(2*l.map.length);
                    for(int i=0; i<l.map.length; i++)
                        temp.map[i] = l.map[i];
                    l = temp;      
            }
    //doubles the capacity of the list
            public void add(CursorTuple c){ //adds string s to the list. Grows if need be.
                if(top >= map.length){
                    grow(this);
                }
                map[top] = c;
                top++;
            }
    //adds pair of cursors to the list.
    }
	
        
        
} //Cursor data structure is used to hold the position on a deflector.
