/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RadialGradientPaint;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Rajib
 */
public class NewsCategorization 
{
    RawData rawData;
    PreData preeData;
    DatabaseConnection DB;
    Set<String> A;
    NewsCategorization(Set<String> temp)
    {
        A = temp;
        //startCategorization();
    }
    
    public NewsCategorization()
    {
        rawData = new RawData();
        preeData = new PreData();
        DB = new DatabaseConnection();
        Start_Categorization();
    }
    private void Start_Categorization()
    {
        try {
            ResultSet rs = DB.queryResult("select * from crawled_data limit 10");
            String id, content, category, date, location_id, ccc;
            while(rs.next())
            {
                id = rs.getString("id");
                content = rs.getString("content");
                ccc = rs.getString("category");
                category = Naive_Bayes_Clustering(content);
                date = rs.getString("date");
                location_id="0";
                //if(!ccc.contains("crime")) continue;
                int flag = Get_Users_Confirmation(content, category, ccc);
                if(flag==0)
                {
                    String query = "INSERT into maindata values (NULL, '"+StringEscapeUtils.escapeSql(content)+"', '"+category+"', '"+date+"','"+location_id+"')";
                    DB.queryInsert(query);
                    continue;
                }
                else if(flag==1)
                {
                    String ct="";
                    while(true)
                    {
                        ct = JOptionPane.showInputDialog("Enter Confirm Category: ");
                        if(getIndexOf(ct)>-1) break;
                        else JOptionPane.showMessageDialog(null, "Please Careful about input");
                    }
                    System.out.println(ct);
                    
                    String query = "INSERT into maindata values (NULL, '"+StringEscapeUtils.escapeSql(content)+"', '"+ct+"', '"+date+"','"+location_id+"')";
                    DB.queryInsert(query);
                    //System.out.println("Database Entry is not being applied");
                }
                else continue;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NewsCategorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private int Get_Users_Confirmation(String content, String category, String ccc)
    {
        JTextArea textArea = new JTextArea("Given Category: "+ccc+"\r\n"+content+"\r\n\r\nCategory Calculated: "+category);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SolaimanLipi", Font.PLAIN, 20));
        
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        //JOptionPane.showMessageDialog(null, scrollPane, "Is It Correct??", JOptionPane.YES_NO_CANCEL_OPTION);
        
        
        int optionType = JOptionPane.YES_NO_CANCEL_OPTION; // YES+NO+CANCEL
        int res = JOptionPane.showConfirmDialog(null, scrollPane, "Is It Correct??", optionType);
        return res;
    }
    
    private String Naive_Bayes_Clustering(String document)
    {
        //http://nlp.stanford.edu/IR-book/html/htmledition/naive-bayes-text-classification-1.html
        
        double Likelihood_Probability, Posterior_Probability;
        double Prior_Probability[] = new double[5];
        Occurance O;
        
        String words[] = document.split("([[ ]|.|।|,|[+]|ঃ|/|<|>|#|?|!|-|‘|’|:|\\\"|\\;|\\r|\\n|\\s|\\t|\\(|\\)|[a-zA-Z]|[০-৯]|\\d|-|\\'||\\*|\\[|\\-])+"); //এখানে সবচেয়ে Latest Regular expression টা লেখা হয়েছে :D
        for(int i=0;i<Prior_Probability.length;i++)
        {
            
            Prior_Probability[i] = Math.log10(preeData.documents_kount[i] *1.0 / preeData.total_documents *1.0) ; 
            //System.out.println(i+": "+preeData.documents_kount[i]+", "+preeData.total_documents);
            
            for(String term:words)
            {
                term = term.trim();
                if(rawData.useless.contains(term)) continue;
                if(rawData.rootWord.containsKey(term))
                    term = rawData.rootWord.get(term);
                
                if(preeData.TermFrequency.containsKey(term))
                    O = preeData.TermFrequency.get(term);
                else
                    O = new Occurance();
                //if(i==0) System.out.println(term);
                
                Likelihood_Probability = (O.kounter[i]+1)*1.0 /  (O.total + 1)*1.0;
                
                Prior_Probability[i]+=Math.log10(Likelihood_Probability);
            }
            
        }
        
        int indx=0;
        double argMax=-(1<<30);
        System.out.println("************************************");
        System.out.println(document);
        for(int i=0;i<Prior_Probability.length;i++)
        {
            System.out.println(getNameOf(i)+": "+Prior_Probability[i]);
            if(Prior_Probability[i]>argMax) 
            {
                argMax  = Prior_Probability[i];
                indx=i;
            }
        }
        System.out.println("Category Calculated: "+getNameOf(indx));
        System.out.println("************************************");
        
        
        return getNameOf(indx);
    }
    private String getNameOf(int id)
    {
        if(id==0) return "crime";
        if(id==1) return "sports";
        if(id==2) return "entertainment";
        if(id==3) return "technology";
        return "others";
        
    }
    private int getIndexOf(String s)
    {
        if(s.equals("crime")) return 0;
        if(s.equals("sports")) return 1;
        if(s.equals("entertainment")) return 2;
        if(s.equals("technology")) return 3;
        if(s.equals("others")) return 4;
        return -1;
    }
    
}


