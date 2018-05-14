/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Extract_Location;

import java.awt.Point;

/**
 *
 * @author Rajib
 */
public class Location {
    int id;
    public String name;
    public String parent, type;
    Point coOrdinate;

    public Location() 
    {
        name = "";
        parent = "";
        type = "";
        coOrdinate = new Point(0, 0);
    }

    public Location(int id, String name, String parent, String type, Point coOrdinate) {
        this();
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.coOrdinate = coOrdinate;
    }
    
}
