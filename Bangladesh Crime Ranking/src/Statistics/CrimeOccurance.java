/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import CrimeRanking.DatabaseConnection;
import Extract_Location.FindLocation;
import Extract_Location.Location;
import SimilarityMatching.SimilarityMatching;
import SimilarityMatching.TfIdf;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajib
 */
public class CrimeOccurance {

    TreeMap<String, Integer> Map;
    DatabaseConnection db;
    TreeMap<dataCount, Integer> Dt;

    public CrimeOccurance() {
        db = new DatabaseConnection();
        Dt = new TreeMap<>();
    }

    @SuppressWarnings("empty-statement")
    public void loadFromDb() {
        ResultSet rs;
        FindLocation fl = new FindLocation();
        try {
            rs = db.queryResult("select * from crawled_data   ORDER BY (id) asc ");
//              rs = db.queryResult("select * from test_data where news_id>5 ORDER BY (news_id) asc limit 10");
            int xxx = 0;
            while (rs.next()) {
                xxx++;
                String content = rs.getString("content");
                Vector<Location> Vl = fl.locations(content);
                String[] date = rs.getString("date").split("[ -]+");
                String month = date[1];
                String year = date[0];
                Set<String> s = new TreeSet<>();
                for (Location lc : Vl) {
                    if (lc.type.equals("district")) {
                        s.add(lc.name);
                    }
                    if (lc.type.equals("thana")) {
                        s.add(lc.parent);
                    }
                }
                dataCount dt = null;
                for (String loc : s) {
                    dt = new dataCount(loc, year, month, 1);
                    Integer x = Dt.get(dt);
                    if (x == null) {
                        x = 1;
                    } else {
                        x = x + 1;
                        //System.out.println("Found "+dt.toString()+" "+x);
                    }
                    dt.count = x;

                    if (dt.location.length() > 0) {
//                        if (Dt.get(dt) == null) {
//                            Dt.put(dt, dt.count);
//                        } else {
//                             Dt.put(dt, dt.count);
//                            System.out.println(dt.toString() + " <-- not found " + Dt.size());
//                        }
//                        System.out.println(dt.toString());
                        Dt.put(dt, dt.count);
                    }
                }
//                System.out.println(Dt.size());
            }
//            System.out.println("Opps"+xxx);

            System.out.println(Dt.size());
            System.out.println("Location Name \t Year\t Month\t count");
            Vector<String> MONTH = new Vector<String>();

            MONTH.add("Jan");
            MONTH.add("Feb");
            MONTH.add("Mar");
            MONTH.add("Apr");
            MONTH.add("May");
            MONTH.add("Jun");
            MONTH.add("Jul");
            MONTH.add("Aug");
            MONTH.add("Sep");
            MONTH.add("Oct");
            MONTH.add("Nov");
            MONTH.add("Dec");
            for (int i = 1; i < 10; i++) {
                MONTH.add("0" + i);
            }
            MONTH.add("10");
            MONTH.add("11");
            MONTH.add("12");

            Vector<String> YEAR = new Vector<>();
            for (int i = 2011; i < 2013; i++) {
                YEAR.add("" + i);
            }
            xxx = 0;

//            System.out.println("location ");
//            for(String yr : YEAR)
//            {
//                for(String mnth: MONTH)
//                {
//                     for (dataCount dc : Dt.keySet())
//                     {
//                         
//                     }
//                }
//            }

            Set<String> s = new TreeSet<>();

            for (dataCount dc : Dt.keySet()) {
                s.add(dc.location);
            }
            System.out.print("Location\t");
            for (int i = 0; i < 12; i++) {
                System.out.print("2011\t");
            }
            for (int i = 0; i < 12; i++) {
                System.out.print("2012\t");
            }
            System.out.println("");
            
            System.out.print("Month\t");
            for (int i = 0; i < 12; i++) {
                System.out.print(MONTH.get(i) + "\t");
            }
            for (int i = 0; i < 12; i++) {
                System.out.print(MONTH.get(i) + "\t");
            }
            System.out.println("");

            for (String str : s) {
                System.out.print(str + "\t");
                int sum=0;
                for (int yr = 1; yr < 3; yr++) {
                    String year = "201"+yr;
                    for (int mn = 1; mn < 13; mn++) {
                        String mnth = mn<10?"0"+mn:""+mn; 
                        boolean flag = false;
                        for (dataCount dc : Dt.keySet()) {
                            if (dc.location.equals(str)) {
                                if(dc.year.equals(year))
                                {
                                    if(dc.month.equals(mnth))
                                    {
                                        
                                        xxx += Dt.get(dc);
//                                        System.out.print(Dt.get(dc)+"("+dc.year+","+dc.month+ ")\t");
                                        System.out.print(Dt.get(dc)+"\t");
                                        flag = true;
                                        sum+=Dt.get(dc);
                                        break;
                                    }
                                }
                                //                    System.out.print(dc.location + " \t\t " + dc.year + "\t" + dc.month + "\t" + dc.count+"\t"+Dt.get(dc));
                                //                System.out.println(dc.toString()+" "+Dt.get(dc));
                            }
                        }
                        if(!flag)
                        {
                            System.out.print("0\t");
                        }
                    }
                }

                //                System.out.println(dc.location + " " + dc.month + " " + dc.year + " " + Dt.get(dc));

                System.out.println("\t"+sum);
            }
            System.out.println("Total Crime Counted: " + xxx);
        } catch (SQLException ex) {
            Logger.getLogger(SimilarityMatching.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        CrimeOccurance c = new CrimeOccurance();
        c.loadFromDb();
    }
}

class CrimeData {

    String news;
    Vector<dataCount> loc;
    String mionth;
    String year;
    String date;

    public CrimeData() {
    }
}

class dataCount implements Comparable<Object> {

    String location;
    String year;
    String month;
    int count;

    public dataCount() {
        location = "";
        year = "";
        month = "";
        count = 0;
    }

    public dataCount(String location, String year, String month, int count) {
        // this();
        this.location = location;
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public String toString() {
        return "Location " + location + " year " + year + " month " + month + " count " + count;
    }

    @Override
    public int compareTo(Object o) {
        //FoodItems temp = (FoodItems) o;
        dataCount dt = (dataCount) o;
        if (this.location.equals(dt.location) && this.year.equals(dt.year) && this.month.equals(dt.month)) {
            return 0;
        } else if (location.equals(dt.location)) {
            if (!year.equals(dt.year)) {
                return year.compareTo(dt.year);
            }
            if (!month.equals(dt.month)) {
                return month.compareTo(dt.month);
            }
        } else {
            return location.compareTo(dt.location);
        }
        return location.compareTo(dt.location);
    }

    public boolean equals(Object o) {
        dataCount dt = (dataCount) o;
        if (this.location.equals(dt.location) && this.year.equals(dt.year) && this.month.equals(dt.month)) {
            return true;
        }
        return false;

    }
}