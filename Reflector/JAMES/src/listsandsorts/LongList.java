package listsandsorts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author James Fulford
 * @lastEdit 5/1/2016
 * @jing http://screencast.com/t/9yhI9c5d
 */
public class LongList extends AList {

    @Override
    public AList createNew() {
        return new LongList();
    }

    @Override
    public boolean beats(Object a, Object b) {
        return (long) a > (long) b;
    }

    @Override
    public void printElement(Object a) {
        System.out.println(a);
    }

    @Override
    public void extract(File extractionPoint) throws FileNotFoundException {
        Scanner type = new Scanner(extractionPoint);
        while (type.hasNextLong()) {
            push(type.nextLong());
        }
        type.close();
    }

    /**
     *
     * @return non dynamic array of entries in list.
     */
    public long[] toArray() {
        long[] temp = new long[end];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = (long) array[i];
        }
        return temp;
    }

    /**
     *
     * @return array of strings, via String.valueOf method. (AList toStringArray wasn't working)
     */
    public String[] toStringArray() {
        String[] temp = new String[end];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = String.valueOf(array[i]);
        }
        return temp;
    }

}
