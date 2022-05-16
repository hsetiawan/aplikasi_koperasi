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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import koneksi.koneksi;
import org.codehaus.groovy.control.messages.Message;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author hends
 */
public class Anggota extends javax.swing.JFrame {
    koneksi connect;
    private Connection conn = (Connection) new koneksi().connect();
    private DefaultTableModel tabmode;
    Icon deleteIcon = new ImageIcon(getClass().getResource("/gambar/trash_white_16.png"));
    Icon editIcon = new ImageIcon(getClass().getResource("/gambar/edit.png"));
    SimpleDateFormat todaySdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    String today = todaySdf.format(new Date());

    
    
    private void autoNumber(){
        String idKey = "A000";
        int i = 0;
        try{
            //Connection con = conn.connect();
            Statement st = conn.createStatement();
            String sql = "select id from anggota order by id ";
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
            idKey = "A"+ idKey.substring(idKey.length()-5);
            idAnggota.setText(idKey);
            idAnggota.setEnabled(false);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void isAlreadyExistNik(){
         try{
            String sql = "select * from anggota where nik = ? and is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, nik.getText());
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                JOptionPane optionPane = new JOptionPane("NIK Sudah Ada Terdaftar,\nTidak diperbolehkan Menambah NIK yang Sama", JOptionPane.WARNING_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Peringatan!");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);                
                this.nik.setText("");
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    protected void datatable(){
        
        setTotal();        
        Object[] Baris ={"ID Anggota", "NIK", "Nama", "Gender", "No Hp", "Alamat", " ", ""};
        tabmode = new DefaultTableModel(null, Baris);
        
        anggotaTabel.setModel(tabmode);
        anggotaTabel.getColumnModel().getColumn(0).setMaxWidth(90);
        anggotaTabel.getColumnModel().getColumn(3).setMaxWidth(70);
        anggotaTabel.getColumnModel().getColumn(6).setMaxWidth(100);
        anggotaTabel.getColumnModel().getColumn(7).setMaxWidth(100);
      
        
        String sql = "select * from anggota where is_deleted = 0 ";
        if(!cariField.getText().isEmpty()){
            sql = sql.concat("AND name like '%"+cariField.getText()+"%' "
                + "OR phone_number like '%"+cariField.getText()+"%' "
                + "OR nik like '%"+cariField.getText()+"%'  ");
        }
                
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id"); 
                String nik = rs.getString("nik"); 
                String name = rs.getString("name");
                String address = rs.getString("address");
                String gender  = rs.getString("gender");
                String phoneNumber = rs.getString("phone_number");
                
              
                Object[] data={id, nik, name, gender, phoneNumber, address,  editIcon, deleteIcon };
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
        
        ButtonColumn editButton = new ButtonColumn(anggotaTabel, edit, 6, Color.ORANGE, "Ubah");
        ButtonColumn deleteButton = new ButtonColumn(anggotaTabel, delete, 7, Color.RED, "Hapus");
    }
    
    public void disabledForm(){
        idLabel.setForeground(Color.GRAY);
        nikLabel.setForeground(Color.GRAY);
        namaLabel.setForeground(Color.GRAY);
        tglLahirLabel.setForeground(Color.GRAY);
        noHpLabel.setForeground(Color.GRAY);
        alamatLabel.setForeground(Color.GRAY);
        jkLabel.setForeground(Color.GRAY);
        lakiLakiLabel.setForeground(Color.GRAY);
        perempuanLabel.setForeground(Color.GRAY);
        
        nik.setText("");
        nama.setText("");
        phone_number.setText("");
        alamat.setText("");
        tglLahir.setText("");
        
        idAnggota.setEnabled(false);
        nik.setEnabled(false);
        nama.setEnabled(false);
        phone_number.setEnabled(false);
        alamat.setEnabled(false);
        tglLahir.setEnabled(false);
        lakiLakiLabel.setEnabled(false);
        perempuanLabel.setEnabled(false);

        simpanButton.setEnabled(false);
        batalButton.setEnabled(false);
        formPanel.setBackground(getBackground());

     }
    
    public void enableForm(){
        idLabel.setForeground(Color.BLACK);
        nikLabel.setForeground(Color.BLACK);
        namaLabel.setForeground(Color.BLACK);
        tglLahirLabel.setForeground(Color.BLACK);
        noHpLabel.setForeground(Color.BLACK);
        alamatLabel.setForeground(Color.BLACK);
        jkLabel.setForeground(Color.BLACK);
        lakiLakiLabel.setForeground(Color.BLACK);
        perempuanLabel.setForeground(Color.BLACK);
        
        nik.setEnabled(true);
        nama.setEnabled(true);
        phone_number.setEnabled(true);
        alamat.setEnabled(true);
        tglLahir.setEnabled(true);

        
        lakiLakiLabel.setEnabled(true);
        perempuanLabel.setEnabled(true);
        simpanButton.setEnabled(true);
        batalButton.setEnabled(true);
    }

    /**
     * Creates new form Anggota
     */
    public Anggota() {
        initComponents();
        datatable();
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        disabledForm();
        ubahDataLabel.setVisible(false);
        

        PromptSupport.setPrompt("Cari ...", cariField);
    }
    
    public boolean isValidate(){
        boolean result = false;
        if(nik.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "NIK tidak boleh kosong!");
        else if(nama.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong!");
        else if(phone_number.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "No HP boleh kosong!");
        else if(tglLahir.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Tanggal Lahir tidak boleh kosong!");
        else if(alamat.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong!");
        else
            result = true;
        
        return result;
        // jenis kelamin belum
    }
    
    public String getGender(){
        String result = "P";
        if(lakiLakiLabel.isSelected())
            result = "L";
        
        return result;
    }
    
     public void setGender(String value){
        if(value.equals("L"))
            lakiLakiLabel.setSelected(true);
        else
            perempuanLabel.setSelected(true);
         
    }
     
    public void setTotal(){
        String total = "0";
        try{
            String sql = "select count(id) as total from anggota where is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                total = rs.getString("total");
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        totalData.setText(total);
    }
    
    public void save(){
        boolean isValid = isValidate();
       
        String sqlInsert = "INSERT INTO anggota (id, name, nik, address, gender, birth_date, phone_number, created_by, created_date ) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if(isValid){
            //save syntax is here
            try {
            PreparedStatement stat =  conn.prepareStatement(sqlInsert);
            stat.setString(1, idAnggota.getText());
            stat.setString(2, nama.getText());          
            stat.setString(3, nik.getText());
            stat.setString(4, alamat.getText());
            stat.setString(5, getGender());
            stat.setString(6, tglLahir.getText());
            stat.setString(7, phone_number.getText());         
            stat.setString(8, UserSession.getUserId());         
            stat.setString(9, today);         
            
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Disimpan");
            autoNumber();
            disabledForm();
            datatable();
            //clear form
             }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ID Sudah Ada Tidak Diperbolehkan Menambah ID yang Sama");
            }
        }
        
    }
    
    
    public void setDetail(String id){
        try{
            String sql = "select * from anggota where id = ? and is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, id);
            ResultSet rs = stat.executeQuery();
            
            while(rs.next()){
                idAnggota.setText(rs.getString("id"));
                nik.setText(rs.getString("nik"));
                nama.setText(rs.getString("name"));
                tglLahir.setText(rs.getString("birth_date"));
                alamat.setText(rs.getString("address"));
                phone_number.setText(rs.getString("phone_number"));
                setGender(rs.getString("gender"));
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
     }
    
    public void edit(){
        ubahDataLabel.setVisible(true);
        formPanel.setBackground(Color.ORANGE);
        enableForm();
        
        int bar = anggotaTabel.getSelectedRow(); 
        String id = tabmode.getValueAt(bar, 0).toString();
        PromptSupport.setPrompt("YYYY-MM-DD", tglLahir);

        setDetail(id);
    }
    
    public void proccessEdit(){
        boolean isValid = isValidate();
        String sqlUpdate = "UPDATE anggota  SET name=?, nik=?, address=?, gender=?, birth_date=?, phone_number=?, modified_by=?, modified_date=? WHERE id=? ";
        if(isValid){
            //save syntax is here
             try {
            PreparedStatement stat =  conn.prepareStatement(sqlUpdate);
            stat.setString(1, nama.getText());          
            stat.setString(2, nik.getText());
            stat.setString(3, alamat.getText());
            stat.setString(4, getGender());
            stat.setString(5, tglLahir.getText());
            stat.setString(6, phone_number.getText());         
            stat.setString(7, UserSession.getUserId());         
            stat.setString(8, today);     
            stat.setString(9, idAnggota.getText());
            stat.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Anggota Berhasil Diubah");
            autoNumber();
            disabledForm();
            datatable();
            ubahDataLabel.setVisible(true);

            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
    }
    
    private void delete(){
        int bar = anggotaTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        int ok = JOptionPane.showConfirmDialog(null,"Apakah Anda Yakin Akan Menghapus Data ini?","Konfirmasi", JOptionPane.YES_OPTION);
        if (ok==0){
            String sql ="update anggota set is_deleted = ?, modified_by = ?, modified_date = ? where id = ?";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setInt(1, 1);
                stat.setString(2, UserSession.getUserId());         
                stat.setString(3, today);
                stat.setString(4, id);

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
                autoNumber();
                datatable();
            }catch (SQLException e) {
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
        anggotaTabel = new javax.swing.JTable();
        formPanel = new javax.swing.JPanel();
        namaLabel = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        nikLabel = new javax.swing.JLabel();
        nik = new javax.swing.JTextField();
        jkLabel = new javax.swing.JLabel();
        tglLahirLabel = new javax.swing.JLabel();
        lakiLakiLabel = new javax.swing.JRadioButton();
        perempuanLabel = new javax.swing.JRadioButton();
        noHpLabel = new javax.swing.JLabel();
        phone_number = new javax.swing.JTextField();
        tglLahir = new javax.swing.JFormattedTextField();
        alamatLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        alamat = new javax.swing.JTextArea();
        simpanButton = new javax.swing.JButton();
        idLabel = new javax.swing.JLabel();
        idAnggota = new javax.swing.JTextField();
        batalButton = new javax.swing.JButton();
        ubahDataLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Master - Data Anggota");
        setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        setLocation(new java.awt.Point(10, 100));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("dataAnggota"); // NOI18N
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(1, 138, 51));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Calibri", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Anggota");

        addButton.setBackground(new java.awt.Color(255, 255, 255));
        addButton.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        addButton.setForeground(new java.awt.Color(1, 138, 51));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/plus.png"))); // NOI18N
        addButton.setText("Buat Baru");
        addButton.setToolTipText("Buat Data Anggota Baru");
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
                .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel4.setText("Anggota");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 454, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalData, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
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
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(totalData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        anggotaTabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        anggotaTabel.setRowHeight(25);
        anggotaTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anggotaTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(anggotaTabel);

        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        namaLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        namaLabel.setText("Nama :");

        nikLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        nikLabel.setText("Nomor Induk Karyawan (NIK) :");

        nik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nikKeyReleased(evt);
            }
        });

        jkLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        jkLabel.setText("Jenis Kelamin :");

        tglLahirLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        tglLahirLabel.setText("Tanggal Lahir :");

        buttonGroup1.add(lakiLakiLabel);
        lakiLakiLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        lakiLakiLabel.setText("Laki - Laki");
        lakiLakiLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lakiLakiLabelActionPerformed(evt);
            }
        });

        buttonGroup1.add(perempuanLabel);
        perempuanLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        perempuanLabel.setText("Perempuan");

        noHpLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        noHpLabel.setText("No HP :");

        tglLahir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));

        alamatLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        alamatLabel.setText("Alamat :");

        alamat.setColumns(20);
        alamat.setRows(5);
        jScrollPane2.setViewportView(alamat);

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

        idAnggota.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

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

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglLahir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nik)
                    .addComponent(nama)
                    .addComponent(phone_number)
                    .addComponent(jScrollPane2)
                    .addComponent(idAnggota)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nikLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(formPanelLayout.createSequentialGroup()
                                .addComponent(lakiLakiLabel)
                                .addGap(38, 38, 38)
                                .addComponent(perempuanLabel))
                            .addComponent(tglLahirLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(noHpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alamatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(formPanelLayout.createSequentialGroup()
                                .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 176, Short.MAX_VALUE))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ubahDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(ubahDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nikLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nik, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(namaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jkLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lakiLakiLabel)
                    .addComponent(perempuanLabel))
                .addGap(18, 18, 18)
                .addComponent(tglLahirLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(noHpLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(alamatLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cariFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariFieldActionPerformed

    private void cariFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariFieldKeyReleased
        // TODO add your handling code here:
        datatable();
    }//GEN-LAST:event_cariFieldKeyReleased

    private void lakiLakiLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lakiLakiLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lakiLakiLabelActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        enableForm();
        ubahDataLabel.setVisible(false);
        autoNumber();
        PromptSupport.setPrompt("YYYY-MM-DD", tglLahir);


    }//GEN-LAST:event_addButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        // TODO add your handling code here:
        if(!ubahDataLabel.isVisible())
            save();
        else
            proccessEdit();
        
    }//GEN-LAST:event_simpanButtonActionPerformed

    private void anggotaTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anggotaTabelMouseClicked
        // TODO add your handling code here:
        int bar = anggotaTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        String namaValue = tabmode.getValueAt(bar, 1).toString();
        String noHpValue = tabmode.getValueAt(bar, 2).toString();
        String alamatValue = tabmode.getValueAt(bar, 3).toString();
                
//        idPengungjung.setText(id);
//        namaPengunjung.setText(namaValue);
//        telp.setText(noHpValue);
//        alamat.setText(alamatValue);
    }//GEN-LAST:event_anggotaTabelMouseClicked

    private void batalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        ubahDataLabel.setVisible(false);


    }//GEN-LAST:event_batalButtonActionPerformed

    private void nikKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nikKeyReleased
        // TODO add your handling code here:
        isAlreadyExistNik();
    }//GEN-LAST:event_nikKeyReleased

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
            java.util.logging.Logger.getLogger(Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Anggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextArea alamat;
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JTable anggotaTabel;
    private javax.swing.JButton batalButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextField cariField;
    private javax.swing.JPanel formPanel;
    private javax.swing.JTextField idAnggota;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jkLabel;
    private javax.swing.JRadioButton lakiLakiLabel;
    private javax.swing.JTextField nama;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JTextField nik;
    private javax.swing.JLabel nikLabel;
    private javax.swing.JLabel noHpLabel;
    private javax.swing.JRadioButton perempuanLabel;
    private javax.swing.JTextField phone_number;
    private javax.swing.JButton simpanButton;
    private javax.swing.JFormattedTextField tglLahir;
    private javax.swing.JLabel tglLahirLabel;
    private javax.swing.JLabel totalData;
    private javax.swing.JLabel ubahDataLabel;
    // End of variables declaration//GEN-END:variables
}
