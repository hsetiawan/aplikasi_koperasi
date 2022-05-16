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
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
public class BackgroundLogin extends JPanel{
     private Image game;
    
    public BackgroundLogin(){
        setOpaque(true);
        game=new ImageIcon(getClass().getResource("/gambar/KOPERASI_KARYAWAN_1.png")).getImage();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd=(Graphics2D)g.create();
        gd.drawImage(game, 0,0,getWidth(),getHeight(),null);
        gd.dispose();
    } 
}
