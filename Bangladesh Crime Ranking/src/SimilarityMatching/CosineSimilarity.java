/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityMatching;

import java.util.TreeMap;

/**
 *
 * @author Rajib
 */
public class CosineSimilarity {

    public CosineSimilarity() {
    }
    
    public Double  cosineAngle( TreeMap<String, Double> doc1,  TreeMap<String, Double> doc2)
    {
         Double AxBx, A, B, angle, x, y;
         
         AxBx=0.0;
         A=0.0;
         B=0.0;
         for(String str: doc1.keySet())
         {
             x = doc1.get(str);
             y = doc2.get(str);
             if(y==null) y=0.0;
             AxBx +=x*y;
             A += x*x;
         }
         for(Double val: doc2.values())
         {
             B += val*val;
         }
         angle = AxBx/((Math.sqrt(A)*Math.sqrt(B)));
         return angle*100;
    }
    
}
