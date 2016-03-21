



public class program {
    
    
	public static void main(String[] args) {
		
            
            
                    //      INITIALIZATION  
	partition reflector = partition.constructDefault(); //initializes a default partitition (as detailed in Data structures sessions)
	reflector.pylon = pylon.construct(99); //this is my memory of 99 levels (see pylon class) being connected to my partition. The missing level is the master deflector of the partition.
	reflector.pylon.blanketStatement(reflector.master);//fills the pylon with
        System.out.println("Initialization Complete");
	System.out.println();
        
        
        
                    //      SOMETHING OLD, NEW, BORROWED, BLUE
        
        
                    
                    
        
                    //      SEARCHING TEST
        
//        for(int i = 0; i<reflector.pylon.buffer.length; i++)
//            reflector.pylon.buffer[i].array[0][0] = 1; //changes all indexes
//        
//        reflector.pylon.buffer[99].array[79][24] = '#'; //at the very end
//        
//            long startSeq = System.currentTimeMillis(); //begin timing
//            reflector.pylon.sequentialSearch('#');
//            System.out.println(System.currentTimeMillis() - startSeq + "ms by sequential search,"); //end timing
////        
////            long startInd = System.currentTimeMillis(); //begin timing
////            reflector.pylon.indexSearch('#');
////            System.out.println(System.currentTimeMillis() - startInd + "ms by index search,"); //end timing
////        
        



        
	} //end public static void main(String[] args)
        
}