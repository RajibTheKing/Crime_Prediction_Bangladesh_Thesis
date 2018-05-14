/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajib
 */
public class LoadFromDatabase {

    Vector<Location> location;
    DatabaseConnection db;

    public LoadFromDatabase() {
        location = new Vector<>();
        db = new DatabaseConnection();
    }

    public void readData() {

        String str = "select * from locationdata";
        try {
            Location lc;
            ResultSet rs = db.queryResult(str);
            int xx = 0;
            while (rs.next()) {
                int location_id = rs.getInt("location_id");
                String name = rs.getString("name");
                String parent = rs.getString("parent");
                String type = rs.getString("type");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                Point p = new Point(x, y);
                lc = new Location(name, parent, type, p);
                //location.add(lc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Point FindCordinate(String name, String parent) {

        Point p = new Point(-1, -1);
        for (Location lc : location) {
            if(lc.name.equals(name) && lc.parent.equals(parent))
            {
                p.setLocation(lc.coOrdinate);
                break;
            }
        }
        return p;
    }
}
