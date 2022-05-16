/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
public class BackgroundDashboard extends JDesktopPane{
     private Image image;
    
    public BackgroundDashboard(){
        setOpaque(true);
        image=new ImageIcon(getClass().getResource("/gambar/edit.png")).getImage();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd=(Graphics2D)g.create();
        gd.drawImage(image, 0,0,getWidth(),getHeight(),null);
        gd.dispose();
    } 
}
