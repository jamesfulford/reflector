/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author James
 */
public class Timer {
    long start;
    boolean running = false;
    
    private long elapsedTime = 0;
    
    public void start(){
        if(!running) {
            running = true;
            start = System.nanoTime();
        }
    }
    
    public long end(){
        elapsedTime = System.nanoTime() - start;
        running = false;
        
        return elapsedTime;
    }
    
    public long lap() {
        return System.nanoTime() - start;
    }
}
