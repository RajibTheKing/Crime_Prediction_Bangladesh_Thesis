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
public class createXML 
{
    createXML()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("RawData.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("newXML.xml"));
            bw.write("<country>\r\n\t<name>বাংলাদেশ</name>\r\n");
            String str;
            boolean flag=false, flag1=false, flag2=false;
            while( (str = br.readLine())!=null)
            {
                if(str.contains("বিভাগ") && flag==false)
                {
                    flag=true;
                    flag1=false;
                    bw.write("\t<division>\r\n\t\t<name>"+str.substring(0, str.length()-6)+"</name>\r\n");
                    continue;
                }
                if(str.contains("বিভাগ") && flag==true)
                {
                    //flag=false;
                    flag1=false;
                        bw.write("\t\t</district>\r\n");
                    bw.write("\t</division>\r\n");
                    bw.write("\t<division>\r\n\t\t<name>"+str.substring(0, str.length()-6)+"</name>\r\n");
                    continue;
                }
                
                if(str.contains("জেলা") && flag1==false)
                {
                    
                    flag1=true;
                    bw.write("\t\t<district>\r\n\t\t\t<name>"+refine(str).substring(0, refine(str).length()-4)+"</name>\r\n");
                    continue;
                }
                
                if(str.contains("জেলা") && flag1==true)
                {
                   // flag1=false;
                    
                    bw.write("\t\t</district>\r\n");
                    
                    bw.write("\t\t<district>\r\n\t\t\t<name>"+refine(str).substring(0, refine(str).length()-4)+"</name>\r\n");
                    continue;
                }
                
                if(str.startsWith("·"))
                {
                    str = refine(str);
                    bw.write("\t\t\t<thana>\r\n\t\t\t\t<name>"+str+"</name>\r\n\t\t\t</thana>\r\n");
                }
                
                
            }
            bw.write("\t\t</district>\r\n");
            bw.write("\t</division>\r\n");
            bw.write("</country>");
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
