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
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.groovy.control.messages.Message;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author hends
 */
public class Pengguna extends javax.swing.JFrame {
    koneksi connect;
    private Connection conn = (Connection) new koneksi().connect();
    private DefaultTableModel tabmode;
    Icon deleteIcon = new ImageIcon(getClass().getResource("/gambar/trash_white_16.png"));
    Icon editIcon = new ImageIcon(getClass().getResource("/gambar/edit.png"));
    SimpleDateFormat todaySdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    String today = todaySdf.format(new Date());
    String pageName = "Pengguna";

    
    
    private void autoNumber(){
        String idKey = "U000";
        int i = 0;
        try{
            //Connection con = conn.connect();
            Statement st = conn.createStatement();
            String sql = "select id from user order by id ";
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
            idKey = "U"+ idKey.substring(idKey.length()-5);
            idPengguna.setText(idKey);
            idPengguna.setEnabled(false);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void usernameAlreadyExist(){
         try{
            String sql = "select * from user where username = ? and is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, username.getText());
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                 
                JOptionPane optionPane = new JOptionPane("Username Sudah Ada Terdaftar,\nTidak diperbolehkan Menambah username yang Sama", JOptionPane.WARNING_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Peringatan!");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                
                this.username.setText("");
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected void datatable(){
        
        setTotal();
        
        Object[] Baris ={"ID Pengguna", "Username", "Nama", " ", ""};
        tabmode = new DefaultTableModel(null, Baris);
        penggunaTabel.setModel(tabmode);
        penggunaTabel.getColumnModel().getColumn(3).setMaxWidth(100);
        penggunaTabel.getColumnModel().getColumn(4).setMaxWidth(100);
        penggunaTabel.setModel(tabmode);
        
        String sql = "select * from  user where is_deleted = 0 ";
        if(!cariField.getText().isEmpty()){
            sql = sql.concat("AND username like '%"+cariField.getText()+"%' "
                + "OR name like '%"+cariField.getText()+"%' "
                + "OR id like '%"+cariField.getText()+"%'  ");
        }
                
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id"); 
                String username = rs.getString("username"); 
                String name = rs.getString("name");
               
              
                Object[] data={id, username, name,  editIcon, deleteIcon };
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
        
        ButtonColumn editButton = new ButtonColumn(penggunaTabel, edit, 3, Color.ORANGE, "Ubah");
        ButtonColumn deleteButton = new ButtonColumn(penggunaTabel, delete, 4, Color.RED, "Hapus");
    }
    
    public void disabledForm(){
        idLabel.setForeground(Color.GRAY);
        usernameLabel.setForeground(Color.GRAY);
        passwordLabel.setForeground(Color.GRAY);
        konfirmasiPasswordLabel.setForeground(Color.GRAY);
        namaLabel.setForeground(Color.GRAY);

       
        username.setText("");
        password.setText("");
        konfirmasiPassword.setText("");
        nama.setText("");
        
        idPengguna.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);
        konfirmasiPassword.setEnabled(false);
        nama.setEnabled(false);
     
        simpanButton.setEnabled(false);
        batalButton.setEnabled(false);
        formPanel.setBackground(getBackground());

     }
    
    public void enableForm(){
        idLabel.setForeground(Color.BLACK);
        usernameLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        konfirmasiPasswordLabel.setForeground(Color.BLACK);
        namaLabel.setForeground(Color.BLACK);
       
        username.setEnabled(true);
        konfirmasiPassword.setEnabled(true);
        password.setEnabled(true);
        nama.setEnabled(true);

        
        simpanButton.setEnabled(true);
        batalButton.setEnabled(true);
    }

    /**
     * Creates new form Anggota
     */
    public Pengguna() {
        initComponents();
        datatable();
        Locale locale = new Locale ("id","ID");
        Locale.setDefault(locale);
        disabledForm();
        ubahDataLabel.setVisible(false);
        

        PromptSupport.setPrompt("Cari ...", cariField);
    }
    
    public boolean isValidate(boolean isCreate){
        boolean result = false;
        if(username.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "username tidak boleh kosong!");
        else if(username.getText().trim().length() < 5)
            JOptionPane.showMessageDialog(null, "Panjang Username kurang dari 5 karakter");
        else if(isCreate && password.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Password tidak boleh kosong!");
        else if(isCreate && password.getText().trim().length() < 5)
            JOptionPane.showMessageDialog(null, "Panjang Password kurang dari 5 karakter");
        else if(isCreate && konfirmasiPassword.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Konfirmasi Password tidak boleh kosong!");
        else if(isCreate && !konfirmasiPassword.getText().equals(password.getText()))
            JOptionPane.showMessageDialog(null, "Konfirmasi Password tidak sama dengan Password!"); 
        else if(nama.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong!");
        else
            result = true;
        
        return result;
        // jenis kelamin belum
    }
 
    public void setTotal(){
        String total = "0";
        try{
            String sql = "select count(id) as total from user where is_deleted = 0 ";
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
        boolean isValid = isValidate(true);
       
        String sqlInsert = "INSERT INTO user (id, name, username, password, created_by, created_date ) "
                + "values (?, ?, ?, ?, ?, ?)";
        if(isValid){
            //save syntax is here
            try {
                PreparedStatement stat =  conn.prepareStatement(sqlInsert);
                stat.setString(1, idPengguna.getText());
                stat.setString(2, nama.getText());          
                stat.setString(3, username.getText());
                stat.setString(4, DigestUtils.md5Hex(password.getText()));
                stat.setString(5, UserSession.getUserId());         
                stat.setString(6, today);         


                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data "+pageName+" Berhasil Disimpan");
                autoNumber();
                disabledForm();
                datatable();
             }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ID Sudah Ada Tidak Diperbolehkan Menambah ID yang Sama");
            }
        }
        
    }
    
    
    public void setDetail(String id){
        try{
            String sql = "select * from user where id = ? and is_deleted = 0 ";
            PreparedStatement stat =  conn.prepareStatement(sql);
            stat.setString(1, id);
            ResultSet rs = stat.executeQuery();
            
            while(rs.next()){
                idPengguna.setText(rs.getString("id"));
                username.setText(rs.getString("username"));
                nama.setText(rs.getString("name"));
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
     }
    
    public void edit(){
        ubahDataLabel.setVisible(true);
        formPanel.setBackground(Color.ORANGE);
        enableForm();
        
        int bar = penggunaTabel.getSelectedRow(); 
        String id = tabmode.getValueAt(bar, 0).toString();

        setDetail(id);
    }
    
    public void proccessEdit(){
        boolean isValid = isValidate(false);
        
        if(!password.getText().isEmpty() && password.getText().trim().length() < 5)
            JOptionPane.showMessageDialog(null, "Panjang Password kurang dari 5 karakter");
        else if(!password.getText().isEmpty() && konfirmasiPassword.getText().trim().isEmpty())
            JOptionPane.showMessageDialog(null, "Konfirmasi Password tidak boleh kosong!");
        else if(!konfirmasiPassword.getText().equals(password.getText()))
            JOptionPane.showMessageDialog(null, "Konfirmasi Password tidak sama dengan Password!"); 
        
        String sqlUpdate = "UPDATE user  SET name=?, username=?, modified_by=?, modified_date=? WHERE id=? ";
        if(isValid){
              try {
                PreparedStatement stat =  conn.prepareStatement(sqlUpdate);
                stat.setString(1, nama.getText());          
                stat.setString(2, username.getText());
                stat.setString(3, UserSession.getUserId());         
                stat.setString(4, today);     
                stat.setString(5, idPengguna.getText());
                stat.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data "+pageName+" Berhasil Diubah");
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
        int bar = penggunaTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        int ok = JOptionPane.showConfirmDialog(null,"Apakah Anda Yakin Akan Menghapus Data ini?","Konfirmasi", JOptionPane.YES_OPTION);
        if (ok==0){
            String sql ="update user set is_deleted = ?, modified_by = ?, modified_date = ? where id = ?";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setInt(1, 1);
                stat.setString(2, UserSession.getUserId());         
                stat.setString(3, today);
                stat.setString(4, id);

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data "+pageName+" Berhasil Di Hapus");
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
        penggunaTabel = new javax.swing.JTable();
        formPanel = new javax.swing.JPanel();
        passwordLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        konfirmasiPasswordLabel = new javax.swing.JLabel();
        simpanButton = new javax.swing.JButton();
        idLabel = new javax.swing.JLabel();
        idPengguna = new javax.swing.JTextField();
        batalButton = new javax.swing.JButton();
        ubahDataLabel = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        namaLabel = new javax.swing.JLabel();
        konfirmasiPassword = new javax.swing.JPasswordField();
        nama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Master - Data Pengguna");
        setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        setLocation(new java.awt.Point(10, 100));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("dataAnggota"); // NOI18N
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(1, 138, 51));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Calibri", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Pengguna");

        addButton.setBackground(new java.awt.Color(255, 255, 255));
        addButton.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        addButton.setForeground(new java.awt.Color(1, 138, 51));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/plus.png"))); // NOI18N
        addButton.setText("Buat Baru");
        addButton.setToolTipText("Buat Data Pengguna Baru");
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
        jLabel4.setText("Pengguna");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
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

        penggunaTabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        penggunaTabel.setRowHeight(25);
        penggunaTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                penggunaTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(penggunaTabel);

        formPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        passwordLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        passwordLabel.setText("Password :");

        usernameLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        usernameLabel.setText("Username :");

        username.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameFocusLost(evt);
            }
        });
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameKeyReleased(evt);
            }
        });

        konfirmasiPasswordLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        konfirmasiPasswordLabel.setText("Konfirmasi Password :");

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

        idPengguna.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

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

        password.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        namaLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        namaLabel.setText("Nama :");

        konfirmasiPassword.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Panjang karakter username harus lebih dari 5 karakter ");

        jLabel6.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Panjang karakter password harus lebih dari 5 karakter ");

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(konfirmasiPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(username)
                    .addComponent(idPengguna)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ubahDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(password)
                    .addComponent(nama)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(formPanelLayout.createSequentialGroup()
                                .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(konfirmasiPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 99, Short.MAX_VALUE)))
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
                .addComponent(idPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordLabel)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(konfirmasiPasswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(konfirmasiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(namaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(batalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(189, 189, 189))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE))
                .addGap(0, 12, Short.MAX_VALUE))
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

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        enableForm();
        ubahDataLabel.setVisible(false);
        autoNumber();
 

    }//GEN-LAST:event_addButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        // TODO add your handling code here:
        if(!ubahDataLabel.isVisible())
            save();
        else
            proccessEdit();
        
    }//GEN-LAST:event_simpanButtonActionPerformed

    private void penggunaTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_penggunaTabelMouseClicked
        // TODO add your handling code here:
        int bar = penggunaTabel.getSelectedRow(); // TODO add your handling code here:
        String id = tabmode.getValueAt(bar, 0).toString();
        String namaValue = tabmode.getValueAt(bar, 1).toString();
        String noHpValue = tabmode.getValueAt(bar, 2).toString();
        String alamatValue = tabmode.getValueAt(bar, 3).toString();
                
//        idPengungjung.setText(id);
//        namaPengunjung.setText(namaValue);
//        telp.setText(noHpValue);
//        alamat.setText(alamatValue);
    }//GEN-LAST:event_penggunaTabelMouseClicked

    private void batalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalButtonActionPerformed
        // TODO add your handling code here:
        disabledForm();
        ubahDataLabel.setVisible(false);


    }//GEN-LAST:event_batalButtonActionPerformed

    private void usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyReleased
        // TODO add your handling code here:
//        isValidate();
    }//GEN-LAST:event_usernameKeyReleased

    private void usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusLost
        // TODO add your handling code here:
        usernameAlreadyExist();
    }//GEN-LAST:event_usernameFocusLost

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
            java.util.logging.Logger.getLogger(Pengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton batalButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextField cariField;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idPengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField konfirmasiPassword;
    private javax.swing.JLabel konfirmasiPasswordLabel;
    private javax.swing.JTextField nama;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTable penggunaTabel;
    private javax.swing.JButton simpanButton;
    private javax.swing.JLabel totalData;
    private javax.swing.JLabel ubahDataLabel;
    private javax.swing.JTextField username;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
