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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

    
public class ClusteringNews 
{
    Set<String> useless, crime, sports, entertainment, currentNews;
    double probability_crime, probability_sports, probability_entertainment;
    public ClusteringNews() 
    {
            useless = new TreeSet<String>();
            crime = new TreeSet<String>();
            sports = new TreeSet<String>();
            entertainment = new TreeSet<String>();
            currentNews = new TreeSet<String>();
            
            useless = insertIntoSet("UselessWord.txt"); 
            crime = insertIntoSet("crime.txt");
            sports = insertIntoSet("sports.txt");
            entertainment = insertIntoSet("entertainment.txt");
            System.out.println(sports.size());
            
            //UpdateFile(entertainment, "faltuWord.txt");
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("Resource/TemporarynewsData.txt"));
                String str="", lines="";
                while( (str=br.readLine())!=null )
                {
                    lines+=str;
                }
                br.close();
                String words[]=lines.split("[ ,ঃ<>()।?!/-|]");
//                probability_crime = Calculate_Jaccard_Similarity(words, crime);
//                probability_sports = Calculate_Jaccard_Similarity(words, sports);
//                probability_entertainment = Calculate_Jaccard_Similarity(words, entertainment);
                
                int category=-1;
                Scanner scan = new Scanner(System.in);
                //System.out.println("Enter Category: ");
                //category = scan.nextInt();
                
                category= Calculate_Probability(words, crime, sports, entertainment);
                
                if(category==1) AddTopWord(words, "crime.txt", crime, useless);
                if(category==2) AddTopWord(words, "sports.txt", sports, useless);
                if(category==3) AddTopWord(words, "entertainment.txt", entertainment, useless);
                if(category==-1) System.out.println("Sorry No Category Found");
                    
                
      
                
            }catch(Exception e){
                  System.out.println(e.getMessage());
            }
            
                
                
            

    
    }
    private int Calculate_Probability(String[] words, Set<String>P, Set<String>Q, Set<String>R)
    {
        double ans1, ans2, ans3;
        Set<String> T = new TreeSet<String>();
        for(int i=0;i<words.length;i++)
        {
            words[i] = words[i].trim();
            String s=words[i];
            if (NewsCategorization.rt.isAvailable(s) && useless.contains(s) == false) 
            {
                String now = NewsCategorization.rt.getItem(s);
                T.add(now);
            }
        }
        
        int common1 = 0, common2=0, common3=0;
        for(String s:T)
        {
            if(P.contains(s)) common1++; //অপরাধ
            if(Q.contains(s)) common2++; //খেলা
            if(R.contains(s)) common3++; //বিনোদন
                
        }
        Vector<Integer> V = new Vector<Integer>();
        V.clear();
        
        int Total = common1+common2+common3;
        System.out.println(Total);
        System.out.println(common1+" "+common2+" "+common3);
        ans1 = (common1*1.0)/(Total*1.0)*100.0;
        System.out.println("অপরাধঃ "+ ans1+" %");
        ans2 = (common2*1.0)/(Total*1.0)*100.0;
        System.out.println("খেলাঃ "+ ans2+" %");
        ans3 = (common3*1.0)/(Total*1.0)*100.0;
        System.out.println("বিনোদনঃ "+ ans3+" %");
        
        if(ans1>50.0) return 1;
        if(ans2>50.0) return 2;
        if(ans3>50.0) return 3;
        
        return -1;
        
        
    }
    private double Calculate_Jaccard_Similarity(String[] words, Set<String> A) 
    {
        Set<String> B = new TreeSet<String>();
        for (int i = 0; i < words.length; i++) 
        {
            words[i] = words[i].trim();
            String s = words[i];

            if (NewsCategorization.rt.isAvailable(s) && useless.contains(s) == false) 
            {
                String now = NewsCategorization.rt.getItem(s);
                B.add(now);
            }
        }
        // J(A,B) A_intersection_B/A_Uniion_B
        // A_Union_B = n(A) + n(B) - A_intersection_B
        
        int A_intersection_B = 0;
        for(String s:B)
        {
            if(A.contains(s))
                A_intersection_B++;
        }
        
        int A_Union_B = A.size() + B.size() - A_intersection_B;
        System.out.println("n(A): "+A.size());   
        System.out.println("n(B): "+B.size());
        System.out.println("AUB: "+A_Union_B);
        System.out.println("AIB: "+A_intersection_B);
        
        double JaccardSimilarity = A_intersection_B*1.0 / A_Union_B*1.0;
        System.out.println("Jaccard Similarity: "+JaccardSimilarity);
        
        return JaccardSimilarity*1000;
    }
    private Set<String> insertIntoSet(String  fileName)
    {
        Set<String> st = new TreeSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Resource/"+fileName));
            String str="";
            while( (str=br.readLine())!=null )
            {
                st.add(str);
            }
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ClusteringNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return st;
    }
    private void UpdateFile(Set<String> set, String fileName)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Resource/"+fileName));
            String lines="";
            for(String s:set)
            {
                lines+=s;
                lines+="\r\n";
            }
            bw.write(lines);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(ClusteringNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void AddTopWord(String[] words, String fileName, Set<String> A, Set<String> useless) 
    {
        try {
            BufferedWriter addW = new BufferedWriter(new FileWriter("Resource/"+fileName, true));
            TreeMap<String, Integer> treemp = new TreeMap<String, Integer>();
            for(int i=0;i<words.length;i++)
            {
                words[i] = words[i].trim();
                if(NewsCategorization.rt.isAvailable(words[i]))
                {
                    Integer a = treemp.get(words[i]);
                    if(a==null)
                    {
                        a=1;
                        treemp.put(words[i], a);
                    }
                    else
                        treemp.put(words[i], ++a);
                        
                }
            }
            
            Set set = treemp.entrySet();
            Iterator i = valueIterator(treemp);
            int kounter=0;
            while(i.hasNext())
            {
                Map.Entry me = (Map.Entry) i.next();
                String hl = me.getKey().toString();
                if(NewsCategorization.rt.isAvailable(hl) && useless.contains(hl)==false && A.contains(NewsCategorization.rt.getItem(hl))==false)
                {
                    
                    A.add(NewsCategorization.rt.getItem(hl));
                    addW.append("\r\n");
                    addW.append(NewsCategorization.rt.getItem(hl));
                    System.out.println(NewsCategorization.rt.getItem(hl)+" -- is Added to "+fileName);
                    
                }
                if(kounter++==30) break;
                
            }
            addW.flush();
            addW.close();
                    
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(ClusteringNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    Iterator valueIterator(TreeMap map) 
    {
        Set set = new TreeSet(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return  o1.getValue().compareTo(o2.getValue()) < 0 ? 1 : -1;
            }
        });
        set.addAll(map.entrySet());
        return set.iterator();
    }
    
}
