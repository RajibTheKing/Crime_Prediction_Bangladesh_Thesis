/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extract_Location;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajib
 */
public class createTable 
{
    createTable()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("RawData.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("newTable.xml"));
            
            String district="", division="", thana, str;
            while( (str = br.readLine())!=null)
            {
                if(str.contains("বিভাগ")) division = str.substring(0, str.length()-6);
                if(str.contains("জেলা") ) district = refine(str).substring(0, refine(str).length()-4);
                
                if(str.startsWith("·"))
                {
                    str = refine(str);
                    bw.write(division+"\t\t"+district+"\t\t"+str+"\r\n");
                }
                
                
            }
            bw.flush();
            bw.close();
            System.out.println("Done");
            
        } catch (Exception ex) {
            Logger.getLogger(createXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    String refine(String s)
    {
        String ss="";
        s.replaceAll("[ঃ১২৩৪৫৬৭৮৯/]", ss);
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='.' || s.charAt(i)==' ' ||s.charAt(i)=='\t'||s.charAt(i)=='ঃ' || s.charAt(i)=='·'|| (s.charAt(i)>='০' && s.charAt(i)<='৯') || s.charAt(i)=='ঃ' || s.charAt(i)=='/')
                continue;
            ss+=s.charAt(i);
        }
        return ss;
    }
    
}
