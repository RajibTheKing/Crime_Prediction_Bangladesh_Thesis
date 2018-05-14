
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main 
{

    Set<String> keyword;
    Set<String> sentence;
    TreeMap<String, Data> mp;
    
    Main()
    {
        keyword = new TreeSet<String>();
        sentence = new TreeSet<String>();
         mp = new TreeMap<String, Data>();
        InitiateResult();
    }
    public static void main(String[] args) 
    {
        // TODO code application logic here
        
        Main obj = new Main();
        obj.PrintResult();
        
    }

    private void InitiateResult() 
    {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("keyword.txt"));
            String s = "";
            while ((s = br.readLine()) != null) {
                keyword.add(s);
            }
            br.close();

            br = new BufferedReader(new FileReader("sentence.txt"));

            while ((s = br.readLine()) != null) {
                sentence.add(s);
            }
            br.close();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(sentence.size());
        for (String st : sentence) 
        {
            String words[] = st.split(" ");
            if (words.length < 3)
            {
                continue;
            }
            for (String kw : keyword) 
            {
                String ff = "";

                if (st.startsWith(kw)) 
                {
                    if (mp.containsKey(kw)) 
                    {
                        mp.get(kw).AddNewValue(words[1] + " " + words[2], true);
                    } 
                    else 
                    {
                        mp.put(kw, new Data());
                        mp.get(kw).AddNewValue(words[1] + " " + words[2], true);

                    }
                    //System.out.println("Keyword= "+kw+ " Previous word: "+words[1]+" "+words[2]+" Next Word: ");
                } 
                else if (st.endsWith(kw)) 
                {
                    if (mp.containsKey(kw)) 
                    {
                        mp.get(kw).AddNewValue(words[0] + " " + words[1], false);
                    } 
                    else 
                    {
                        mp.put(kw, new Data());
                        mp.get(kw).AddNewValue(words[0] + " " + words[1], false);

                    }
                    //System.out.println("Keyword= "+kw+" Previous word: "+"Next word: "+words[0]+" "+words[1]);
                }
                else if (st.contains(kw)) 
                {
                    //System.out.println("Keyword= " + kw + " Previous word: " + words[0] + " Next Word: " + words[2]);
                }


            }
        }
    }
    
    public void PrintResult()
    {
         for (Map.Entry<String, Data> entry : mp.entrySet()) 
        {
            String key = entry.getKey();
            Data value = entry.getValue();
            System.out.println(key+" : ");
            System.out.print("Prev:  ");
            
            boolean flag=true;
            for(String ss: value.prev)
            {
                if(flag)
                {
                    System.out.print(ss);
                    flag=false;
                }
                else
                {
                    System.out.print(", "+ss);
                }
            }
            System.out.println("");
            System.out.print("Next:  ");
            flag=true;
            for(String ss: value.next)
            {
                 if(flag)
                {
                    System.out.print(ss);
                    flag=false;
                }
                else
                {
                    System.out.print(", "+ss);
                }
            }
            System.out.println("");
        }
    }
}
