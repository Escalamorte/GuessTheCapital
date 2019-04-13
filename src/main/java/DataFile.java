import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataFile extends Thread {

    private static String fileName = "data.txt";
    private static String dir = System.getProperty("user.dir") + "\\src\\main\\resources" + File.separator;
    private static String fullName = dir + fileName;
    private static File data = new File(fullName);
    private static MainWindow mw = new MainWindow();

    private void createFile() {
        if (!data.exists()){
            try{
                if(data.createNewFile()){
                    System.out.println("New file created");
                    setData();
                } else {
                    System.out.println("Error while file creating");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("File already created at " + dir + "\\src\\main\\resources" );
        }
    }

    private void setData() {
        String countries;
        int counter = 0;
        try {
            if (data.exists()) {
                FileReader fr = new FileReader(fullName);
                Scanner scan = new Scanner(fr);

                if (!scan.hasNextLine()) {
                    try {
                        FileWriter fw = new FileWriter(fullName);
                        Document doc = Jsoup.connect("http://ostranah.ru/_lists/capitals.php").get();

                        for (Element table : doc.select("table[id=sort-table]")) {
                            for (Element row : table.select("tr:gt(0)")) {
                                Elements tds = row.select("td:not(rowspan)");
                                countries = (++counter) + ";" + tds.get(0).text() + ";" + tds.get(1).text();
                                System.out.println(countries);
                                fw.write(countries + "\n");
                            }
                        }

                        fw.close();
                    } catch (IOException exp) {
                        System.out.println("Check internet connection.\n" + exp.getMessage());
                    }
                } else {
                    System.out.println("File is ready for use");
                }

                fr.close();
            } else {
                createFile();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int LineCount(){
        int lineCount = 0;
        try{
            FileReader fr = new FileReader(fullName);
            LineNumberReader lnr = new LineNumberReader(fr);

            while (lnr.readLine() != null){
                lineCount++;

            }
            fr.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return lineCount;
    }

    static String getCountry() {
        String country = "";

            try {
                FileReader fr = new FileReader(fullName);
                Scanner scan = new Scanner(fr);

                Random random = new Random();
                int lineNumber = random.nextInt(LineCount()) + 1;
                System.out.println(lineNumber);

                for (int i = 0; i < lineNumber; i++) {
                    country = scan.nextLine();
                }
                System.out.println(country);
                fr.close();
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            }
        return country;
    }

    public void run() {
       new DataFile().setData();
    }


    public static void main(String[] args) {
        DataFile.getCountry();
    }
}