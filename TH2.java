package htmlintegritychecker;

import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TH2 implements Runnable {
double millies;
    @Override
    public void run() {
        while (!Main.q2.isEmpty()) {
            String tempLink = (String) Main.q2.remove();                        
            Main.q.add(tempLink);                                               //return what in q2 to q1
        }
        if (Main.depthNow == Main.neededDepth-1) {
            millies = Math.abs(Main.time.getTime() - java.util.Calendar.getInstance().getTime().getTime()); //calculating consumed time
            Main.diff=millies/1000;
        }
        
        System.out.println("Valid Links = " +Main.valid + " \t InValid Links = " + Main.inValid + " "+" \t Time Consumed = "+Main.diff+" \t No. of threads = "+Main.threadsNo);        //represent the process on console
    }
}
