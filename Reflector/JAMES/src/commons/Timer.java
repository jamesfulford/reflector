package commons;

/**
 *
 * @author James Fulford
 * @lastEdit 5/1/16
 * @jing http://screencast.com/t/UIlqrLnZ
 */
public class Timer {
    long start;
    boolean running = false;
    
    public long elapsedTime = 0;
    
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
