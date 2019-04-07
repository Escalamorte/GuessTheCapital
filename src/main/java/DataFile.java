import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataFile {

    private String fileName = "data.txt";
    private String dir = System.getProperty("user.dir");
    private String fullName = dir + "\\src\\main\\resources" + File.separator + fileName;
    private File data = new File(fullName);

    private void createFile() {
        if (!data.exists()){
            try{
                if(data.createNewFile()){
                    System.out.println("New file created");
                    getDate();
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

    void getDate() {
        String countries;
        if (data.exists()) {
            try {
                FileWriter fw = new FileWriter(fullName);
                Document doc = Jsoup.connect("http://ostranah.ru/_lists/capitals.php").get();

                for (Element table : doc.select("table[id=sort-table]")){
                    for ( Element row : table.select("tr:gt(0)")) {
                        Elements tds = row.select("td:not(rowspan)");
                        countries = tds.get(0).text() + ";" + tds.get(1).text();
                        System.out.println(countries);
                        fw.write(countries + "\n");
                    }
                }

                fw.close();
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            }
        } else createFile();
    }

    public static void main(String[] args) {
       new DataFile().getDate();
    }

}
