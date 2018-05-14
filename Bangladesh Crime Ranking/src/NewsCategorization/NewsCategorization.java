package NewsCategorization;


import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
public class NewsCategorization 
{
    public static  TreeMap<String, String> mp = new TreeMap<>();
    public static SearchRoot rt;
    
    public static void main(String args[])
    {
        
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        rt = new SearchRoot();
        
        IndexingRootWord();
        //new storeHotWord();
      //new ClusteringNews();
        
        
    }
    
    public static void write_file(BufferedWriter wr, String key) throws IOException
    {
        wr.append("\r\n");
        wr.append(key);
        wr.flush();
        System.out.println("Added: "+key);
    }
    
    public static TreeMap read_file(BufferedReader br, TreeMap<String,Boolean> mp) throws IOException
    {
        System.out.println("Entering read file");
        String str = "";
        while((str=br.readLine())!=null)
        {
            System.out.println("loading "+str);
            mp.put(str, true);
        }
         br.close();
         
         return mp;
    }
     
    public static void IndexingRootWord() 
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Resource/temp.txt"));
            TreeMap<String, Boolean> useless = new TreeMap<String,  Boolean> ();
            BufferedReader uselessReader = new BufferedReader(new FileReader("Resource/UselessWord.txt"));
            useless = read_file(uselessReader, useless);
            
            BufferedWriter uselessWriter = new BufferedWriter(new FileWriter("Resource/UselessWord.txt", true));
            
            String str="";
            String data="";
            while( (str=br.readLine() ) != null )
            {
                System.out.println(str);
                data+=str;
            }
            br.close();
            
            //String words[] = data.split("([ ,ঃ<>()?!/-।])+");
            //String words[] = data.split("।");
            String words[] = data.split("([[ ]|।|,|ঃ|<|>|?|!|-|‘|’|:|\\r|\\n|\\s|\\t|[a-zA-Z0-9]])+");
            for(int i=0;i<words.length;i++)
            {
                words[i] = words[i].trim();
                //System.out.println(words[i]);
                if(!rt.isAvailable(words[i]) && !useless.containsKey(words[i]) && words[i].length()>1 && IsImportant(words[i]))
                {
                    
                    String s=words[i];
                    int optionType = JOptionPane.YES_NO_CANCEL_OPTION; // YES+NO+CANCEL
                    int res = JOptionPane.showConfirmDialog(null, s, "Keep in Useless file?", optionType);
                    
                    if(res==2) continue;
                    if(res==0)
                    {
                        useless.put(s, true);
                        write_file(uselessWriter, s);
                        continue;
                    }
                    if(res==1)
                    {
                        String ss = JOptionPane.showInputDialog(null, words[i]+"?", words[i]);
                    
                        if(ss==null)
                        {
                            System.out.println("Cancel");
                            
                        }
                        else
                        {
                            rt.InsertItem(words[i], ss);
                           // System.out.println("OK");
                        }
                        
                    }
                }
                    
            }
            uselessWriter.close();
            
            System.out.println("Indexing Completed for Temporary_NewsData");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }      
    }

    public static boolean IsImportant(String s) 
    {
        for(int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            
            if(s.charAt(i)>='a' && s.charAt(i)<='z') return false;
            if(s.charAt(i)>='A' && s.charAt(i)<='Z') return false;
            if(s.charAt(i)>='0' && s.charAt(i)<='9') return false;
            if(s.charAt(i)>='০' && s.charAt(i)<='৯') return false;
        }
        return true;
    }
    
   
}

