package reflector;

/*
@author James Fulford
@lastEdit 5/1/16
@jing http://screencast.com/t/pwXAKYqbUvgP
//jing does not cover deflector, as stated at start. Apologies for discrepancy.
*/
public class Cursor {
	int[] cursor = {0, 0}; //x and y values of the absolute position of the cursor
        
        int level;
        
        
        
        
                            //      PYLON INTEGRATED METHODS
        
        Pylon home;
        
                //      CURSOR INITIATION

    /**
     * Constructs new Cursor with specified parameters. (Self explanatory)
     * Tends to be used with Memory Allocation methods.
     * @param x - x position
     * @param y - y position
     * @param level - level in pylon
     * @param home - pylon affiliated with
     */
	public Cursor(int x, int y, int level, Pylon home){
            cursor[0] = x;
            cursor[1] = y;
            this.level = level;
            this.home = home;
        }
        
    /**
     *
     * @return a copy of this cursor, but not the same cursor.
     */
    public Cursor copy(){
            return new Cursor(this.cursor[0], this.cursor[1], this.level, this.home);
        }
        
        
        
                //      CURSOR POSITIONING
        
    //<editor-fold defaultstate="collapsed" desc="inBounds method. Pylon-dependent boolean method.">
    /*
    public boolean inBounds(){
    return inBounds( home.buffer[level] ) && level >= 0 && level < home.buffer.length;
    }
    //checks if the cursor is inside the bounds of the current Deflector and Pylon.
    */
//</editor-fold>

    /**
     * is this cursor at the top left corner of the current Deflector?
     * Pylon dependent
     * @return true if at top left corner.
     */
    public boolean atTopLeft(){
        return atTopLeft(thisLevel());
    }

    /**
     *
     * @param dish
     * @return if this is at the top left of dish.
     */
    public boolean atTopLeft(Deflector dish){
        return (dish.y == cursor[1] && dish.x == cursor[0]);
    }

    /**
     *
     * @return the deflector this cursor is pointing in.
     */
    public Deflector thisLevel(){
        return home.buffer[level];
    }

    //<editor-fold defaultstate="collapsed" desc="setAtStart pylon dependent method.">
    /*
    public void setAtStart(){
    Deflector thislevel = thisLevel();
    cursor[0] = thislevel.x;
    cursor[1] = thislevel.y;
    if( atTopLeft() )
    next(); //avoid the top left corner
    }
    //puts cursor at the start of a Deflector.
    */
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setAtEnd pylon dependent method">
    /*
    public void setAtEnd(){
    Deflector thislevel = thisLevel();
    cursor[0] = thislevel.x + thislevel.width - 1;
    cursor[1] = thislevel.y + thislevel.height - 1;
    if( atTopLeft() )
    next(); //avoid the top left corner - should not be a problem if Deflector is not 1x1
    }
    //puts cursor at the end of a Deflector.
    */
//</editor-fold>


            //      CURSOR TRAVERSING

    private void descend(){
        level = (level - 1)%home.buffer.length;
    }
    //bring cursor down a level (wrap around in Pylon)

    private void ascend(){
        level = (level + 1)%home.buffer.length;
    }
    //bring cursor up a level (wrap around in Pylon)

    /**
     * moves the cursor to the next spot in Pylon.
     */
    public void next(){
        if(progress( thisLevel() )){
            //just progress.
        } else {
            ascend(); //move along to next level
            setAtStart(thisLevel()); //reset cursor position in the new level.
        }
    }
    
    /**
     * moves the cursor back a spot in Pylon.
     */
    public void back(){
        back ( thisLevel() );  
    }
    


            //      CURSOR COMPARISON

    /**
     *
     * @param compare cursor to compare to
     * @return whether the two are at the same x, y, and level.
     */
    public boolean sameSpotAndLevel(Cursor compare){
            return this.cursor[0]==compare.cursor[0] && this.cursor[1]==compare.cursor[1] && this.level == compare.level;
    }

    /**
     * returns whether one cursor is before or after another cursor. Does not account for wrap around in the Pylon.
     * @param latter cursor that beats this cursor 
     * @return true if latter is ahead of this cursor. Otherwise, false. (Tie, false)
     */
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
    
    /**
     *
    //returns whether a cursor is between two other cursors. Order of parameters doesn't matter.
    //returns false if two parameters "tie" - don't beat each other.
     * @param start cursor
     * @param end cursor
     * @return whether this is between start and end (order of parameters doesn't matter.)
     */
    public boolean between(Cursor start, Cursor end){
        if(start.beats(end)){
            return this.beats(start) && !this.beats(end);
        } else if(end.beats(start)){
            return this.beats(end) && !this.beats(start);
        } else {
            return false;
        }
    }

    /**
     * determines how many empty spaces there are between this and latter (order not concern, non-negative answer)
     * @param latter
     * @return empty spaces between this and latter. (No wrap around)
     */
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
    

            //      DEFLECTOR DATA INTERACTIONS

    /**
     * Writes a in this spot in Pylon
     * @param a character to write in this spot
     */
    public void write(char a){
        thisLevel().write(this, a);
    }

    /**
     *
     * @return character in this spot in Pylon
     */
    public char read(){
        return (thisLevel().read(this));
    }






                        //          PYLON IGNORANCE METHODS ****************

            //      CURSOR INITIATION

    /**
     * Ignores pylon and level fields, returns a new Cursor.
     * @param x
     * @param y
     * @return a cursor with x and y positions
     */
    public static Cursor at(int x, int y){
        return new Cursor(x, y, 0, null);
    }



            //      CURSOR POSITIONING

    /**
     * checks if cursor is inside the bounds of specified Deflector. No regard to Pylon or level
     * @param dish
     * @return whether this cursor is inside the borders of the dish.
     */
    public boolean inBounds (Deflector dish){
            if(cursor[0] >= dish.width + dish.x || cursor[0] < dish.x) return false;
            if(cursor[1] >= dish.height + dish.y || cursor[1] < dish.y) return false;		
            return true;
    }

    /**
     * Moves cursor to be at the spot after the top left corner in dish.
     * @param dish
     */
    public void setAtStart (Deflector dish){
        cursor[0] = dish.x;
        cursor[1] = dish.y;
        progress(dish); //avoid the top left corner
    }



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

    /**
     * Move cursor down a row and all the way to the left.
     * Richard Garcia appreciates typewriters. This works just like that.
     * @param dish
     */
    public void skipLine(Deflector dish){
        down();
        while(dish.x < this.cursor[0]){
            left();
        }
    }

    /**
     * moves cursor to next spot in dish. Wraps around if need be.
        DOES NOT WRAP BACK TO TOP IF FALLS OUT THE BOTTOM
     * @param dish
     * @return whether this is still inside dish
     */
    public boolean progress(Deflector dish){
        right();
        if(cursor[0]>=dish.width+dish.x){
                cursor[0]=dish.x; //avoids the border
                down();
                //Moves cursor right. If out of space on line, moves to start of next line. (doesn't respect the border)
                if(cursor[1]>=dish.height+dish.y){
                    //If next line is out of bounds, moves to start and returns false. (doesn't respect the border)
                    return false;
                }
        }
            return true; //Else, returns true.		
    }
    
    
    /**
     * Moves the cursor back a spot. Wraps around if need be.
     * @param dish
     * @return whether this cursor is still in dish
     */
    public boolean back(Deflector dish){
        left();
        if(cursor[0]<dish.x){
            cursor[0]=dish.x+dish.width; //avoids the border
            up();
            if(cursor[1]<dish.y){
                    cursor[1]=dish.y+dish.height; //set back to bottom right.
                    return false;
            }
        }
        return true;
    }

    
    
            //      CURSOR COMPARISION

    /**
     * //returns whether two cursors are at the sameSpot position.
     * @param compare cursor to compare
     * @return whether this and compare have same x and y.
     */
    public boolean sameSpot(Cursor compare){
            if(this.cursor[0]==compare.cursor[0] && this.cursor[1]==compare.cursor[1]) return true;

            return false;
    }

    

                //      DEFLECTOR DATA INTERACTIONS

    /**
     * Writes character a into deflector dish at this position
     * @param a character to write
     * @param dish deflector to write to
     */
    public void write(char a, Deflector dish){
        dish.write(this, a);
    }

    /**
     *
     * @param dish
     * @return character at this position
     */
    public char read(Deflector dish){
        return (dish.read(this));
    }

    /**
     * Fills border entries of deflector dish with character a.
     * @param a character to border the deflector with
     * @param dish deflector to border
     */
    public static void border(char a, Deflector dish){
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

} //Cursor data structure is used to hold the position in a Pylon and/or Deflector.
