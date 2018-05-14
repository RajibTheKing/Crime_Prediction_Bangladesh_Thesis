/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extract_Location;

import com.sun.org.apache.xml.internal.utils.Trie;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Rajib
 */
public class buildTree 
{
    Node Trie[] = new Node[600];
    
    public buildTree() 
    {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("newTable.xml"));
            String str="";
            int indx=0;
            while( (str=br.readLine())!=null)
            {
                String words[] = str.split("[\\s]+");
                indx++;
            }
        } catch (Exception ex) {
            Logger.getLogger(buildTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void InsertTrie()
    {
    }
    
    void Traverse_Tree()
    {  
    }
    
    
}

class Node 
{
    int id;
    int parent;
    String name;
    int next[] = new int[50];
    Point Co_Ordinate = new Point();
    Node()
    {
        for(int i=0;i<50;i++)
        {
            next[i]=-1;
        }
        
    }
}
