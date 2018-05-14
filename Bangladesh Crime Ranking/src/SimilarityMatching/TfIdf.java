/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityMatching;

import CrimeRanking.InitializeReadingFiles;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Rajib
 */
public class TfIdf {
    public TfIdf(){
    }
    
    public  TreeMap<String, Double> Tfcalculator(String[] str)
    {
        TreeMap <String, Double> Tf = new TreeMap<>();
        Set<String> useless = new InitializeReadingFiles().ReadFileSet("Resource/UseLessWord.txt");
        TreeMap<String, String> rootword = new InitializeReadingFiles().ReadFileMap("Resource/RootWord.txt");
        
        Double totalTerm=0.0;
        for(String s: str)
        {
            if(s.length()<2) continue;
            if(useless.contains(s)) continue;
            String ss = rootword.get(s);
            if(ss!=null) s = ss;
            
//            System.out.println(ss+" <--> "+s);
            
            Double x = Tf.get(s);
            if(x==null) x=0.0;
            Tf.put(s, x+1);
            x = SimilarityMatching.globalMap.get(s);
           if(x==null) x=0.0;
            SimilarityMatching.globalMap.put(s, x+1);
            totalTerm++;
        }
        Tf.put("#TotalTerm", totalTerm);
        return  Tf;
    }
    
    public TreeMap<String, Double> normalize( TreeMap<String, Double> tf, Double term)
    {
        
        double x = term;
        for(String str: tf.keySet())
        {
            Double val = tf.get(str);
            tf.put(str, val/x);
        }
        return tf;
    }
    
    public  TreeMap<String, Double> IDF( TreeMap<String, Double> globalMap,  TreeMap<String, Double> tf, Double totalDoc)
    {
        
        Double idf = 0.0;
        
      //  System.out.println("global map "+globalMap.size()+" tf "+tf.size()+" double "+totalDoc);
        
        for(String str: tf.keySet())
        {
            Double x  = globalMap.get(str);
//            System.out.println("asdfad -> "+str+" "+globalMap.get(str));
            if(str!=null)
            tf.put(str, globalMap.get(str)/totalDoc);
            else {
                System.out.println("--> not found "+str);
            }
        }
        return tf;
    }
    
    public  TreeMap<String, Double> TFIDF( TreeMap<String, Double> tf,  TreeMap<String, Double> idf)
    {
        for(String str: tf.keySet())
        {
            tf.put(str, tf.get(str)/idf.get(str));
        }
        return tf;
    }
}
