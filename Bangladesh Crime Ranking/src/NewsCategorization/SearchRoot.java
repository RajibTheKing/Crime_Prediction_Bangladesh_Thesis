/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NewsCategorization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchRoot
{
    BufferedWriter bw = null;
    SearchRoot()
    {
        
        NewsCategorization.mp.clear();
        try {
            
            BufferedReader br = new BufferedReader(new FileReader("Resource/RootWord.txt"));
            String str=null;
            
            while( (str=br.readLine() )  !=null)
            {
                System.out.println(str);
                String s[] = str.split(" ");
                if(s.length>1)
                    NewsCategorization.mp.put(s[0], s[1]);
                
            }
            br.close();
            bw  = new BufferedWriter(new FileWriter("Resource/RootWord.txt", true));
        } catch (Exception ex) {
            Logger.getLogger(SearchRoot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }

    void InsertItem(String key, String value)
    {
        NewsCategorization.mp.put(key, value);
        
        try {
            //bw = new BufferedWriter(new FileWriter("Resource/RootWord.txt"));
            bw.append(key+" "+value);
            bw.append("\r\n");
            bw.flush();
            
            
            System.out.println("Added: "+key+" "+value);
        } catch (IOException ex) {
            Logger.getLogger(SearchRoot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void CloseALL()
    {
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(SearchRoot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    String getItem(String key)
    {
        return NewsCategorization.mp.get(key);
    }
    
    boolean isAvailable(String str)
    {
        if(NewsCategorization.mp.get(str)!=null)
            return true;
        return false;
    }
    
}
