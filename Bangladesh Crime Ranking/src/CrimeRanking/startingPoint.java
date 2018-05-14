package CrimeRanking;

import Extract_Location.FindLocation;
import ParseDocument.parseDocument;
import ShowCrimeMap.ShowMap;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rajib
 */
public class startingPoint 
{
    public static FindLocation fl;
    public static ShowMap frame;
    
    public static void main(String[] args)
    {
        
        // TODO code application logic here
        
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 14)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 14)));
        
        //new RawData();
        //new parseDocument();
        //fl = new FindLocation();
        //frame = new ShowMap();
        
        NewsCategorization newsCategorize = new NewsCategorization();
        //GenerateRandomPredictionTable();
        
    }
    
    public static void GenerateRandomPredictionTable()
    {
        DatabaseConnection db = new DatabaseConnection();
        int loc_id, year, month, day;
        Random rand = new Random();
        for(int i=0;i<1000;i++){
        year = rand.nextInt(10)+2004;
        month = rand.nextInt(12)+1;
        day = rand.nextInt(28)+1;
        loc_id = rand.nextInt(526)+57;
        String dd = ""+year+"-"+month+"-"+day;
        String location_id = ""+loc_id;
                
        
        try {
            db.queryInsert("insert into predictiontable values  (NULL, '"+dd+"', '"+location_id+"')");
            
        } catch (SQLException ex) {
            Logger.getLogger(startingPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
        
    }
    
}
