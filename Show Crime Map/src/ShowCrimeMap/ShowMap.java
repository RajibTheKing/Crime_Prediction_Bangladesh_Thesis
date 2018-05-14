/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.FontUIResource;

public class ShowMap extends JFrame implements MouseInputListener {

    static Lock lock1, lock2;
    static Condition condition1, condition2;
    private JButton addPoint = new JButton("Add New Point");
    public static Vector<Point> points = new Vector<Point>();
    private ImageZoom zoom;
    private ImagePanel panel;
    JScrollPane ScrollPane;
    static String msg = "", div1 = "", thana1 = "", dist1 = "";
    static String par = "", current = "", type1 = "";
    LoadFromDatabase dbload;

    ShowMap() {
        dbload = new LoadFromDatabase();
        dbload.readData();
        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        condition1 = lock1.newCondition();
        condition2 = lock2.newCondition();

        panel = new ImagePanel();
        zoom = new ImageZoom(panel);
        ScrollPane = new JScrollPane(panel);
        //JFrame f = new JFrame();  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(zoom.getUIPanel(), "North");
        getContentPane().add(ScrollPane);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        panel.add(addPoint);
        panel.addMouseListener(this);

        addPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String s = JOptionPane.showInputDialog("Enter New Point: ");
                    String ss[] = s.split(",");
                    int x = Integer.parseInt(ss[0]);
                    int y = Integer.parseInt(ss[1]);
                    points.add(new Point(x, y));
                    panel.repaint();
                    panel.repaint();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

    }

    public void add_points(int x, int y) {
        points.add(new Point(x, y));
        panel.repaint();
        panel.repaint();
    }

    public static void Add_New_Point(Graphics g, int x, int y) {
        g.setColor(Color.red);
        g.fillRect(x, y, 10, 10);
        //repaint();
    }

    public boolean isExists() {
        Point p = dbload.FindCordinate(current, par);
        if (p.equals(new Point(-1, -1))) {
            return false;
        } else {
            add_points(p.x, p.y);
//            condition1.signalAll();
            return true;
        }
    }

    public static void main(String[] args) {

        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SolaimanLipi", Font.BOLD, 32)));
        ShowMap ss = new ShowMap();
        PointEntry pp = new PointEntry();
        pp.FileRead();

        msg = "";

        for (String str : pp.division.keySet()) {

            lock1.lock();

            par = "বাংলাদেশ";
            current = str;
            msg = " বিভাগঃ" + str;
            div1 = str;
            dist1 = "";
            thana1 = "";
            type1 = "division";
            try {
                if (!ss.isExists()) {
                    JOptionPane.showMessageDialog(ss, "বিভাগ " + str);
                    condition1.await();
                }

                for (String dist : pp.district.keySet()) {
                    if (!pp.district.get(dist).contains(str)) {
                        continue;
                    }
                    dist1 = dist;
                    par = str;
                    current = dist;
                    type1 = "district";
                    String msg1 = " জেলাঃ" + dist;

                    if (!ss.isExists()) {
                        JOptionPane.showMessageDialog(ss, msg + msg1);
                        condition1.await();
                    }
                    thana1 = "";
                    for (String thana : pp.thana.keySet()) {
                        if (!pp.thana.get(thana).equals(dist)) {
                            continue;
                        }
                        type1 = "thana";
                        thana1 = thana;
                        par = dist;
                        current = thana;
                        String msg2 = " থানাঃ" + thana;

                        if (!ss.isExists()) {
                            JOptionPane.showMessageDialog(ss, msg + msg1 + msg2);
                            condition1.await();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ShowMap.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                lock1.unlock();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {


        lock1.lock();
        try {
            System.out.println("MouseClicked: " + e.getX() + "," + e.getY());
//            JOptionPane.showMessageDialog(null, "Mouse Clicked: " + e.getX() + "," + e.getY());
            int ch = JOptionPane.showConfirmDialog(rootPane, msg + " " + dist1 + " " + thana1, "Is it?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (ch == JOptionPane.OK_OPTION) {
                
//                add_points(e.getX(), e.getY());
                updateDatabase(e.getX(), e.getY());
                
                condition1.signalAll();
            }

        } finally {
            lock1.unlock();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("MousePressed: " + e.getX() + "," + e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("MouseReleased: " + e.getX() + "," + e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("MouseEntered: " + e.getX() + "," + e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("MouseExited: " + e.getX() + "," + e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("MouseDragged: " + e.getX() + "," + e.getY());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("MouseMoved: " + e.getX() + "," + e.getY());

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateDatabase(int x, int y) {
        add_points(x, y);
        DatabaseConnection db = new DatabaseConnection();
        String query = "INSERT INTO `CrimeRanking`.`locationdata` (`location_id`, `name`, `parent`, `type`, `x`, `y`) VALUES (NULL,'" + current + "', '" + par + "', '" + type1 + "', '" + x + "', '" + y + "')";
        try {
            db.queryInsert(query);
        } catch (SQLException ex) {
            Logger.getLogger(ShowMap.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database entry fail");
        }
    }
}

class ImagePanel extends JPanel {

    BufferedImage image;
    double scale;

    public ImagePanel() {
        loadImage();
        scale = 1.0;
        setBackground(Color.black);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double x = (w - scale * imageWidth) / 2;
        double y = (h - scale * imageHeight) / 2;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.scale(scale, scale);
        g2.drawRenderedImage(image, at);

        g2.setFont(new Font("kalpurush", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        g2.drawString(ShowMap.div1 + " " + ShowMap.dist1 + " " + ShowMap.thana1, 700, 150);

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Image Width: " + image.getWidth());
        System.out.println("Image Height: " + image.getHeight());

        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + getHeight());

        int iW = image.getWidth();
        int iH = image.getHeight();

        int W = getWidth();
        int H = getHeight();
        double garbageX = (W - iW * scale) / 2;
        double garbageY = (H - iH * scale) / 2;

        double gg = 10 * scale;

        g.setColor(Color.red);
        for (int i = 0; i < ShowMap.points.size(); i++) {
            int p, q;
            p = ShowMap.points.get(i).x;
            q = ShowMap.points.get(i).y;
            double xx = p * scale;
            double yy = q * scale;
            g.fillRect((int) garbageX + (int) xx, (int) garbageY + (int) yy, (int) gg, (int) gg);
        }

    }

    /**
     * For the scroll pane.
     */
    public Dimension getPreferredSize() {
        int w = (int) (scale * image.getWidth());
        int h = (int) (scale * image.getHeight());
        return new Dimension(w, h);
    }

    public void setScale(double s) {
        scale = s;

        revalidate();      // update the scroll pane  
        repaint();
    }

    private void loadImage() {
        //String fileName = "BangladeshMap.jpg";  
        String fileName = "BangladeshMap_3.png";
        try {
            URL url = getClass().getResource(fileName);
            //image = ImageIO.read(url);
            image = ImageIO.read(new FileInputStream(fileName));
            //this.icon = ImageIO.read(new FileInputStream("res/test.txt"));
        } catch (MalformedURLException mue) {
            System.out.println("URL trouble: " + mue.getMessage());
        } catch (IOException ioe) {
            System.out.println("read trouble: " + ioe.getMessage());
        }
    }
}

class ImageZoom {

    ImagePanel imagePanel;

    public ImageZoom(ImagePanel ip) {
        imagePanel = ip;
    }

    public JPanel getUIPanel() {
        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.1, 1.4, .01);
        final JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));
        //spinner.setEnabled(false);
        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                float scale = ((Double) spinner.getValue()).floatValue();
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
