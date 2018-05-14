/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

/**
 *
 * @author Rajib
 */
public class Occurance 
{
    int kounter[] = new int[5];
    int total;
    public Occurance() 
    {
        for(int i=0;i<kounter.length;i++)
            kounter[i]=0;
        total = 0;
    }
    
    public int getTotal()
    {
        int sum=0;
        for(int i=0;i<kounter.length;i++)
            sum+=kounter[i];
        return sum;
    }
    
    public void AddToCategory(String s)
    {
        kounter[getIndexOf(s)]++;
        total++;
    }
    
    private int getIndexOf(String s)
    {
        if(s.equals("crime")) return 0;
        if(s.equals("sports")) return 1;
        if(s.equals("entertainment")) return 2;
        if(s.equals("technology")) return 3;
        return 4;
    }
    
}
