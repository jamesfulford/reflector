
import java.io.File;
import java.io.FileNotFoundException;

public class program {
        static long timer;
        
        public static void start(String section){
            System.out.println("            //      STARTING " + section + ".");
            askFor.readKey();
            System.out.println();
            timer = System.currentTimeMillis(); //begin timing
        }
        
        public static void end(String section){
            long elapsedTime = System.currentTimeMillis() - timer;
            System.out.println("            " + section + " COMPLETE.       \\\\");
            System.out.println("Time Elapsed in milliseconds: " + elapsedTime);
            askFor.readKey();
            System.out.println();
            System.out.println();
            System.out.println();
            
        }
    
	public static void main(String[] args) throws FileNotFoundException {
		
            
            
                    //      INITIALIZATION  
                    
        start("PARTITION INITIALIZATION");  
        
	partition reflector = partition.constructDefault(pylon.construct(99)); //initializes a default partitition (as detailed in Data structures sessions)
	
        end("PARTITION INITIALIZATION");
        
        
        
                    //      PARSING FROM FILE
        
        start("EXTRACT GREEN EGGS AND HAM");
        
        File extractionPoint = new File ( "src/greenEggsAndHam.txt" );
        StringList greenEggsAndHam = new StringList();
        greenEggsAndHam = greenEggsAndHam.extractToStringList( extractionPoint );
        
        end("EXTRACT GREEN EGGS AND HAM");
        
        
        
                    //      SORTING
        
//        start("SORTING");
//                    
//        greenEggsAndHam.bucketSort(false);
//        greenEggsAndHam.print();
//        
//        end("SORTING");
        


                    //      HASHING
                    
        start("HASHING");
        
        StringList hashbrown = (StringList) greenEggsAndHam.uniqueize();
        IntegerList hashedEggsAndHam = greenEggsAndHam.hashToIntList(hashbrown);
        hashedEggsAndHam.print();
        
        end("HASHING");
        
        
        
                    //      END OF PROGRAM
                    
        end("PROGRAM");
        
       
        
        
        
                    //      SOMETHING OLD, NEW, BORROWED, BLUE
                    
                    
        
                    //      SEARCHING TEST
        
//        for(int i = 0; i<reflector.spire.buffer.length; i++)
//            reflector.spire.buffer[i].array[0][0] = 1; //changes all indexes
//        
//        reflector.spire.buffer[99].array[79][24] = '#'; //at the very end
//        
//            long startSeq = System.currentTimeMillis(); //begin timing
//            reflector.spire.sequentialSearch('#');
//            System.out.println(System.currentTimeMillis() - startSeq + "ms by sequential search,"); //end timing
////        
////            long startInd = System.currentTimeMillis(); //begin timing
////            reflector.spire.indexSearch('#');
////            System.out.println(System.currentTimeMillis() - startInd + "ms by index search,"); //end timing
////        
        



        
	} //end public static void main(String[] args)
        
}