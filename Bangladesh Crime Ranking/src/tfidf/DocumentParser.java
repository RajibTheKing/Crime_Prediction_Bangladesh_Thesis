/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Class to read documents
 *
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    private Vector<String>filenames = new Vector<String>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        int ii=0, kk=0;
        
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                
                System.out.println(ii++ +" th file name is "+f.getName());
                filenames.add(f.getName());
                
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    s = new String(s.getBytes(), "UTF-8");
                    
//                    System.out.println("-->"+s);
                    sb.append(s);
                }
//                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&\\s&&[^ঁ-ৱ]]", ":").split(":");   //to get individual terms
                String str = sb.toString().replaceAll("[[^ঁ-ৱ]&&\\W]", ":");
                str = str.replace("\\s", ":");
//                System.out.println(str);
                str = str.replaceAll(":+", ":");
//                System.out.println(str);

                String[] tokenizedTerms = str.split(":");   //to get individual terms
//                String[] tokenizedTerms = sb.toString().split("\\s");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
//                        System.out.println(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
                System.out.println("Total term in file  "+f.getName()+" is " + tokenizedTerms.length);
                kk+=tokenizedTerms.length;
            }
        }
        System.out.println("Total unique terms is : "+allTerms.size() +" in "+kk+" words ");

    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = i+1; j < tfidfDocsVector.size(); j++) {
                System.out.println("between " + filenames.elementAt(i) + " and " +  filenames.elementAt(j) + "  =  "
                                   + new CosineSimilarity().cosineSimilarity
                                       (
                                         tfidfDocsVector.get(i), 
                                         tfidfDocsVector.get(j)
                                       )*100+"%"
                                  );
            }
        }
    }
}
