/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

import static NewsCategorization.NewsCategorization.IsImportant;
import static NewsCategorization.NewsCategorization.rt;
import static NewsCategorization.NewsCategorization.write_file;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rajib
 */
public class PreData 
{
    DatabaseConnection DB;
    File_Update FU;
    RawData RD;
    TreeMap<String, Occurance> TermFrequency;
    int documents_kount[] = new int[5];
    int total_documents;
    public PreData()
    {
        DB = new DatabaseConnection();
        RD = new RawData();
        FU = new File_Update();
        TermFrequency = new TreeMap<String, Occurance>();
        
        for(int i=0;i<documents_kount.length;i++)
            documents_kount[i]=0;
        total_documents=0;
        UpdateTermFrequency();
    }

    private void UpdateTermFrequency() 
    {
        try {
            ResultSet rs =  DB.queryResult("select * from newsdata");
            String content, category;
            
            while(rs.next())
            {
                total_documents++;
                
                content = rs.getString("content");
                category=rs.getString("category");
                documents_kount[getIndexOf(category)]++;
                
                String words[] = content.split("([[ ]|.|।|,|[+]|ঃ|/|<|>|#|?|!|-|‘|’|:|\\\"|\\;|\\r|\\n|\\s|\\t|\\(|\\)|[a-zA-Z]|[০-৯]|\\d|-|\\'||\\*|\\]|\\[|\\-|\\“])+"); //এখানে সবচেয়ে Latest Regular expression টা লেখা হয়েছে :D 
               // LearnMyData(words);
                
                for(String s:words)
                {
                    if(RD.rootWord.containsKey(s))
                        s=RD.rootWord.get(s);
                    
                    if(RD.useless.contains(s)) continue;
                    Occurance O;
                    if(TermFrequency.containsKey(s))
                    {
                        O = TermFrequency.get(s);
                        O.AddToCategory(category);
                    }
                    else
                    {
                        O = new Occurance();
                        O.AddToCategory(category);
                        TermFrequency.put(s, O);
                    }
                    
                }
            }
            
            Check_TermFrequency();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PreData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int getIndexOf(String s)
    {
        if(s.equals("crime")) return 0;
        if(s.equals("sports")) return 1;
        if(s.equals("entertainment")) return 2;
        if(s.equals("technology")) return 3;
        return 4;
    }

    private void LearnMyData(String words[]) 
    {
        for(int i=0;i<words.length;i++)
        {
            String s = words[i].trim();
            //System.out.println(words[i]);
            if(!RD.rootWord.containsKey(s) && !RD.useless.contains(s))
            {
                int optionType = JOptionPane.YES_NO_CANCEL_OPTION; // YES+NO+CANCEL
                int res = JOptionPane.showConfirmDialog(null, s, "Keep in Useless file?", optionType);
                if(res==0)
                {
                    RD.useless.add(s);
                    FU.File_Append("Resource/UseLessWord.txt", s);
                    continue;
                }
                
                
                
                if(res==2) continue;
                
                if(res==1)
                {
                    String ss = JOptionPane.showInputDialog(null, s+"?", s);

                    if(ss==null)
                    {
                        System.out.println("Cancel");

                    }
                    else
                    {
                        //rt.InsertItem(words[i], ss);
                        RD.rootWord.put(s, ss);
                        FU.File_Append("Resource/RootWord.txt", s+" "+ss);
                        
                       // System.out.println("OK");
                    }

                }
                
            }
                    
        }
    }
    private String getNameOf(int id)
    {
        if(id==0) return "crime";
        if(id==1) return "sports";
        if(id==2) return "entertainment";
        if(id==3) return "technology";
        return "others";
        
    }
    
    private void Check_TermFrequency() 
    {
        Iterator i = valueIterator(TermFrequency);
        Occurance O;
        while (i.hasNext()) 
        {
            
            Map.Entry me = (Map.Entry) i.next();
            O = (Occurance) me.getValue();
            
            System.out.print(me.getKey().toString()+" ");
            for(int k=0;k<O.kounter.length;k++)
            {
                System.out.print("   -->"+getNameOf(k)+" = "+O.kounter[k]);
            }
            System.out.println("     Total: "+O.total);
        }
    }
    
    Iterator valueIterator(TreeMap map) 
    {
        Set set = new TreeSet(new Comparator<Map.Entry<String, Occurance>>() {
            @Override
            public int compare(Map.Entry<String, Occurance> o1, Map.Entry<String, Occurance> o2) {
                //return  o.compareTo(o2.getValue()) < 0 ? 1 : -1;
                return o1.getValue().total<o2.getValue().total? 1:-1;
            }
        });
        set.addAll(map.entrySet());
        return set.iterator();
    }
    
}
