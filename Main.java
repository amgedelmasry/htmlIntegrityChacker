package htmlintegritychecker;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import javax.swing.JOptionPane;

public class Main {

    static int counter1;
    static Date time;
    public static int valid, inValid;
    static Queue<String> q
            = new LinkedList<>();
    static Queue<String> q2
            = new LinkedList<>();
    static Thread threads[] = new Thread[50];
    static int neededDepth = 1;
    static int depthNow = 0;
    static int threadsNo = 12;
    static String URL;
    static double diff;
    static double min_time=100000;
    static int min_time_thread=0;

    public static void extractLinks1(String URL, Queue queue) {                 //extract links from URL

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                if (link.attr("href").startsWith("https://") || link.attr("href").startsWith("http://")) {
                    queue.add(link.attr("href"));
                } else {
                    String temp = link.attr("href");                            //if the extracted link doesn't have https:// we add it
                    temp = URL + temp;
                    queue.add(temp);
                }
            }
        } catch (IOException e) {
        }
    }

    public static Boolean checkValidity(String URL) throws IOException {        //checks the validity of the URL
        try {
            Document doc = Jsoup.connect(URL).get();                            //connecting if vaild the valid URL increases by one if not inVaild increases one
            valid++;
            return true;
        } catch (Exception e) {
            inValid++;
            return false;
        }
    }
    public static void min(double num)                                          //changeg on minimum time and minimum time threads
    {
        if(num<min_time)
        {
            min_time=num;
            min_time_thread=threadsNo;
            
        }
        
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //Initialization
        diff = 0;
        q.clear();
        q2.clear();
        time = java.util.Calendar.getInstance().getTime();
        valid = 0;
        inValid = 0;

        //Reads from gui interface
        threadsNo = Maingui.threads_no;
        neededDepth = Maingui.depth_no;
        URL = Maingui.URL_NAME;

        /*check if the given URL is connecting or not
         if true the program continue processing if not it shows a messege that
         the URL isn't connecting
         */
        if (checkValidity(URL)) {
            extractLinks1(URL, q);
            depthNow = 0;

            for (; depthNow < neededDepth; depthNow++) {
                for (int i = 0; i < threadsNo; i++) {
                    threads[i] = new Thread(new TH());                  //creating threads from TH class
                    threads[i].start();
                }
                int count = 0;
                while (count != threadsNo) {
                    count = 0;
                    for (int i = 0; i < threadsNo; i++) {

                        if (!threads[i].isAlive()) {                    //a for loop till all threads ended
                            count++;
                        }
                    }
                    if (count == threadsNo) {
                        Thread threadTemp = new Thread(new TH2());      //if count became = no of threads start q2 by using TH2 class
                        threadTemp.start();
                        threadTemp.join();
                        min(Main.diff);
                    }

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unable to connect to the URL..");

        }
    }
}
