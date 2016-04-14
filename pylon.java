//James Fulford
//Memory area

public class pylon {
	int size;
	deflector[] buffer;
        int rearGuard = 0;
	int vanguard = 0; //points to the most recent deflector.
        AllocationList philes;
	
        
                //      INITIALIZAITON
        
	public static pylon construct(int size){
            pylon additionalPylon = new pylon();
            additionalPylon.buffer = new deflector[size];
            additionalPylon.size = size;
            return additionalPylon;
	}
	//Constructs a pylon with size size
	
        public void fillAllWithSimilar(deflector base){
            for(int i = 0; i<size; i++){
                this.buffer[i] = base.copy();
            }
            this.cleanUpIndexes();
        }
        // fills this pylon with deflectors with the content of base deflector.
        
        
                //      GUARD MANAGEMENT
        
	private void next(){
            if(this.vanguard==this.rearGuard)
                this.vanguard = (this.vanguard+1)%this.buffer.length;
	}
        private void drag(){
            this.rearGuard = (this.rearGuard + 1)%this.buffer.length;
        }
	//moves the index forward. If out of bounds of array, wraps around to the beginning.
	private boolean gapBetweenGuards(int v){
            for(int i=rearGuard; i!=vanguard; i=(i+1)%size)
                if(i==v) return true;
            return false;
        }
        
        
        
                //      CURSOR MANAGEMENT
        
        private deflector whichLevel(Cursor spot){
            return buffer[ spot.level ];
        }
        
        
                //      INDEX MANAGEMENT
        
        private void dogEar(deflector dish){
            if(dish.isEmpty()){
                dish.array[0][0] = 0;
            } else {
                dish.array[0][0] = 1;
            }
        }
        
        public void cleanUpIndexes(){
            for(int i=0; i<size; i++)
                if(buffer[i].isEmpty()){
                    remove(i);
                } else {
                    dogEar(buffer[i]);
                }
        }
        
        
                //      INTERACTION WITH LIST
        
        public void add(deflector dish){
            this.next();
            this.buffer[vanguard]=dish.copy();
            dogEar(this.buffer[vanguard]);
            if(buffer[rearGuard].array[0][0]==0) rearGuard++;
        }
        
        public deflector read(int v){
            deflector temp = buffer[v].copy();
            temp.array[0][0] = '*';
            return temp;
        }
        
        public void remove(int v){
            if(this.gapBetweenGuards(v))
                for(int i = v; i!=rearGuard; i=(i-1)%size){
                  buffer[i] = buffer[(i-1)%size];
                } 
            drag();
        }
        //overwrites entry by shifting previous pointers. Brings along rear guard.

        
        
                //      SEARCHING METHODS
        
        public Cursor[] sequentialSearch(char a){
            Cursor[] results = new Cursor[size];
            for(int i = 0; i!=size; i++)
                results[i]=buffer[i].sequentialSearch(a);
            return results;
        }
        //sequential search through all of the pylon. Under 40 average through 100 levels.
        public Cursor[] indexSearch(char a){
            Cursor[] results = new Cursor[100];
            for(int i = 0; i < buffer.length; i++)
                if(buffer[i].array[0][0] == 1){
                    results[i]=buffer[i].sequentialSearch(a);
                } else {
                    results[i]=null;
                }
            return results;
        }
        //sequential search, exclude sequential searching deflectors that are marked empty. 0ms at best, at worst under 40ms (average).
	
        
        
                //      MEMORY ALLOCATION
        
        public boolean add(String content){
            Allocation region = philes.whereToAdd(content);
            if(region == null) return false;
            Cursor typist = region.start.copy();
            
            StringList contentWords = null;
            contentWords = contentWords.listAllWords(content); //content is parsed.
            
            for(String word : contentWords.toArray()){
                typist.write( (char) region.hashmap.findPutIn(word) ); //possible lossy conversion from int to char... :(
            }
             return true;
        }
        
        private class Allocation{
            String name;
            Cursor start;
            Cursor end;
            StringList hashmap;
            pylon home;
            
            public boolean beats(Allocation latter){
                return (this.end).beats(latter.start);
            }
            
            public Allocation(Cursor starter, String content){
                name = content.substring(0, 40);
                this.home = starter.home;
                
                start = starter.copy();
                if( start.atTopLeft() ) 
                    start.next(); //if starter is in the top left spot, move along one spot.
                
                hashmap = (StringList) hashmap.listAllWords(content).uniqueize(); //make the hashmap from the content
            }
        }
        
        private class AllocationList extends AList {
            @Override
            AList createNew() {
                return new AllocationList();
            }

            @Override
            boolean beats(Object a, Object b) {
                return ((Allocation) a).beats((Allocation) b);
            }

            @Override
            void printElement(Object a) {
                Allocation theObject = (Allocation) a;
                System.out.println(theObject.name + " start: " + theObject.start.cursor[0] + ", " + theObject.start.cursor[1] + " end: " + theObject.end.cursor[0] + ", " + theObject.end.cursor[1]);
            }

            public Allocation[] toArray(){
                Allocation[] temp = new Allocation[end];
                for(int i = 0; i < temp.length; i++)
                    temp[i] = (Allocation) array[i];
                return temp; 
            }


            public Allocation whereToAdd(String content){
                StringList contentWords = null;
                contentWords = contentWords.listAllWords(content); //content is parsed.
                int length = contentWords.toArray().length;
                
                
                
                //FIND WHERE TO PLACE NEW FILE
                
                this.bubbleSort(false); //puts first allocations at top, last allocations at bottom. BUBBLESORT!
                Allocation[] asArray = this.toArray();
                for( int i = 0; i < asArray.length; i++ ){
                    if( (asArray[i].end).distanceBetween(asArray[i+1].start) <= length ){ //if there is space for it in the pylon,
                        
                        Cursor starter = (asArray[i].start).copy();
                        starter.next();
                        
                        Allocation theAllocation = new Allocation( starter, content );
                        this.push( theAllocation );
                        
                        this.bubbleSort(false); //keeping it sorted. Just for kicks.
                        return theAllocation;
                    } 
                }
                
                return null;
            }
        }
        
                //      INTERACTION WITH DEFLECTORS
        
        /*
        public void write(String s){
            int charCapacity = this.buffer[vanguard].height * this.buffer[vanguard].width - 1;
            String[] assessments = new String[s.length()/charCapacity];
            for(int i = 0; i < assessments.length; i++){
                assessments[i] = s.substring(i*charCapacity, min((i+1)*charCapacity, s.length()));
            }
            
            for(int j = 0; j < assessments.length; j++){
                deflector keeper = buffer[vanguard].copy();
                keeper.write(Cursor.at(0, 1), s);
                add(keeper);
            }    
        } */ 
        //Writes s into the next few available deflectors in pylon. Can split over multiple deflectors.
        
        
        /*
        private int min(int a, int b){
            if(a < b) return a;
            return b;
        } */
        //I can't believe this wasn't built in. Either that, or it was too hard to find.

     
        
}
