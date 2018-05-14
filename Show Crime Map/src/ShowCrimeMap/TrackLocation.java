/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.text.html.HTMLEditorKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author Rajib
 */
public class TrackLocation 
{
    
    public static void Get_All_Location()
    {
        try {
            
            BufferedReader br = new BufferedReader(new FileReader("BangladeshLocation.xml"));
            String str="";
            String source = "";
            while( (str=br.readLine()) !=null)
            {
                source+=str;
                source = source + "\r\n";
               
            }
            //System.out.println(source);
            Document doc = Jsoup.parse(source, "", Parser.xmlParser());
            
            Elements el = doc.select("district");
            
            for(Element f:el)
            {
                
                //System.out.println(f.toString());
                Elements allThana = f.select("thana");
                for(Element t:allThana)
                {
                    System.out.println(t.toString());
                    
                }
                //System.out.println(f.select("thana").toString());
                System.out.println("");
                
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String args[])
    {
        Get_All_Location();
    }
    
}
