/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CrimeRanking;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Rajib
 */
public class RawData 
{
    TreeMap<String, String> rootWord;
    TreeMap<String, Integer> query;
    Set<String> useless,sports,crime,entertainment, newNews, location;
    String date;
    int totalQueryWords;
    RawData()
    {
        InitializeReadingFiles IR = new InitializeReadingFiles();
        rootWord = IR.ReadFileMap("Resource/RootWord.txt");
        useless = IR.ReadFileSet("Resource/UseLessWord.txt");
        
//        crime = IR.ReadFileSet("Resource/crime.txt");
//        sports = IR.ReadFileSet("Resource/sports.txt");
//        entertainment = IR.ReadFileSet("Resource/entertainment.txt");
//        location = IR.ReadFileSet("Resource/newTable.xml");
//        newNews = IR.ReadFileSet("Resource/temp.txt");
//        query = IR.ReadQueryMap("Resource/temp.txt", rootWord, useless);
        
        
        
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
