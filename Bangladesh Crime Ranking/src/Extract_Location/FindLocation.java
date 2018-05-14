/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extract_Location;

import CrimeRanking.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FindLocation {

    Vector<Location> loc;
    
    DatabaseConnection db;
    LoadFromDatabase load;
    int k=0;

    public FindLocation() 
    {
        load = new LoadFromDatabase();
        load.readData();
        
        loc = load.location;
    //    System.out.println("location : "+loc.size());
        db = new DatabaseConnection();
        
       // UpdateLocation();
        
    }
    private void UpdateLocation()
    {
        
        try {
            ResultSet rs = db.queryResult("select * from maindata");
            String content, category, location_id;
            int id;
            while(rs.next())
            {
                id = rs.getInt("news_id");
                content = rs.getString("content");
                category=rs.getString("category");
                location_id = rs.getString("location_id");
                
//                System.out.println("-->crime = "+category+" "+" location "+location_id);
                if(category.equals("crime") && location_id.equals("0"))
                {
//                    System.out.println("----------->"+location_id);
                    Vector<Location> now = locations(content);
                    if(now==null)
                    {
                        db.queryInsert("UPDATE  maindata set location_id='0' where news_id="+id);
                    }
                    else
                    {
                        String sss="";
                        boolean flag=true;
                        for(Location c:now)
                        {
                            if(flag)
                            {
                                sss+=c.id; 
                                flag=false;
                            }
                            else
                            {
                                sss = sss+","+c.id;
                            }
                        }
                        
                        db.queryInsert("UPDATE  maindata set location_id='"+sss +"' where news_id="+id);
                    }
                }
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(FindLocation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    String getParentType(String s)
    {
        if(s=="thana") return "district";
        if(s=="district") return "division";
        if(s=="division") return "country";
        return null;
    }
    public void PrintVector(Vector<Location> now)
    {
        System.out.println("Vector Size: "+now.size());
        for(Location c: now)
        {
            System.out.println(c.id+", "+c.name+", "+c.type+", "+c.type+", "+c.coOrdinate.x+", "+c.coOrdinate.y);
        }
    }
    public Vector<Location> locations(String doc) {
        Vector<Location> Vc = new  Vector<Location>();
        
        String words[] = doc.split("([[ ]|।|,|ঃ|<|>|?|!|-|‘|’|:|\\r|\\n|\\s|\\t])+");
        
        for (String str : words) 
        {
//            System.out.println(str);
            for(Location ptr: loc)
            {
                if(str.contains(ptr.name))
                {
                    Vc.add(ptr);
//                    Vc.add(names);
                }
            }
        }
        
        Vector<Location> now = new Vector<Location>();
       // PrintVector(Vc);
        
        for(Location c : Vc)
        {
            
            if(c.type.equals("thana"))
            {
                for(Location p:Vc)
                {
                    if(p.type.equals("district") && p.name.equals(c.parent) && !isAvailAble(now, c))
                        now.add(c);
                }
            }
        }
        if(now.size()>0) return now;
        //যদি থানার Perspective জেলা না পাওয়া যায় এবং শুধু একটাই  থানার নাম থাকে তাহলে ওই থানা রিটার্ন করে ফেলব... 
        
         for(Location c : Vc)
        {
            
            if(c.type.equals("thana") && !isAvailAble(now, c))
            {
               now.add(c);
            }
        }
         
        if(now.size()==1) return now;
        
        
        //যদি থানা না পায় তাহলে কি করব............ তাহলে যদি একটাই জেলা থাকে , আমি ওই জেলাটা পাঠিয়ে দিব...
        // অন্যথায় বলে দিব কোন জায়গা পাওয়া যায় নাই... 
        
        for(Location c: Vc)
        {
            if(c.type.equals("district") && !isAvailAble(now, c))
            {
                now.add(c);
            }
        }
        
      //  System.out.println("Now Number of District found: "+now.size());
        
        if(now.size()==1)
            return now;
        else
            return new Vector<>(0);
    }
    public boolean isAvailAble(Vector<Location> now, Location c)
    {
        for(Location i : now)
        {
            if(i.name.equals(c.name) && i.type.equals(c.type))
                return true;
        }
        return false;
    }
}
