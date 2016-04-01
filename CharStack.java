/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author James
 */
  
    public class CharStack{
        char[] stack = new char[40];
        int top = 0; //points to next available position
        
        public static CharStack init(){
            return new CharStack();
        }
        
        public void push(char a){
            if(top>=stack.length){
              System.out.println("Stack overflow! Did not push character " + a);  
            } else {
            stack[top] = a;
            top++;
            }
        } //if there is room in the stack, adds character onto the stack.
        
        public char pop(){
            if(top > 0){
                    return stack[--top];
            } else {
                    return (char) 0;
            }
        } //if there is something, returns highest character in stack. Else, returns (char) 0 (null).
        
        public String flush(){
            String result = "";
            boolean notNull = true;
            while(top > 0)
                result = pop() + result; //tack on the stack entries to the front. Puts things in the right order.
            return result;     
        } //returns and empties the entire stack. Puts the characters in the inverse order, top being last.
        
        public void funnel(String word){
            if(word.length() == 0){
                //do nothing. This is here for logical inversion.
            } else {
                char[] arrayWord = word.toCharArray();
                for(int i = 0; i < arrayWord.length; i++)
                    push(arrayWord[i]);  
            }
        } //pushes the entire string into the stak. Does nothing if the string has no length.
        
    }
    
