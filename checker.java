import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class checker {


    public static void main(String[] args) {
        //SHOWCLASSPATH();
        //sendEmail("thekieransmith@gmail.com");

        String url;
        try{
            url = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You must specify the link to monitor\n(ex: https://www.uvic.ca/BAN1P/bwckschd.p_disp_detail_sched?term_in=201901&crn_in=20623)");
            return;
        }

        int period = 15; //period in minutes

        long pMillis = period*60*1000; //period in millis
        while(true) {
            int remaining = 0;
            try {
                remaining = remaining(url);
                System.out.println(remaining);
            } catch (IOException IOE) {
                System.out.println("SOMETHING BROKE");
            }
            if (remaining > 0) {
                System.out.println("THERE IS A SPOT GO DO STUFF" + "\n(" + remaining + " spot(s) remaining)");
            } else {
                System.out.println("not yet");
            }

            try
            {
                Thread.sleep(pMillis);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }

    }


    static int remaining(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();


        Element table = doc.select("table").get(3).select("table").get(0); //select the first table.
        Elements rows = table.select("tr"); //the rows
        Element row = rows.get(1);
        Elements datadisplaytable = row.select("td").get(0).select("tr");

        Elements waitListRow = row.select("td").get(0).select("tr").get(2).select("td");
        int wl = Integer.parseInt(waitListRow.get(1).ownText());

        Element remaining = datadisplaytable.select("tr").get(1).select("td").get(2);

        int rem = Integer.parseInt(remaining.ownText());
        int actualRemaining = rem - wl;

        return actualRemaining;
    }

    static void SHOWCLASSPATH(){
        // SHOW ME THE CLASSPATH
        System.out.println("\n---------------------------");
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("---------------------------\n");
        //I WILL END YOU
    }
}


