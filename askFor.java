
import java.math.BigInteger;
import java.util.Scanner;

/*
 * Static methods that use scanners to ask user for information of different data types.
 * Arrays are implemented.
 * If something goes wrong, method runs again until input is alright.
 */

/**
 *
 * @author James
 */
public class askFor {
    
    private static Scanner type = new Scanner(System.in);
    
                //      Strings
    
    public static String aString(){
        try {
            System.out.println("Please enter a String (String): ");
            String input = type.nextLine();
            
            //checking for errors.
            EXEP.isItNull(input);
            EXEP.isItEmpty(input);
            
            return input;
            
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aString();
        }
    }
    //will not return empty or null Strings.
    public static String[] aStringArray(int size){
        try {
            if(size > 0){
                String[] temp = new String[size];
                for(int i = 0; i<size; i++){
                    System.out.print(i + ": ");
                    temp[i] = aString();
                }
                return temp;
            } else {
                throw new Exception("Array Size: " + size + " is unacceptable. ");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "Please enter a new array size:");
            return aStringArray(anInt()); //try again if error. Recursion!
        }
    }
    
    
    
                //      Ints
    
    public static int anInt(){
        try {
            System.out.println("Please enter an Integer (int): ");
            return type.nextInt();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return anInt();
        }
    }
    
    public static int aPositiveInt(){
        try {
            System.out.println("Please enter a Positive Integer (int): ");
            int input = type.nextInt();
            if(input > 0)
                return type.nextInt();
            else 
                throw new Exception("Input is not positive. ");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aPositiveInt();
        }
    }
    //explicitly asks for a positive integer. Does not accept 0.
    public static int aNonNegativeInt(){
        try {
            System.out.println("Please enter a non-negative Integer (int): ");
            int input = type.nextInt();
            if(input >= 0)
                return type.nextInt();
            else 
                throw new Exception("Input is not non-negative. ");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aPositiveInt();
        }
    }
    //explicitly asks for a non-negative integer. Does accept 0.
    
    public static int aNegativeInt(){
        try {
            System.out.println("Please enter a Negative Integer (int): ");
            int input = type.nextInt();
            if(input < 0)
                return type.nextInt();
            else 
                throw new Exception("Input is not negative. ");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aPositiveInt();
        }
    }
    //explicitly asks for a negative integer. Does not accept 0.
    public static int aNonPositiveInt(){
        try {
            System.out.println("Please enter a non-positive Integer (int): ");
            int input = type.nextInt();
            if(input <= 0)
                return type.nextInt();
            else 
                throw new Exception("Input is not non-positive. ");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aPositiveInt();
        }
    }
    //explicitly asks for a non-positive integer. Does  0.
    
    public static int aNonZeroInt(){
        try {
            System.out.println("Please enter a non-zero Integer (int): ");
            int input = type.nextInt();
            if(input != 0)
                return type.nextInt();
            else 
                throw new Exception("Input is not non-zero. ");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aPositiveInt();
        }
    }
    //explicitly asks for a non-zero integer. Does not accept 0.
    
                    //      Integer Arrays
    
    public static int[] anIntArray(int size){
        try {
            if(size > 0){
                int[] temp = new int[size];
                for(int i = 0; i<size; i++){
                    temp[i] = anInt();
                }
                return temp;
            } else {
                throw new Exception("Array Size: " + size + " is unacceptable. ");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "Please enter a new array size:");
            return anIntArray( aPositiveInt() ); //try again if error. Recursion!
        }
    }
    
    
    
                //      BigIntegers
    
    public static BigInteger aBigInteger(){
        try {
            System.out.println("Please enter an Integer (BigInteger): ");
            
            BigInteger input = type.nextBigInteger();
            if(input == null)
                throw new Exception("Input is null. ");
            return input;
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aBigInteger();
        } 
    }

    public static BigInteger aNonZeroBigInteger(){
        try {
            System.out.println("Please enter a non-zero Integer (BigInteger): ");
            BigInteger input = type.nextBigInteger();
            if(input == null)
                throw new Exception("Input is null. ");
            if(input.equals(0)) 
                return input;
            else
                throw new Exception("Input is not non-zero. "); 
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return aBigInteger();
        } 
    }
    //explicitly requests a non-zero integer. Does not accept 0.
   
                    //      BigInteger Arrays
    
    public static BigInteger[] aBigIntegerArray(int size){
        try {
            if(size > 0){
                BigInteger[] temp = new BigInteger[size];
                for(int i = 0; i<size; i++){
                    temp[i] = aBigInteger();
                }
                return temp;
            } else {
                throw new Exception("Array Size: " + size + " is unacceptable. ");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "Please enter a new array size:");
            return aBigIntegerArray( aPositiveInt() );
        }
    }
    
    public static BigInteger[] aNonZeroBigIntegerArray(int size){
        try {
            if(size > 0){
                BigInteger[] temp = new BigInteger[size];
                for(int i = 0; i<size; i++){
                    temp[i] = aNonZeroBigInteger();
                }
                return temp;
            } else {
                throw new Exception("Array Size: " + size + " is unacceptable. ");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "Please enter a new array size:");
            return aBigIntegerArray( aPositiveInt() );
        }
    }
    
}
