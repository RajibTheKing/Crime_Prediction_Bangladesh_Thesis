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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class storeHotWord
{
    TreeMap<String, Integer> mp;
    TreeMap<String, Boolean>useless_map;
    TreeMap<String, Boolean>sp_map;
    storeHotWord()
    {
        mp = new TreeMap<String, Integer>();
        useless_map = new TreeMap<String, Boolean>();
        sp_map = new TreeMap<String, Boolean>();
        mp.clear();
        useless_map.clear();
        sp_map.clear();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("Resource/TemporarynewsData.txt"));
            BufferedReader read_useless = new BufferedReader(new FileReader("Resource/UselessWord.txt"));
            BufferedReader read_sport = new BufferedReader(new FileReader("Resource/entertainment.txt"));
            BufferedWriter write_useless = new BufferedWriter(new FileWriter("Resource/UselessWord.txt",true));
            BufferedWriter write_sport = new BufferedWriter(new FileWriter("Resource/entertainment.txt",true));
            
            useless_map=NewsCategorization.read_file(read_useless, useless_map);
            sp_map=NewsCategorization.read_file(read_sport, sp_map);
            String str="", lines="";
            while( (str=br.readLine())!=null )
            {
                lines+=str;
            }
            br.close();
            String words[] = lines.split("[ ,ঃ<>()।?!/-|]");
            for(int i=0;i<words.length;i++)
            {
                words[i] = words[i].trim();
                //System.out.println(words[i]);
                if(NewsCategorization.IsImportant(words[i]))
                {
                   Integer a = mp.get(words[i]);
                   if(a==null)
                   {
                       a=1;
                       mp.put(words[i], a);                    
                   }
                   else
                   {
                       mp.put(words[i], ++a);
                   }
                }                   
            }
            
            Set set = mp.entrySet();
            Iterator i = valueIterator(mp);
            int cnt = 0;
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                System.out.print(me.getKey() + "  ");
                System.out.println(me.getValue());
                String hel = me.getKey().toString();
                if(useless_map.get(hel)!=null || hel.length()<1 || hel==null)  continue;
                if(NewsCategorization.mp.get(hel)!=null && sp_map.get(NewsCategorization.mp.get(hel)) !=null) continue;
                
                int optionType = JOptionPane.YES_NO_CANCEL_OPTION; // YES+NO+CANCEL
                int res = JOptionPane.showConfirmDialog(null, hel, "Keep in Useless file?", optionType);
                
                if(res==2)  continue;
                if(res==0) //I want to keep it in UseLess file
                {
                     useless_map.put(me.getKey().toString(),true);
                     NewsCategorization.write_file(write_useless, me.getKey().toString());
                     continue;
                }
                
                if(NewsCategorization.rt.isAvailable(hel))
                {
                    if(++cnt>200) break;
                    NewsCategorization.write_file(write_sport, NewsCategorization.mp.get(hel));
                    continue;
                }
                String ss=null;
                if(hel.length()>0&&useless_map.get(hel)==null&&sp_map.get(hel)==null)
                    ss = JOptionPane.showInputDialog(null,me.getKey() +"?", me.getKey() );
                else
                    continue;
                
                 if(ss==null)
                 {
                    
                     if(useless_map.get(me.getKey().toString())==null && me.getKey().toString().length()>0)
                     {
                         useless_map.put(me.getKey().toString(),true);
                         NewsCategorization.write_file(write_useless, me.getKey().toString());
                     }
                 }
                 else
                 {
                     NewsCategorization.rt.InsertItem(hel, ss);
                     
                     if(sp_map.get(ss)==null)
                     {
                         if(++cnt>200) break;
                         sp_map.put(ss,true);
                         NewsCategorization.write_file(write_sport, ss);
                     }
                 }
            
            }
            
            write_useless.close();
            write_sport.close();
           
            //throw new Exception();
            
             
        } catch (Exception ex) {
            Logger.getLogger(storeHotWord.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }


    Iterator valueIterator(TreeMap map) 
    {
        Set set = new TreeSet(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return  o1.getValue().compareTo(o2.getValue()) < 0 ? 1 : -1;
            }
        });
        set.addAll(map.entrySet());
        return set.iterator();
    }
}
