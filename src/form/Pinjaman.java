/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.NumberFormatter;
import koneksi.koneksi;
import org.codehaus.groovy.control.messages.Message;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author hends
 */
public class Pinjaman extends javax.swing.JFrame {
    koneksi connect;
    private Connection conn = (Connection) new koneksi().connect();
    private DefaultTableModel tabmode;
    private static final NumberFormat FORMAT = NumberFormat.getCurrencyInstance();

    Icon deleteIcon = new ImageIcon(getClass().getResource("/gambar/trash_white_16.png"));
    Icon editIcon = new ImageIcon(getClass().getResource("/gambar/edit.png"));
    SimpleDateFormat todaySdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    SimpleDateFormat formatDate = new SimpleDateFormat("YYYY-MM-dd");

    String today = todaySdf.format(new Date());

    
    
    private void autoNumber(){
        String idKey = "P000";
        int i = 0;
        try{
            //Connection con = conn.connect();
            Statement st = conn.createStatement();
            String sql = "select id from pinjaman order by id ";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                idKey = rs.getString("id");
            }
            if(!idKey.isEmpty() && idKey.length() > 4){
                idKey = idKey.substring(4);
 
                i = Integer.parseInt(idKey)+1;
            }else{
                i = 1;
            }
            idKey = "0000" +i;
            idKey = "P"+ idKey.substring(idKey.length()-5);
            idPinjaman.setText(idKey);
            idPinjaman.setEnabled(false);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void setAnggotaList(){
        try{
            String sql = "select * from anggota where  is_deleted = 0 order by name";
            PreparedStatement stat =  conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                 anggotaList.addItem(rs.getString("nik")+" - "+rs.getString("name"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String getIdAnggotaByNik(String nik){
        String result = "";
        try {
            String sql = "select id from anggota where nik = ? and is_deleted = 0 order by name";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, nik);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                 result = rs.getString("id");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    private String getNikAndNameById(String id){
        String result = "";
        try {
            String sql = "select * from anggota where id = ? and is_deleted = 0 order by name";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, id);

            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                 String name = rs.getString("name");
                 String nik = rs.getString("nik");
                 result = nik + " - " + name;   
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
     
    private NumberFormatter setFormating(){
        NumberFormat longFormat = NumberFormat.getIntegerInstance();

        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setValueClass(Long.class); //optional, ensures you will always get a long value
        numberFormatter.setAllowsInvalid(false); //this is the key!!
        numberFormatter.setMinimum(0l); //Optional
        
        return numberFormatter;
    }
    
 
    protected void datatable(){
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        
        setTotal();
        
        Object[] Baris ={"ID Pinjaman", "Id Anggota","Nama Anggota", "Tanggal Pinjaman", "Jumlah",  " ", ""};
        tabmode = new DefaultTableModel(null, Baris);
        
        pinjamanTabel.setModel(tabmode);
        pinjamanTabel.getColumnModel().getColumn(4).setHeaderRenderer(rightRenderer);
        pinjamanTabel.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        pinjamanTabel.getColumnModel().getColumn(0).setMinWidth(100);
        pinjamanTabel.getColumnModel().getColumn(1).setMinWidth(100);
        pinjamanTabel.getColumnModel().getColumn(5).setMaxWidth(100);
        pinjamanTabel.getColumnModel().getColumn(6).setMaxWidth(100);
         
        String sql = "select a.*, p.* from" +
                    " pinjaman as p inner join anggota as a on a.id = p.id_anggota" +
                    " where a.is_deleted  = 0" +
                    " and p.is_deleted = 0";
             
        if(!cariField.getText().isEmpty()){
            sql = sql.concat(" AND a.name like '%"+cariField.getText()+"%' "
                + "OR a.id like '%"+cariField.getText()+"%'  "
                + "OR p.jumlah like '%"+cariField.getText()+"%'  "
                + "OR p.tanggal_pinjaman like '%"+cariField.getText()+"%'  ");
        }
                
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("p.id"); 
                String idAnggota = rs.getString("p.id_anggota"); 
                String nameAnggota = rs.getString("a.name"); 
                String tanggalPinjaman = rs.getString("p.tanggal_pinjaman");
                String jumlah =  FORMAT.format(rs.getLong("p.jumlah")).replace("Rp", "");
                          
                Object[] data={id, idAnggota, nameAnggota, tanggalPinjaman, jumlah, editIcon, deleteIcon };
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                delete();
            }
        };
        
         Action edit = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                edit();
            }
        };
        
        ButtonColumn editButton = new ButtonColumn(pinjamanTabel, edit, 5, Color.ORANGE, "Ubah");
        ButtonColumn deleteButton = new ButtonColumn(pinjamanTabel, delete, 6, Color.RED, "Hapus");
    }
    
    public void disabledForm(){
        idLabel.setForeground(Color.GRAY);
        anggotaLabel.setForeground(Color.GRAY);
        tglSimpanLabel.setForeground(Color.GRAY);
        jumlahLabel.setForeground(Color.GRAY);
        
        jumlah.setValue(null);
        tglPinjaman.setDate(null);
        anggotaList.setSelectedIndex(0);
      
        idPinjaman.setEnabled(false);
        jumlah.setEnabled(false);
        tglPinjaman.setEnabled(false);
        anggotaList.setEnabled(false);
        
        simpanButton.setEnabled(false);
        batalButton.setEnabled(false);
        formPanel.setBackground(getBackground());
     }
    
    public void enableForm(){
        idLabel.setForeground(Color.BLACK);
        anggotaLabel.setForeground(Color.BLACK);
        tglSimpanLabel.setForeground(Color.BLACK);
        jumlahLabel.setForeground(Color.BLACK);
      
        jumlah.setEnabled(true);
        tglPinjaman.setEnabled(true);
        anggotaList.setEnabled(true);

        
        simpanButton.setEnabled(true);
        batalButton.setEnabled(true);
    }

    /**
     * Creates new form Anggota
     */
    public Pinjaman() {
        initComponents();
        datatable();
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        disabledForm();
        ubahDataLabel.setVisible(false);
        setAnggotaList();
        idAnggota.setVisible(false);

        PromptSupport.setPrompt("Cari ...", cariField);
    }
    
    public boolean isValidate(){
        boolean result = false;
        if(jumlah.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "No HP boleh kosong!");
        else
            result = true;
        
        return result;
        // jenis kelamin belum
    }
    
   
     
    public void setTotal(){
        String total = "0";
        try{
            String sql = "select sum(p.jumlah) as total from" +
                        " pinjaman as p inner join anggota as a on a.id = p.id_anggota" +
                        " where a.is_deleted  = 0" +
                        " and p.is_deleted = 0";
            PreparedStatement stat =  conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
             if(rs.getString("total") != null)
                    total = ""+FORMAT.format(rs.getLong("total"));
            }
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(total.contains("Rp"))
            total = total.replace("Rp", "Rp. ");
        
        totalData.setText(total);
    }
    
    public void save(){
        boolean isValid = isValidate();
        String sqlInsert = "INSERT INTO pinjaman (id, id_anggota, tanggal_pinjaman, jumlah, created_by, created_date ) "
                            + "values (?, ?, ?, ?, ?, ?)";
        if(isValid){
              String newJumlah = jumlah.getText().replace(".","");
                try {
                    PreparedStatement stat =  conn.prepareStatement(sqlInsert);
                    stat.setString(1, idPinjaman.getText());
                    stat.setString(2, idAnggota.getText());          
                    stat.setString(3, todaySdf.format(tglPinjaman.getDate()));
                    stat.setLong(4, Long.valueOf(newJumlah));     
                    stat.setString(5, UserSession.getUserId());         
                    stat.setString(6, today);         

                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Pinjaman Berhasil Disimpan");
                    autoNumber();
                    disabledForm();
                    datatable();
                }catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error "+e.getMessage());
                }
        }
        
    }
    
    
    public void setDetail(String id){
        try{
            String sql = "select * from pinjaman where id = ? and is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, id);
            ResultSet rs = stat.executeQuery();
            
            while(rs.next()){
                String idAnggotaValue = rs.getString("id_anggota");
                idAnggota.setText(idAnggotaValue);
                System.out.println("nilai anggota "+getNikAndNameById(idAnggotaValue) );
                anggotaList.setSelectedItem(getNikAndNameById(idAnggotaValue));

                idPinjaman.setText(rs.getString("id"));
                Date dateSaving = rs.getDate("tanggal_pinjaman");
                tglPinjaman.setDate(dateSaving);
                jumlah.setText(rs.getString("jumlah"));
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
     }
    
    public void edit(){
        ubahDataLabel.setVisible(true);
        formPanel.setBackground(Color.ORANGE);
        enableForm();
        
        int bar = pinjamanTabel.getSelectedRow(); 
        String id = tabmode.getValueAt(bar, 0).toString();

        setDetail(id);
    }
    
    public void proccessEdit(){
        boolean isValid = isValidate();
        String sqlUpdate = "UPDATE pinjaman SET id_anggota=?, tanggal_pinjaman=?, jumlah=?, modified_by=?, modified_date=? WHERE id=? ";
        if(isValid){
            //save syntax is here
            try {
                String newJumlah = jumlah.getText().replace(".","");
                
                PreparedStatement stat =  conn.prepareStatement(sqlUpdate);
                stat.setString(1, idAnggota.getText());          
                stat.setString(2, todaySdf.format(tglPinjaman.getDate()));
                stat.setLong(3, Long.valueOf(newJumlah));     
                stat.setString(4, UserSession.getUserId());         
                stat.setString(5, today);     
                stat.setString(6, idPinjaman.getText());
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Pinjaman Berhasil Diubah");
                autoNumber();
                disabledForm();
                datatable();
                ubahDataLabel.setVisible(false);

            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
    }
    
    private void delete(){
        int bar = pinjamanTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        int ok = JOptionPane.showConfirmDialog(null,"Apakah Anda Yakin Akan Menghapus Data ini?","Konfirmasi", JOptionPane.YES_OPTION);
        if (ok==0){
            String sql ="update pinjaman set is_deleted = ?, modified_by = ?, modified_date = ? where id = ?";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setInt(1, 1);
                stat.setString(2, UserSession.getUserId());         
                stat.setString(3, today);
                stat.setString(4, id);

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Pinjaman Berhasil Di Hapus");
                autoNumber();
                datatable();
            }catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Data gagal" +e);
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cariField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalData = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pinjamanTabel = new javax.swing.JTable();
        formPanel = new javax.swing.JPanel();
        anggotaLabel = new javax.swing.JLabel();
        tglSimpanLabel = new javax.swing.JLabel();
        jumlahLabel = new javax.swing.JLabel();
        simpanButton = new javax.swing.JButton();
        idLabel = new javax.swing.JLabel();
        idPinjaman = new javax.swing.JTextField();
        batalButton = new javax.swing.JButton();
        ubahDataLabel = new javax.swing.JLabel();
        tglPinjaman = new com.toedter.calendar.JDateChooser();
        anggotaList = new javax.swing.JComboBox<>();
        jumlah = new javax.swing.JFormattedTextField(setFormating());
        idAnggota = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaksi - Data Pinjaman");
        setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        setLocation(new java.awt.Point(10, 100));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("dataAnggota"); // NOI18N
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(1, 138, 51));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Calibri", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Pinjaman");

        addButton.setBackground(new java.awt.Color(255, 255, 255));
        addButton.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        addButton.setForeground(new java.awt.Color(1, 138, 51));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/plus.png"))); // NOI18N
        addButton.setText("Buat Baru");
        addButton.setToolTipText("Buat Data Simpanan Baru");
        addButton.setBorderPainted(false);
        addButton.setIconTextGap(6);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        cariField.setBackground(new java.awt.Color(235, 236, 237));
        cariField.setColumns(1);
        cariField.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        cariField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        cariField.setAutoscrolls(false);
        cariField.setBorder(null);
        cariField.setMargin(new java.awt.Insets(5, 1, 5, 1));
        cariField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariFieldActionPerformed(evt);
            }
        });
        cariField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariFieldKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/search_black_16.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cariField)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pinjaman");

        totalData.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        totalData.setForeground(new java.awt.Color(255, 255, 255));
        totalData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalData.setText("#");
        totalData.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalData, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323)
                .addComponent(addButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(totalData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap())
        );

        pinjamanTabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pinjamanTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pinjamanTabel.setEditingColumn(0);
        pinjamanTabel.setEditingRow(0);
        pinjamanTabel.setRowHeight(25);
        pinjamanTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pinjamanTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pinjamanTabel);

        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        anggotaLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        anggotaLabel.setText("Anggota :");

        tglSimpanLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        tglSimpanLabel.setText("Tanggal Pinjam :");

        jumlahLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        jumlahLabel.setText("Jumlah :");

        simpanButton.setBackground(new java.awt.Color(1, 138, 51));
        simpanButton.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        simpanButton.setForeground(new java.awt.Color(255, 255, 255));
        simpanButton.setText("Simpan");
        simpanButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 138, 51)));
        simpanButton.setIconTextGap(6);
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        idLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        idLabel.setText("ID :");

        idPinjaman.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N

        batalButton.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        batalButton.setText("Batal");
        batalButton.setBorder(null);
        batalButton.setIconTextGap(6);
        batalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalButtonActionPerformed(evt);
            }
        });

        ubahDataLabel.setBackground(new java.awt.Color(0, 0, 0));
        ubahDataLabel.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        ubahDataLabel.setForeground(new java.awt.Color(255, 255, 255));
        ubahDataLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ubahDataLabel.setText("Ubah Data");
        ubahDataLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ubahDataLabel.setOpaque(true);

        tglPinjaman.setDateFormatString("yyyy-MM-dd");
        tglPinjaman.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N

        anggotaList.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        anggotaList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Anggota" }));
        anggotaList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anggotaListActionPerformed(evt);
            }
        });

        jumlah.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jumlah.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idPinjaman)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ubahDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tglPinjaman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(anggotaList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglSimpanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anggotaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(formPanelLayout.createSequentialGroup()
                                .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jumlahLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 199, Short.MAX_VALUE))
                    .addComponent(jumlah))
                .addContainerGap())
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(ubahDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addComponent(idPinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tglSimpanLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglPinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(anggotaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(anggotaList, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jumlahLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(207, 207, 207))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pinjamanTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pinjamanTabelMouseClicked
        // TODO add your handling code here:
        int bar = pinjamanTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        String namaValue = tabmode.getValueAt(bar, 1).toString();
        String noHpValue = tabmode.getValueAt(bar, 2).toString();
        String alamatValue = tabmode.getValueAt(bar, 3).toString();
                
//        idPengungjung.setText(id);
//        namaPengunjung.setText(namaValue);
//        telp.setText(noHpValue);
//        alamat.setText(alamatValue);
    }//GEN-LAST:event_pinjamanTabelMouseClicked

    private void cariFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariFieldKeyReleased
        // TODO add your handling code here:
        datatable();
    }//GEN-LAST:event_cariFieldKeyReleased

    private void cariFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariFieldActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        enableForm();
        ubahDataLabel.setVisible(false);
        autoNumber();
          
    }//GEN-LAST:event_addButtonActionPerformed

    private void batalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        ubahDataLabel.setVisible(false);

    }//GEN-LAST:event_batalButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        // TODO add your handling code here:
        if(!ubahDataLabel.isVisible())
            save();
        else
            proccessEdit();

    }//GEN-LAST:event_simpanButtonActionPerformed

    private void anggotaListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anggotaListActionPerformed
        // TODO add your handling code here:
        if(anggotaList.getSelectedItem().toString().contains("-")){
            String nikAnggota = anggotaList.getSelectedItem().toString().split("-")[0].trim();
            String idAnggotaValue = getIdAnggotaByNik(nikAnggota);
            this.idAnggota.setText(idAnggotaValue);
        }
    }//GEN-LAST:event_anggotaListActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyReleased
        // TODO add your handling code here:
         
    }//GEN-LAST:event_jumlahKeyReleased

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
            java.util.logging.Logger.getLogger(Pinjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pinjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pinjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pinjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pinjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel anggotaLabel;
    private javax.swing.JComboBox<String> anggotaList;
    private javax.swing.JButton batalButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextField cariField;
    private javax.swing.JPanel formPanel;
    private javax.swing.JTextField idAnggota;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idPinjaman;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField jumlah;
    private javax.swing.JLabel jumlahLabel;
    private javax.swing.JTable pinjamanTabel;
    private javax.swing.JButton simpanButton;
    private com.toedter.calendar.JDateChooser tglPinjaman;
    private javax.swing.JLabel tglSimpanLabel;
    private javax.swing.JLabel totalData;
    private javax.swing.JLabel ubahDataLabel;
    // End of variables declaration//GEN-END:variables
}
