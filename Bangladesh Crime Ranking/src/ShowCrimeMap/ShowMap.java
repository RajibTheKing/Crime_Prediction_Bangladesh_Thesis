/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;
import CrimeRanking.DatabaseConnection;
import Extract_Location.Location;
import java.awt.*;  
import java.awt.event.*;  
import java.awt.geom.*;  
import java.awt.image.BufferedImage;  
import java.io.*;  
import java.net.*;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;  

import javax.swing.*;  
import javax.swing.event.*;  
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
   
   

public class ShowMap extends JFrame implements MouseInputListener
{
    private DatabaseConnection db;
    private JButton addPoint = new JButton("Add New Point");
    public static TreeMap<String, Integer> kounter = new TreeMap<String, Integer>();
    public static  Vector<Point> points;
    private ImageZoom zoom;
    private ImagePanel panel;
    JScrollPane ScrollPane;
    ResultSet locData;
    public ShowMap()
    {
        points  = new Vector<Point>();
        db  = new DatabaseConnection();
        try {
            locData = db.queryResult("select * from locationdata");
        } catch (SQLException ex) {
            Logger.getLogger(ShowMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UpdateVectorPoints("select * from predictiontable where EXTRACT(YEAR FROM date)>=2004");
        
        //UpdateVectorPoints("select * from newsdata");
        
        panel = new ImagePanel();  
        zoom = new ImageZoom(panel);  
        ScrollPane = new JScrollPane(panel);
        //JFrame f = new JFrame();  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        getContentPane().add(zoom.getUIPanel(), "North");  
        getContentPane().add(ScrollPane);  
        setSize(1000,700);  
        setLocationRelativeTo(null);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        panel.add(addPoint);
        panel.addMouseListener(this);
        
        addPoint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try{
                String s = JOptionPane.showInputDialog("Enter New Point: ");
                String ss[]=s.split(",");
                int x = Integer.parseInt(ss[0]);
                int y = Integer.parseInt(ss[1]);
                points.add(new Point(x, y));
                panel.repaint();
                panel.repaint();
                
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
                    
                
                
            }
        });
        
    }
    private Point GetPointByID(String s) throws SQLException
    {
        
        System.out.println("Location id = "+s);
        ResultSet rs = locData;
        while(rs.next())
        {
            String ll = rs.getString("location_id");
            if(ll.equals(s))
            {
                int x, y;
                x = rs.getInt("x");
                y = rs.getInt("y");
                rs.beforeFirst();
                return new Point(x,y);
            }
        }
        rs.beforeFirst();
        return new Point(0,0);
        
    }
    void UpdateVectorPoints(String query)
    {
        
        try {
            ResultSet rs =  db.queryResult(query);
            String content, category, location_id, coOrdinates;
            
            int id;
            while(rs.next())
            {
                
                
                //category=rs.getString("category");
                location_id = rs.getString("location_id");
                
                //if(category.equals("crime"))
                {
                    String words[] = location_id.split(",");
                    for(String s:words)
                    {
                        if(s.equals("0")) continue;
                        Point pp = GetPointByID(s);
                        points.add(pp);
                        Integer I;
                        if(kounter.containsKey(pp.x+" "+pp.y))
                        {
                            I = kounter.get(pp.x+" "+pp.y);
                            I++;
                            kounter.put(pp.x+" "+pp.y, I);
                        }
                        else
                        {
                            kounter.put(pp.x+" "+pp.y, new Integer(1));
                        }
                    }
                }
                
               
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ShowMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void Add_New_Point(Graphics g, int x, int y)
    {
        //Random rand = new Random();
        
        g.setColor(Color.red);
        g.fillRect(x, y, 10, 10);
        //repaint();
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("MouseClicked: "+e.getX()+","+e.getY());
        JOptionPane.showMessageDialog(null, "Mouse Clicked: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("MousePressed: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("MouseReleased: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("MouseEntered: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("MouseExited: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("MouseDragged: "+e.getX()+","+e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("MouseMoved: "+e.getX()+","+e.getY());
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}  
class ImagePanel extends JPanel  
{  
    BufferedImage image;  
    double scale;  
   
    public ImagePanel()  
    {  
        loadImage();  
        scale = 1.0;  
        setBackground(Color.black);  
    }  
   
    protected void paintComponent(Graphics g)  
    {  
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
        int w = getWidth();  
        int h = getHeight();  
        int imageWidth = image.getWidth();  
        int imageHeight = image.getHeight();  
        double x = (w - scale * imageWidth)/2;  
        double y = (h - scale * imageHeight)/2;  
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);  
        at.scale(scale, scale);  
        g2.drawRenderedImage(image, at);  
        
    }
    
    
    @Override
    public void paint(Graphics g) 
    {
        
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Image Width: "+  image.getWidth());
        System.out.println("Image Height: "+  image.getHeight());
        
        System.out.println("Width: "+getWidth());
        System.out.println("Height: "+getHeight());
        
        int iW = image.getWidth();
        int iH = image.getHeight();
        
        int W = getWidth();
        int H = getHeight();
        double garbageX = (W - iW*scale)/2; 
        double garbageY = (H - iH*scale)/2; 
        
        double gg = 10*scale;
        
        g.setColor(Color.red);
        Random rand = new Random();
        for(int i=0;i<ShowMap.points.size();i++)
        {
            
            int p, q;
            p = ShowMap.points.get(i).x;
            q = ShowMap.points.get(i).y;
            Integer I = ShowMap.kounter.get(p+" "+q);
            g.setColor(new Color(Math.min(255, I*35),0, 0));
            double xx = p*scale;
            double yy = q*scale;
            g.fillRect((int) garbageX+(int) xx, (int)garbageY+(int) yy, (int)(gg+(I*1.5)), (int)(gg+(I*1.5)));
        }
        
    }
    
   
    /** 
     * For the scroll pane. 
     */  
    public Dimension getPreferredSize()  
    {  
        int w = (int)(scale * image.getWidth());  
        int h = (int)(scale * image.getHeight());  
        return new Dimension(w, h);  
    }  
   
    public void setScale(double s)  
    {  
        scale = s;
        
        revalidate();      // update the scroll pane  
        repaint();  
    }  
   
    private void loadImage()  
    {  
        //String fileName = "BangladeshMap.jpg";  
        String fileName = "BangladeshMap_3.png";
        try  
        {  
            URL url = getClass().getResource(fileName);  
            //image = ImageIO.read(url);
            image = ImageIO.read(new FileInputStream(fileName));
            //this.icon = ImageIO.read(new FileInputStream("res/test.txt"));
        }  
        catch(MalformedURLException mue)  
        {  
            System.out.println("URL trouble: " + mue.getMessage());  
        }  
        catch(IOException ioe)  
        {  
            System.out.println("read trouble: " + ioe.getMessage());  
        }  
    }  
}  
   
class ImageZoom  
{  
    ImagePanel imagePanel;  
   
    public ImageZoom(ImagePanel ip)  
    {  
        imagePanel = ip;  
    }  
   
    public JPanel getUIPanel()  
    {  
        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.1, 1.4, .01);  
        final JSpinner spinner = new JSpinner(model);  
        spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));  
        //spinner.setEnabled(false);
        spinner.addChangeListener(new ChangeListener()  
        {  
            public void stateChanged(ChangeEvent e)  
            {  
                float scale = ((Double)spinner.getValue()).floatValue(); 
                //System.out.println("Here :D");
                imagePanel.setScale(scale);
                imagePanel.repaint();
            }  
        });
        
        JPanel panel = new JPanel();  
        panel.add(new JLabel("scale"));  
        panel.add(spinner);  
        return panel;  
    }  
}  