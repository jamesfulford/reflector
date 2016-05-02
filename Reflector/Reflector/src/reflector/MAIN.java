package reflector;

import commons.AskFor;
import java.io.File;
import java.io.FileNotFoundException;
import listsandsorts.StringList;

/*
    @author James Fulford
    @lastEdit 5/1/16
    @jing {
    POEM READER: http://screencast.com/t/l47xXxkhDo1j
    DEFAULT PARTITION: http://screencast.com/t/poyusV5f
    }
*/
public class MAIN {

    static long timer;

    public static void start( String section ) {
        System.out.println("            //      STARTING " + section + ".");
        AskFor.readKey();
        System.out.println();
        timer = System.currentTimeMillis(); //begin timing
    }

    public static void end( String section ) {
        long elapsedTime = System.currentTimeMillis() - timer;
        System.out.println("            " + section + " COMPLETE.       \\\\");
        System.out.println("Time Elapsed in milliseconds: " + elapsedTime);
        AskFor.readKey();
    }



    public static void main( String[] args ) throws FileNotFoundException {
        
        start("PARTITION INITIALIZATION");

        Pylon james = new Pylon(99);
        PoemPartition poemReader = new PoemPartition(james);
        DefaultPartition reflector = new DefaultPartition(james);
        reflector.paint();
        
//        System.out.println("Would you like to see the deflector's regions?");
//        if(AskFor.aString().toUpperCase().contains("Y")){
//            reflector.print();
//        }

        end("PARTITION INITIALIZATION");


        start("EXTRACT FILES AND SAVE TO PYLON");

        File extractionPoint = new File("src/reflector/greenEggsAndHam.txt");
        StringList greenEggsAndHamWords = new StringList();
        greenEggsAndHamWords.extractWords(extractionPoint);
        poemReader.save("Green Eggs And Ham¶by Dr Seuss", greenEggsAndHamWords);
        
        

        //note: ¶ will cause my program to skip a line without breaking things.
        //note: \n doesn't break anything, but it does make partitions and deflectors print out funny. And wrong.
        //note: I guess that means \n does break things. Hm.
        //Use  pilcrow ¶ wisely.
        //                 ¶

//        System.out.println("Would you like to see Green Eggs and Ham before we put it in the reflector?");
//        if(AskFor.aString().toUpperCase().contains("Y")){
//            greenEggsAndHamWords.print();
//        }
        
        end("EXTRACT FILES AND SAVE TO PYLON");

        start("READING POEMS");

        reflector.loadFileFromPylon(0);

        end("READING POEMS");
        
        
        end("PROGRAM");

    } //end public static void main(String[] args)

}
