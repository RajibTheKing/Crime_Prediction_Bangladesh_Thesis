/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityMatching;

import CrimeRanking.DatabaseConnection;
import CrimeRanking.File_Update;
import Extract_Location.FindLocation;
import Extract_Location.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajib
 */
public class SimilarityMatching {

    String filePath;
    static TreeMap<String, Double> globalMap;
    Vector<TreeMap<String, Double>> V;
    Vector<String> Vs;
    Vector<Integer>VI;
    DatabaseConnection db;
    Vector<NewsType> similar;
    File_Update fd ;
    public SimilarityMatching() {
        filePath = System.getProperty("user.dir");
        globalMap = new TreeMap<>();
        globalMap.put("#TotalTerm", 1.0);
        V = new Vector<>();
        Vs = new Vector<>();
        VI = new Vector<>();
        db = new DatabaseConnection();
        similar = new Vector<>();
        fd = new File_Update();
    }

    public SimilarityMatching(String filePath) {
        this();
        this.filePath = filePath;
    }

    public void loadFromDb() {
        ResultSet rs;
        try {
            rs = db.queryResult("select * from newsdata where news_id=15 or news_id=17  ORDER BY (news_id) asc limit 10");
//              rs = db.queryResult("select * from test_data where news_id>5 ORDER BY (news_id) asc limit 10");
            
            while (rs.next()) {
                String str = rs.getString("content");
                TreeMap<String, Double> tf = new TreeMap<>();
                tf = new TfIdf().Tfcalculator(str.split("([[ ]|।|,|ঃ|<|>|?|!|-|‘|’|:|\\r|\\n|\\s|\\t|0-9A-z])+"));
                V.add(tf);
                Vs.add(str);
                VI.add(rs.getInt("news_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SimilarityMatching.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculate() {

        //tf
        for (int i = 0; i < V.size(); i++) {
            TreeMap<String, Double> tf = new TfIdf().normalize(V.get(i), V.get(i).get("#TotalTerm"));
            V.remove(i);
            V.add(i, tf);
        }
        //idf
        for (int i = 0; i < V.size(); i++) {
            System.out.println(V.get(i).size());
            TreeMap<String, Double> tf = new TfIdf().IDF(globalMap, V.get(i), (double) V.size());
            V.remove(i);
            V.add(i, tf);
        }
        //cosine similarity

        for (int i = 0; i < V.size(); i++) {
            for (int j = i+1; j < V.size(); j++) {
                System.out.println("doc "+VI.get(i)+" -->"+Vs.get(i));
                System.out.println("doc "+VI.get(j)+" -->"+Vs.get(j));
//                System.out.println("doc "+i+" -->"+VI.get(i));
//                System.out.println("doc "+j+" -->"+VI.get(j));
                Double val = new CosineSimilarity().cosineAngle(V.get(i), V.get(j));
                similar.add(new NewsType(val, VI.get(i), VI.get(j)));
                System.out.println("Similarity between doc "+VI.get(i)+" doc "+VI.get(j)+ " is "+val);
                fd.File_Append("Resource/similarity.txt", "Similarity between doc "+VI.get(i)+" doc "+VI.get(j)+ " is "+val);
            }
            if(i%100==0) System.out.println("Similarity between doc "+i);
        }
        System.out.println("done");
        Comparator<NewsType> comparator = new NewsTypeComparator();
        Collections.sort(similar, comparator);   
        NewsType nb;
        System.out.println(similar.size());
        for(int i=0; i<similar.size(); i++){
            nb = similar.get(i);
//            System.out.println("similarity between " +nb.doc1+" "+nb.doc2+" is "+nb.similarity);
            fd.File_Append("Resource/sorted.txt", "similarity between " +nb.doc1+" "+nb.doc2+" is "+nb.similarity);
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        SimilarityMatching sm = new SimilarityMatching();
        sm.loadFromDb();
        sm.calculate();
        FindLocation loc = new FindLocation();
        
        for(String str: sm.Vs)
        {
            System.out.println("Location Found for string : "+str+" \n is :");
            Vector<Location> Vs = loc.locations(str);
            if(Vs.size()>0)
            for(Location lc : Vs)
            {
                System.out.println(lc.name+" "+lc.parent+" "+lc.type);
            }
        }
        
    }
}

class NewsType{

    Double similarity;
    int doc1, doc2;
    public NewsType() {
        doc1=0;
        doc2=0;
        similarity=0.0;
    }

    public NewsType(Double similarity, int doc1, int doc2) {
        this.similarity = similarity;
        this.doc1 = doc1;
        this.doc2 = doc2;
    }
}

class NewsTypeComparator implements Comparator<NewsType>
{
    @Override
    public int compare(NewsType o1, NewsType o2) {
        return o1.similarity.compareTo(o2.similarity);
    }
}