package com.patikadev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_username;
    private JPasswordField passwordField1;
    private JLabel fld_password;
    private JButton btn_login;

    public Example(){
        setContentPane(wrapper);
        setSize(550,450);
        setTitle("Uygulama Adı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2;
        setLocation(x,y);
        setVisible(true);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_username.getText().length() == 0 || fld_password.getText().length() == 0){
                    JOptionPane.showMessageDialog(null,"Lütfen tüm alanları doldurun","Hata",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }


}
