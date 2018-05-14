/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class File_Update 
{
    void File_Write(String path, String lines)
    {
        
    }
    
    public void File_Append(String path, String str)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.append("\r\n");
            bw.append(str);
            bw.flush();
            bw.close();
     //       System.out.println("Append: "+str+" to "+path);
        } catch (IOException ex) {
            Logger.getLogger(File_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
