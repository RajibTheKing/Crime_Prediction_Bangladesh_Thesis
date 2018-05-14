/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banglacharexp;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Rajib
 */
public class BanglaCharExp {

    public static void main(String[] args) 
    {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("text.txt"));
            
            String str;
            while( (str = br.readLine())!=null )
            {
                System.out.println("");
                System.out.println(str);
                for(int i=0;i<str.length();i++)
                    System.out.print(str.charAt(i)+" ");
                 String ss = JOptionPane.showInputDialog(null, str+"?", str);
            }
            System.out.println("");
            
            
        } catch (Exception ex) {
            Logger.getLogger(BanglaCharExp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
