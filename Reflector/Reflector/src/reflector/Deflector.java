package reflector;

/*

@author James Fulford
@lastEdit 5/2/16
@jing http://screencast.com/t/IBZkYZDo
*/
public class Deflector {

    public int x; //top left corner
    public int y; //top left corner
    public int height; //number of rows
    public int width; //number of columns
    public char[][] array = new char[width][height]; //(x, y) format for arrays
//	public Deflector parent = null; //the Deflector being written on top of, if applicable.

    //		AESTHETICS METHODS
    /**
     * prints out this deflector.
     */
    public void print() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.array[j][i]);
            }
            System.out.println("");
        }

    }

    /**
     * lines the borders of a Deflector with a character.
     *
     * @param a - the character to line with.
     */
    public void border(char a) {
        Cursor.border(a, this);
    }

    //		INITIALIZATION METHODS	
    /**
     * constructs a new Deflector with height, width, and position.
     *
     * @param setWidth
     * @param setHeight
     * @param setX
     * @param setY
     */
    public Deflector(int setWidth, int setHeight, int setX, int setY) {
        x = setX;
        y = setY;
        height = setHeight;
        width = setWidth;
        array = new char[width][height]; //just making sure that changes actually occur.

        fill(' ');
    }

    //              DEFLECTOR INTERACTIONS
    /**
     * this Deflector's data steams into appropriate positions in dish.
     *
     * @param dish to compile to.
     */
    public void impressTo(Deflector dish) {
        Cursor ptr = Cursor.at(this.x - 1, this.y);
        while (ptr.progress(this)) {
            if (ptr.inBounds(dish)) {
                dish.write(ptr, this.read(ptr));
            }
        }
    }

    /**
     * impressesTo data in Deflector[] into this Deflector. Deflectors later in
     * the array overwrite earlier deflectors.
     *
     * @param dishes
     */
    public void compile(Deflector[] dishes) {
        for (Deflector dishe : dishes) {
            dishe.impressTo(this);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="uplift method. Unused, but good idea.">
    /*
        public void uplift(Deflector[] dishes){
        for (Deflector dishe : dishes) {
        this.impressTo(dishe);
        }
        }
        //sends data in this array into appropriate spots in Deflector array.
     */
//</editor-fold>
    /**
     * Returns a new object, replica of this deflector. Good for staying sane
     * when working with pointers.
     *
     * @return copy of target deflector
     */
    public Deflector copy() {
        Deflector copied = new Deflector(this.width, this.height, this.x, this.y);
        this.impressTo(copied);
        return copied;
    }

    //		EDITING METHODS
    /**
     * overwrites the cursor position's data on the dish with character a.
     *
     * @param cursor
     * @param a
     */
    public void write(Cursor cursor, char a) {
        char[] arrayToWriteTo = this.array[cursor.cursor[0] - this.x];
        arrayToWriteTo[cursor.cursor[1] - this.y] = a;
    }

    /**
     * fills all entries of this Deflector with character a
     *
     * @param a
     */
    public void fill(char a) {
        Cursor cursor = Cursor.at(this.x, this.y);
        cursor.cursor[0] = cursor.cursor[0] - 1;
        while (cursor.progress(this)) {
            this.write(cursor, a);
        }
    }

    /**
     * fills all entries of this Deflector with spaces ' '
     */
    public void clear() {
        this.fill(' ');
        this.array[0][0] = '0';
    }

//<editor-fold defaultstate="collapsed" desc="resizeInto methods. Made obsolete by impressTo and advances in Cursor manipulation.">
    /*
    public void resizeInto(Deflector target) {
    Cursor arg = Cursor.at(this.x - 1, this.y);
    Cursor tar = Cursor.at(target.x - 1, target.y);
    while (arg.progress(this) && tar.progress(target)) {
    target.write(tar, this.read(arg));
    }
    
    }
    //takes argument Deflector's data and puts it into target Deflector. DOES NOT RESPECT BORDERS. Stops when target is full or data is exhausted.
    
    public static void resizeIntoBorders(Deflector argument, Deflector target) {
    Cursor arg = Cursor.at(0, 0);
    Cursor tar = Cursor.at(0, 0);
    boolean allWell = true;
    while (allWell) {
    target.write(tar, argument.read(arg));
    allWell = arg.typewrite(argument) && tar.typewrite(target);
    }
    }
    //takes argument Deflector's data and puts it into target Deflector. Respects borders. Stops when target is full or data is exhausted.
     */
//</editor-fold>
    /**
     * reads string from indexToPutInNext'th index onward, writes into Deflector. Returns
     * where it left off in the string.
     *
     * @param s
     * @param indexToPutInNext
     * @return integer index where it left off in the string.
     */
    public int feed(String s, int indexToPutInNext) {
        Cursor cursor = Cursor.at(x, y);
        
        char[] charArray = s.toCharArray();
        int result = indexToPutInNext;

        for (; result < charArray.length; result++) {
            if (!cursor.inBounds(this)) { //stop if out of bounds
                break;
            }
            
            if (charArray[result] == (char) 182) { //if pilcrow symbol,
                cursor.skipLine(this);
                continue;
            }
            
            //if not pilcrow symbol
            this.write(cursor, charArray[result]);
            cursor.progress(this);
            
        }



//        while(cursor.progress(this)){
//            result++;
//            if(charArray.end > 0){
//                char data = String.valueOf(charArray.pop(0)).charAt(0);
//                if(data == (char) 182){
//                    cursor.skipLine(this);
//                } else {
//                    this.write(cursor, data);
//                }
//            } else {
//                break;
//            }
//        }
        

        
        return result;
    }

    //		READING METHODS
    public char read(Cursor cursor) {
        return (this.array[cursor.cursor[0] - this.x][cursor.cursor[1] - this.y]);
    }
    //prints out the character at the cursor's position

    //		SEARCHING METHODS
    /**
     * goes through Deflector, returns false when it finds a non-blank entry.
     * Otherwise returns true. spaces and null entries are considered empty.
     * skips the first entry due to index considerations.
     *
     * @return TRUE if it is empty.
     */
    public boolean isEmpty() {
        Cursor ptr = Cursor.at(this.x + 1, this.y);
        while (ptr.progress(this)) {
            if (this.read(ptr) != ' ' && this.read(ptr) != (char) 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param a
     * @return first result found in a Deflector.
     */
    public Cursor sequentialSearch(char a) {
        Cursor head = Cursor.at(x - 1, y);
        while (head.progress(this)) {
            if (this.read(head) == a) {
                System.out.println("              FOUND IT!!!");
                return head;
            }
        }
//            System.out.println("Character not found");
        return null;
    }

}
