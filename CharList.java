/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author James Fulford
 */
public class CharList extends AList{

    @Override
    AList createNew() {
        return new CharList();
    }

    @Override
    public boolean beats(Object a, Object b) {
        return ( (int) a > (int) b ); //OMG casting is so useful!!! 
        //Took me 10 minutes, then it just came to mind. Wow. That is so cool.
    }

    @Override
    void printElement(Object a) {
        System.out.println( (char) a);
    }
    
    char[] toArray(){
        char[] temp = new char[end];
        for(int i = 0; i < temp.length; i++)
            temp[i] = (char) array[i];
        return temp;    
    }
    
                //      NON ABSTRACT IMPLEMENTATION
    
    public String flush(){
            String result = "";
            boolean notNull = true;
            while(end > 0)
                result = pop() + result; //tack on the stack entries to the front. Puts things in the right order.
            return result;     
        } //returns and empties the entire stack. Puts the characters in the inverse order, top being last.
    
}
