/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import org.apache.commons.io.FileUtils;
import org.jfree.util.StringUtils;

/**
 *
 * @author USER
 */
public class Dokumentasi extends javax.swing.JFrame {
koneksi connect;
private Connection conn = (Connection) new koneksi().connect();
private DefaultTableModel tabmode;
private final static String labelKegiatan = "Pilih Kegiatan";
File file;



protected void aktif(){
       id.setEnabled(true);
       kegiatanList.setEnabled(true);
       keterangan.setEnabled(true);
       kegiatanList.requestFocus();
    }

protected void kosong(){
        //t1.setText("");
        kegiatanList.setSelectedItem(labelKegiatan);
        foto.setText("");
        keterangan.setText("");
        previewData.setIcon(null);
        
        jButton5.setEnabled(true);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
     }

private void autoNumber(){
    String idKey = "D000";
    int i = 0;
    try{
        //Connection con = conn.connect();
        Statement st = conn.createStatement();
        String sql = "select id_dokumentasi from dokumentasi";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            idKey = rs.getString("id_dokumentasi");
        }
        if(!idKey.isEmpty() && idKey.length() > 4){
            idKey = idKey.substring(4);
            i = Integer.parseInt(idKey)+1;
        }else{
            i = 1;
        }
        
        idKey = "0000" +i;
        idKey = "D"+ idKey.substring(idKey.length()-5);
        id.setText(idKey);
        id.setEnabled(false);
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

private void cariKegiatan(){
        try{
        Statement st = conn.createStatement();
        String sql = " select id_kegiatan, judul_kegiatan from kegiatan";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
             kegiatanList.addItem( rs.getString("id_kegiatan").concat("-").concat(rs.getString("judul_kegiatan")) );
        }
        
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
}

private void initTable(){
     tabelDokumentasi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

         @Override
         public void valueChanged(ListSelectionEvent e) {
            try {
                int row=tabelDokumentasi.getSelectedRow();
                if(row != -1){
                    Toolkit toolkit=Toolkit.getDefaultToolkit();
                    String fotoValue = tabmode.getValueAt(row, 3).toString();

                    String path=new File(".").getCanonicalPath();
                    Image image=toolkit.getImage(path+"/image/"+fotoValue); //mengambil gambar dari folder image
                    Image imagedResized=image.getScaledInstance(120, 120, Image.SCALE_DEFAULT); //resize foto sesuai ukuran jlabel
                    ImageIcon icon=new ImageIcon(imagedResized);
                    previewData.setIcon(icon); // memasang gambar pada jlabel

                }
                } catch (IOException ex) {
                    ex.printStackTrace();
            } 
             //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
  
        });
}

protected void datatable(){
        Object[] Baris ={"ID Dokumentasi", "Judul", "Deskripsi", "Gambar"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelDokumentasi.setModel(tabmode);
        String sql = "select d.*,  k.id_kegiatan, k.judul_kegiatan from dokumentasi as d "
                + "inner join kegiatan as k ON k.id_kegiatan = d.id_kegiatan  "
                + "where k.judul_kegiatan like '%"+txtcari.getText()+"%' ";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("id_dokumentasi");
                String b = hasil.getString("id_kegiatan").concat("-").concat(hasil.getString("judul_kegiatan")) ; 
                String c = hasil.getString("foto"); 
                String d = hasil.getString("deskripsi"); 
                
                String[] data={a, b, d, c};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    /**
     * Creates new form inventaris
     */
    public Dokumentasi() {
        initComponents();
        datatable();
        autoNumber();
        connect = new koneksi();
        setLocationRelativeTo(this);
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        kegiatanList.requestFocus();
        
        jButton5.setEnabled(true);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
        resetButton.setEnabled(false);
        
        cariKegiatan();
        jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "tif", "tiff", "bmp", "gif", "png", "wbmp", "jpeg"));
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        initTable();
        //aktif();
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        t4 = new javax.swing.JTextField();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDokumentasi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        keterangan = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        kegiatanList = new javax.swing.JComboBox();
        foto = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        previewData = new javax.swing.JLabel();

        t4.setText("jTextField1");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DATA DOKUMENTASI");

        tabelDokumentasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Kegiatan", "Deskripsi", "Gambar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelDokumentasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDokumentasiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelDokumentasi);
        if (tabelDokumentasi.getColumnModel().getColumnCount() > 0) {
            tabelDokumentasi.getColumnModel().getColumn(0).setResizable(false);
            tabelDokumentasi.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Cari");

        txtcari.setToolTipText("");
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/cancel_red.png"))); // NOI18N
        jButton8.setToolTipText("Keluar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("ID");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Judul Kegiatan *");

        id.setEditable(false);
        id.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        id.setFocusable(false);
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Foto");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Deskripsi");

        keterangan.setColumns(20);
        keterangan.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        keterangan.setRows(5);
        jScrollPane1.setViewportView(keterangan);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/verify.png"))); // NOI18N
        jButton5.setText("TAMBAH");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/edit.png"))); // NOI18N
        jButton6.setText("UBAH");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/download.png"))); // NOI18N
        jButton7.setText("HAPUS");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        resetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/redo.png"))); // NOI18N
        resetButton.setToolTipText("Reset");
        resetButton.setBorderPainted(false);
        resetButton.setPreferredSize(new java.awt.Dimension(50, 20));
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        kegiatanList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Kegiatan" }));
        kegiatanList.setMinimumSize(new java.awt.Dimension(88, 30));
        kegiatanList.setPreferredSize(new java.awt.Dimension(88, 30));
        kegiatanList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kegiatanListActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Pilih File");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(id, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(resetButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(kegiatanList, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(foto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jToggleButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(43, 43, 43)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton7))
                        .addComponent(jScrollPane1)
                        .addComponent(kegiatanList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton1)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kegiatanList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        previewData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewData.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(previewData, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(30, 30, 30)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(previewData, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tabelDokumentasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDokumentasiMouseClicked
        try {
            
            
        int bar = tabelDokumentasi.getSelectedRow(); // TODO add your handling code here:
        String idValue = tabmode.getValueAt(bar, 0).toString();
        String kegiatanValue = tabmode.getValueAt(bar, 1).toString();
        String fotoValue = tabmode.getValueAt(bar, 3).toString();
        String keteranganValue = tabmode.getValueAt(bar, 2).toString();
                 
       String idKegiatan =  kegiatanValue.substring(0, 6);
                     
        id.setText(idValue);
        kegiatanList.setSelectedItem(kegiatanValue);
//        kegiatanValue.setText(judulValue);
   
        foto.setText(fotoValue);
        keterangan.setText(keteranganValue);
        
         
        jButton5.setEnabled(false);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        resetButton.setEnabled(true);
        
        initTable();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDokumentasiMouseClicked

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        datatable();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        kosong();
        autoNumber();
        resetButton.setEnabled(false);
        tabelDokumentasi.clearSelection();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int ok = JOptionPane.showConfirmDialog(null,"Apakah Anda Yakin Akan Menghapus Data ini?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql ="delete from dokumentasi where id_dokumentasi=?";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString (1, id.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
                autoNumber();
                kosong();
                kegiatanList.requestFocus();
                datatable();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal" +e);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
        if((String)kegiatanList.getSelectedItem() == labelKegiatan){
            JOptionPane.showMessageDialog(null, "Judul Kegiatan tidak boleh kosong!");
        }else{
            String idKegiatan =  kegiatanList.getSelectedItem().toString().substring(0, 6);
            System.out.println("idkegiatan :: "+idKegiatan);
              
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "update dokumentasi set id_kegiatan=?, foto=?,"
            + "deskripsi=?  where id_dokumentasi=?";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString (1, idKegiatan);
            stat.setString (2, foto.getText());
            stat.setString (3, keterangan.getText());
            stat.setString (4, id.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah");
            
            if(file != null){
                    String path=new File(".").getCanonicalPath();
                    FileUtils.copyFileToDirectory(file, new File(path+"/image")); //copy file ke folder image
                }
            
            autoNumber();
            kosong();
            kegiatanList.requestFocus();
            datatable();
           }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : ".concat(e.getMessage()));
        }catch(Exception ex){
            ex.printStackTrace();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        boolean valid =false;

        if((String)kegiatanList.getSelectedItem() == labelKegiatan){
            JOptionPane.showMessageDialog(null, "Judul Kegiatan tidak boleh kosong!");
        }else{
            String sql = "insert into dokumentasi values (?,?,?,?)";
            try {
                String idKegiatan =  kegiatanList.getSelectedItem().toString().substring(0, 6);
                
                PreparedStatement stat =  conn.prepareStatement(sql);
                stat.setString (1, id.getText());
                stat.setString (2, idKegiatan);
                stat.setString (3, foto.getText());
                stat.setString (4, keterangan.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                
                if(file != null){
                    String path=new File(".").getCanonicalPath();
                    FileUtils.copyFileToDirectory(file, new File(path+"/image")); //copy file ke folder image
                }
                
                autoNumber();
                kosong();
                kegiatanList.requestFocus();
                datatable();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ID Sudah Ada Tidak Diperbolehkan Menambah ID yang Sama".concat(e.getMessage()));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return;

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void kegiatanListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kegiatanListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kegiatanListActionPerformed
    
    private ImageIcon ii;

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
       jFileChooser1.showOpenDialog(this); 
       final File f = jFileChooser1.getSelectedFile();
        if (f != null) {
            String filename = f.getName();
            foto.setText(filename);
        }
        
        if (f == null) {
            return;
        }

        if(jFileChooser1.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            file=new File(jFileChooser1.getSelectedFile().getPath());
        }
 
    SwingWorker sw = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            Thread.sleep(5000);//simulate large image takes long to load
            ii = new ImageIcon(scaleImage(120, 120, ImageIO.read(new File(f.getAbsolutePath()))));
            return null;
        }

        @Override
        protected void done() { 
            super.done();
            previewData.setIcon(ii);
        }
    };
    sw.execute();
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dokumentasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dokumentasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dokumentasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dokumentasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dokumentasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField foto;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private com.toedter.components.JLocaleChooser jLocaleChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox kegiatanList;
    private javax.swing.JTextArea keterangan;
    private javax.swing.JLabel previewData;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField t4;
    private javax.swing.JTable tabelDokumentasi;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables

    private BufferedImage scaleImage(int w, int h, BufferedImage img) {
        
        BufferedImage bi;
        bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return bi;
    }


}
