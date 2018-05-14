/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InitializeReadingFiles 
{
    
    static int counter=0;
    public InitializeReadingFiles()
    {
        
    }
    public Set<String> ReadFileSet(String path)
    {
        Set<String> st = new TreeSet<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str=null;
            
            while( (str=br.readLine() )!=null )
            {
                String words[] = str.split("\\s");
                for(int i=0;i<words.length;i++)
                    st.add(words[i]);
                
            }
            br.close();
            
        } catch (Exception ex) {
            Logger.getLogger(InitializeReadingFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return st;
    }
    
    public TreeMap<String,String> ReadFileMap(String path)
    {
        TreeMap<String,String> tm = new TreeMap<String, String>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str=null;
            
            while( (str=br.readLine() )!=null )
            {
                String words[] = str.split(" ");
                tm.put(words[0], words[1]);
            }
            br.close();
            
        } catch (Exception ex) {
            Logger.getLogger(InitializeReadingFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return tm;
    }
    
    public TreeMap<String,Integer> ReadQueryMap(String path, TreeMap<String,String> rootWord, Set<String> useless)
    {
        TreeMap<String,Integer> tm = new TreeMap<String,Integer>();
        Set<String> keyWords = new TreeSet<String>();
        //RawData.newsLocation = new TreeSet<String>();
        //RawData.totalQueryWords=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str=null;
            
            while( (str=br.readLine() )!=null )
            {
                String words[] = str.split("([[ ]|.|।|,|[+]|ঃ|/|<|>|?|!|-|‘|’|:|\\r|\\n|\\s|\\t|\\(|\\)|[a-zA-Z]|[০-৯]|\\d|-])+"); //এখানে সবচেয়ে Latest Regex টা লেখা হয়েছে :D 
                
                
                for(int i=0;i<words.length;i++)
                {
                    words[i] = words[i].trim();
                    String s=words[i];
                    
                    if(s.length()<2) continue;
                    
                    if(rootWord.get(s)!=null)
                        s = rootWord.get(s);
                    
                    if(useless.contains(s)) continue;
                   // System.out.println("Here: "+s);
                    
//                    if(RawData.rootWord.get(words[i])!=null && !RawData.useless.contains(words[i]))
//                    {
//                        keyWords.add(RawData.rootWord.get(words[i]));
//                    }
                    //RawData.totalQueryWords++;
                    keyWords.add(s);
                    if(tm.get(s)==null)     tm.put(s, 1);
                    else tm.put(s, tm.get(s)+1);
                    
                }
            }
            br.close();
            
            //new NewsCategorization(keyWords);
            
        } catch (Exception ex) {
            Logger.getLogger(InitializeReadingFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return tm;
    }
}
