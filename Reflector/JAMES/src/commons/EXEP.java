package commons;


import java.math.BigInteger;

/*
 * A bunch of homebrewed exceptions, plus some methods
 */

/**
 *
 * @author James Fulford
 * @lastEdit 5/1/16
 * @jing http://screencast.com/t/UIlqrLnZ
 */
class EXEP {
    
                //      METHOD EXCEPTIONS
    
//    public static Exception itsNotPossible = new Exception("Something went wrong. This task cannot be completed. (EXEP.itsImpossible) ");
//    public static void itsImpossible(){
//        throw itsNotPossible;
//    }
    
                //      DATATYPE ATTRIBUTE EXCEPTIONS
    
    public static Exception itsNull = new Exception("Input is null. ");
    public static void isItNull(Object x) throws Exception{
        if(x == null) throw itsNull;
    }
    
    public static Exception itsNotEqual = new Exception("Two values are not equal. ");
    public static void areTheyNotEqual(Object x, Object y) throws Exception{
        if(!x.equals(y)) throw itsNotEqual;
    }
    
    public static Exception itsEqual = new Exception("Two values are equal. ");
    public static void areTheyEqual(Object x, Object y) throws Exception{
        if(!x.equals(y)) throw itsEqual;
    }
    
    
   
                //      NUMBERS    
    
    public static Exception itsZero = new Exception("Input is not non-zero. ");
    public static void isItZero(Object x) throws Exception{
        if(x.equals(0)) throw itsZero;
        if(x.equals(BigInteger.ZERO)) throw itsZero;
    }
    
    
    
                //      STRINGS
    
    public static Exception itsEmpty = new Exception("Input is empty. ");
    public static void isItEmpty(String x) throws Exception{
        if(x.length() == 0) throw itsEmpty;
    }
    
    
    
    
    
    
}
