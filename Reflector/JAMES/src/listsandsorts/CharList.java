package listsandsorts;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author James Fulford
 * @lastEdit 5/1/2016
 * @jing http://screencast.com/t/9yhI9c5d
 */
public class CharList extends AList {

    @Override
    public AList createNew() {
        return new CharList();
    }

    @Override
    public boolean beats(Object a, Object b) {
        return ((int) a > (int) b); //OMG casting is so useful!!! 
        //Took me 10 minutes, then it just came to mind. Wow. That is so cool.
    }

    @Override
    public void printElement(Object a) {
        System.out.println((char) a);
    }

    @Override
    public void extract(File extractionPoint) throws FileNotFoundException {
        StringList extractor = new StringList();
        extractor.extract(extractionPoint);
        for (char e : extractor.toString().toCharArray()) {
            push(e);
        }
    }

    char[] toArray() {
        char[] temp = new char[end];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = (char) array[i];
        }
        return temp;
    }

    //      NON ABSTRACT IMPLEMENTATION
    /**
     *
     * @return inverse concatenation of all entries. (Like a stack)
     */
    public String flush() {
        String result = "";
        boolean notNull = true;
        while (end > 0) {
            result = pop() + result; //tack on the stack entries to the front. Puts things in the right order.
        }
        return result;
    } //returns and empties the entire stack. Puts the characters in the inverse order, top being last.

}
