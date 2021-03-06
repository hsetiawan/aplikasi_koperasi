/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author USER
 */
public class Piket extends javax.swing.JFrame {
koneksi connect;
private Connection conn = (Connection) new koneksi().connect();
private DefaultTableModel tabmode;

protected void aktif(){
       id.setEnabled(true);
       anggotaList.setEnabled(true);
       tglTugas.setEnabled(true);
       tugas.setEnabled(true);
       
       anggotaList.requestFocus();
    }

protected void kosong(){
        //t1.setText("");
        anggotaList.setSelectedItem("Pilih Anggota");
        tugas.setText("");
        tglTugas.setDate(null);
        
        jButton5.setEnabled(true);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
     }

private void autoNumber(){
    String idKey = "T000";
    int i = 0;
    try{
        //Connection con = conn.connect();
        Statement st = conn.createStatement();
        String sql = "select id_piket from piket";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            idKey = rs.getString("id_piket");
        }
        if(!idKey.isEmpty() && idKey.length() > 4){
            idKey = idKey.substring(4);
            i = Integer.parseInt(idKey)+1;
        }else{
            i = 1;
        }
        
        idKey = "0000" +i;
        idKey = "T"+ idKey.substring(idKey.length()-5);
        id.setText(idKey);
        id.setEnabled(false);
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}



protected void datatable(){
        Object[] Baris ={"ID Piket","Anggota","Tanggal",  "Tugas"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelPiket.setModel(tabmode);
        String sql = "select p.* , a.id_anggota, a.nama_anggota "
                + "from piket as p inner join anggota as a ON a.id_anggota = p.id_anggota"
                + " where p.tugas like '%"+txtcari.getText()+"%'"
                + " or a.nama_anggota like '%"+txtcari.getText()+"%' ";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("id_piket");
                String b = hasil.getString("id_anggota"); 
                String c = hasil.getString("nama_anggota"); 
                String d = hasil.getString("jadwal_masuk");
                String e = hasil.getString("tugas"); 
                
                String[] data={a, b.concat("-").concat(c) , d, e};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}

private void cariAnggota(){
        try{
        Statement st = conn.createStatement();
        String sql = " select id_anggota, nama_anggota from anggota";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
             anggotaList.addItem( rs.getString("id_anggota").concat("-").concat(rs.getString("nama_anggota")) );
        }
        
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
}

    /**
     * Creates new form  
     */
    public Piket() {
        initComponents();
        datatable();
        autoNumber();
        connect = new koneksi();
        setLocationRelativeTo(this);
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        anggotaList.requestFocus();
        
        jButton5.setEnabled(true);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
        resetButton.setEnabled(false);
 
        cariAnggota();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPiket = new javax.swing.JTable();
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
        tugas = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        tglTugas = new com.toedter.calendar.JDateChooser();
        anggotaList = new javax.swing.JComboBox();

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
        jLabel7.setText("DATA PIKET");

        tabelPiket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Anggota", "Tanggal", "Tugas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPiket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPiketMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPiket);
        if (tabelPiket.getColumnModel().getColumnCount() > 0) {
            tabelPiket.getColumnModel().getColumn(0).setResizable(false);
            tabelPiket.getColumnModel().getColumn(2).setResizable(false);
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
        jLabel9.setText("Anggota");

        id.setEditable(false);
        id.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        id.setFocusable(false);
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Tanggal Piket");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Tugas");

        tugas.setColumns(20);
        tugas.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tugas.setRows(5);
        jScrollPane1.setViewportView(tugas);

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

        tglTugas.setDateFormatString("dd MMMM yyyy");
        tglTugas.setMinimumSize(new java.awt.Dimension(30, 20));

        anggotaList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Anggota" }));

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
        jLayeredPane1.setLayer(tglTugas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(anggotaList, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10))
                        .addGap(37, 37, 37)
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
                            .addComponent(tglTugas, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anggotaList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 20, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(tglTugas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(anggotaList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(30, 30, 30)
                                .addComponent(txtcari))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void tabelPiketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPiketMouseClicked
        try {
            
            
        int bar = tabelPiket.getSelectedRow(); // TODO add your handling code here:
        String idValue = tabmode.getValueAt(bar, 0).toString();
        String anggota = tabmode.getValueAt(bar, 1).toString();
        String tanggalValue = tabmode.getValueAt(bar, 2).toString();
        String tugasValue = tabmode.getValueAt(bar, 3).toString();
                 
        Date tanggalKegiatanDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalValue);
         
        id.setText(idValue);
        anggotaList.setSelectedItem(anggota);
        tglTugas.setDate(tanggalKegiatanDate);
        
        
        tugas.setText(tugasValue);
        
         
        jButton5.setEnabled(false);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        resetButton.setEnabled(true);
        

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPiketMouseClicked

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
        tabelPiket.clearSelection();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int ok = JOptionPane.showConfirmDialog(null,"Apakah Anda Yakin Akan Menghapus Data ini?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql ="delete from piket where id_piket=?";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString (1, id.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
                autoNumber();
                kosong();
                anggotaList.requestFocus();
                datatable();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal" +e);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
        if(anggotaList.getSelectedItem().toString().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Anggota tidak boleh kosong!");
        }else if(tglTugas.getDate()  == null){
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong!");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String idAnggota =  anggotaList.getSelectedItem().toString().substring(0, 6);
               
            String sql = "update piket  set id_anggota=?, jadwal_masuk=?,"
            + "tugas=?  where id_piket=?";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString (1, idAnggota);
            stat.setString (2, String.valueOf(sdf.format(tglTugas.getDate())));

            
            stat.setString (3, tugas.getText());
            stat.setString (4, id.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah");
            autoNumber();
            kosong();
            anggotaList.requestFocus();
            datatable();
        }        
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : ".concat(e.getMessage()));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        boolean valid =false;

        if(anggotaList.getSelectedItem().toString().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Anggota tidak boleh kosong!");
        }else if(tglTugas.getDate()  == null){
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong!");
        }else{
            String sql = "insert into piket values (?,?,?,?)";
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String idAnggota =  anggotaList.getSelectedItem().toString().substring(0, 6);
               
 
                PreparedStatement stat =  conn.prepareStatement(sql);
                stat.setString (1, id.getText());
                stat.setString (2, idAnggota);
                stat.setString (3, String.valueOf(sdf.format(tglTugas.getDate())));
                stat.setString (4, tugas.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                autoNumber();
                kosong();
                anggotaList.requestFocus();
                datatable();
                
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ID Sudah Ada Tidak Diperbolehkan Menambah ID yang Sama".concat(e.getMessage()));
            }
        }
        return;

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

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
            java.util.logging.Logger.getLogger(Piket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Piket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Piket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Piket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Piket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox anggotaList;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField t4;
    private javax.swing.JTable tabelPiket;
    private com.toedter.calendar.JDateChooser tglTugas;
    private javax.swing.JTextArea tugas;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables


}
