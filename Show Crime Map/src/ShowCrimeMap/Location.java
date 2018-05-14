/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.awt.Point;

/**
 *
 * @author Rajib
 */
public class Location {
    public String name;
    public String parent, type;
    Point coOrdinate;

    public Location() {
        name = "";
        parent = "";
        type = "";
        coOrdinate = new Point(0, 0);
    }

    public Location(String name, String parent, String type, Point coOrdinate) {
        this();
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.coOrdinate = coOrdinate;
    }
    
    
    
}
