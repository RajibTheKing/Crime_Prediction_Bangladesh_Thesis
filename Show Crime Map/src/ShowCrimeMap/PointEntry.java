/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajib
 */
public class PointEntry {
    
    BufferedReader br ;
    int x, y;
    TreeMap<String, String> district, division;
    TreeMap<String, String> thana;
    
    public PointEntry() {
        district = new TreeMap<>();
        thana = new TreeMap<>();
        division = new TreeMap<>();
    }
    
    public  void FileRead() 
    {
        String[] ss={"division","district", "thana"};
        try {
            br = new BufferedReader(new FileReader("newTable.txt"));
            
            String str ="";
            String sss="ঢাকা";
            System.out.println("বিভাগঃ "+sss);
            while((str=br.readLine())!=null)
            {
                
                String[] words = str.split("\\s+");
                division.put(words[0], sss);
                district.put(words[1], words[0]);
                thana.put(words[2], words[1]);
                
                if(!words[0].contains(sss)){
                    sss = words[0];
                    System.out.println("বিভাগঃ "+sss);
                }
                int x=0;
                for(String s:words)
                    if(s.length()>0)
                    System.out.print(ss[x++]+" "+s+" ");
////                    {
////                        district.put(s, sss);
////                        thana.put(s, s)
////                    }
                System.out.println(" "+words.length);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PointEntry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PointEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void main(String[] args) {
        PointEntry pm = new PointEntry();
        pm.FileRead();
        
//        String words[] = s.split("([ ]|,|।)+");
//        
//        for(String ss:words)
//        {
//            ss.trim();
//            //if(ss.length()==0) continue;
//            
//            System.out.println(ss);
//        }
    }

    
}
