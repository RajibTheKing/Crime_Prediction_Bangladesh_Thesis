/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseDocument;

import CrimeRanking.DatabaseConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import java.lang.Object.*;
import java.sql.SQLException;
import java.util.Random;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Rajib
 */
public class parseDocument {

    public parseDocument() {
        DatabaseConnection DB = new DatabaseConnection();

        try {
            BufferedReader br = new BufferedReader(new FileReader("Resource/newdata.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("Resource/Problem_Query.txt", true));
            String data = "", str;
            int kounter = 0;
            while ((str = br.readLine()) != null) {
                data += str;
                data += "\r\n";
                System.out.println("Line("+(++kounter)+"): "+str);
            }
            br.close();
            //System.out.println(data);
            Document doc = Jsoup.parse(data, "", Parser.xmlParser());
            Elements el = doc.select("Index");
            System.out.println("Size: " + el.size());

            String title, content, category, city, domain, date, url, type;

            for (Element e : el) {
                title = escapeString(e.select("TITLE").text());
                content = escapeString(e.select("CONTENT").text());
                category = escapeString(e.select("CATEGORY").text());
                city = escapeString(e.select("CITY").text());
                domain = escapeString(e.select("DOMAIN").text());
                date = escapeString(e.select("DATE").text());
                url = escapeString(e.select("URL").text());
                type = escapeString(e.select("TYPE").text());

                date = PurifyDateString(date);

                //System.out.println(date);
                System.out.println(content);
                System.out.println("");
                String qry = "";
                try {
                    qry = "INSERT INTO crawled_data VALUES(NULL, '" + title + "','" + content + "','" + category + "','" + city + "','" + domain + "','" + date + "','" + url + "','" + type + "')";
                    DB.queryInsert(qry); //এইখানে আমি আপডেট বন্ধ করে রেখেছি... 
                } catch (SQLException ff) {
                    ff.printStackTrace();
                    System.out.println("Problem in " + qry);
                    bw.append("\r\n");
                    bw.append(qry + ";");

                    bw.flush();


                }
                //System.out.println(content);
            }
            bw.close();
        } catch (Exception ex) {
            Logger.getLogger(parseDocument.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String escapeString(String str) {
        return StringEscapeUtils.escapeSql(str);
    }

    private String PurifyDateString(String date) {
        System.out.println("Here date: " + date);
        
        Random rd = new Random();
        String dd = "";
        if (date.compareTo(dd) == 0 || date.length() < 8) {
            dd+=(rd.nextInt(2)+2011);
            dd+="-"+(rd.nextInt(11)+1);
            dd+="-"+(rd.nextInt(28)+1);
            return dd;
        }

        if (date.contains("-")) {
            try {
                String[] str = date.split("[ -]+");
                dd = str[2] + "-" + str[1] + "-" + str[0];
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {

                dd += date.substring(0, 4);
                dd += "-";
                dd += date.substring(4, 6);
                dd += "-";
                dd += date.substring(6, 8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dd;
    }
}
