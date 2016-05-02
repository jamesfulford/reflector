package listsandsorts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author James Fulford
 * @dateEdit 5/1/16
 * @jing http://screencast.com/t/9yhI9c5d
 */
public class IntegerList extends AList { //all like counter-cascad-y stuffs. 
    //Call it on a subclass and it isn't caught, it flows down river to the superclass and runs there.

    @Override
    public IntegerList createNew() {
        IntegerList cart = new IntegerList();
        return cart;
    }

    @Override
    public boolean beats(Object a, Object b) {
        return ((int) a > (int) b); //OMG casting is so useful!!! 
        //Took me 10 minutes, then it just came to mind. Wow. That is so cool.
    }

    @Override
    public void printElement(Object a) {
        System.out.println((int) a);
    }

    @Override
    public void extract(File extractionPoint) throws FileNotFoundException {
        Scanner type = new Scanner(extractionPoint);
        while (type.hasNextInt()) {
            push(type.nextInt());
        }
        type.close();
    }
    // reads file contents. Clears this StringList and populates with words from file.

    /**
     *
     * @return List as an array of ints. Non-dynamic.
     */
    public int[] toArray() {
        int[] temp = new int[end];
        for (int i = 0; i < end; i++) {
            temp[i] = (Integer) array[i];
        }
        return temp;
    }

    /**
     *
     * @return largest value in the list.
     */
    public int max() {
        int[] theArray = toArray();
        int max = 0;
        for (int i = 0; i < theArray.length; i++) {
            if (theArray[i] > max) {
                max = theArray[i];
            }
        }
        return max;
    }
    //returns the value of the largest int value in the list.

    /**
     * Bucket Sorts the list.
     *
     * @param weakestToStrongest (smallest to largest? true: yes, false: no)
     */
    public void bucketSort(boolean weakestToStrongest) {

        //      FIRST, we will sort the numbers using StringList's bucketSort method.
        StringList theSorter = new StringList();
        for (int element : toArray()) {
            theSorter.push(padd(element, 4));
        }
        theSorter.bucketSort(weakestToStrongest); //sort the numbers as Strings
        String[] sortedNumbers = theSorter.toArray(); //array of the sorted numbers as Strings

        //      SECOND, we will import the results.
        clear(); //make room for the import
        for (String element : sortedNumbers) {
            push(Integer.valueOf(element));
        }
    }

    private String padd(int number, int desiredLength) {
        String num = String.valueOf(number);
        while (num.length() < desiredLength) {
            num = "0" + num;
        }
        return num;
    }
    //ensures that when bucketsorting, shorter numbers don't accidentally get ranked higher
    //simply because they start with 9. Or something like that.

    /**
     *
     * @param quantity number of random numbers to generate
     * @param top highest random number to generate.
     */
    public void generateRandomIntegers(int quantity, int top) {
        clear();
        for (int i = 0; i < quantity; i++) {
            push((int) (Math.random() * top));
        }
    }

    /**
     * Clears list and fills with enough prime numbers.
     *
     * @param quantity number of primes to generate.
     */
    public void generatePrimes(int quantity) {
        if (quantity > 0) {
            clear();
        }
        push(2);
        nextNumber:
        for (int i = 3; end < quantity; i++) {

            for (int prime : this.toArray()) {
                if (i % prime == 0) {
                    continue nextNumber;
                }
            }
            push(i);
        }

    }

}
