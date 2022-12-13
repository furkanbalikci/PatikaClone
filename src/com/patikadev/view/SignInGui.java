package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;
import com.patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SignInGui extends JFrame{
    private JTextField fld_e_mail;
    private JTextField fld_uname;
    private JTextField fld_firstname;
    private JTextField fld_surname;
    private JTextField fld_pass;
    private JTextField fld_pass_again;
    private JPanel wrapper;
    private JButton btn_signin;

    public SignInGui(){
        add(wrapper);
        setSize(600,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = fld_e_mail.getText();
                String uname = fld_uname.getText();
                String firstname = fld_firstname.getText();
                String surname = fld_surname.getText();
                String pass = fld_pass.getText();
                String pass_again = fld_pass_again.getText();

                String name = firstname + " " + surname;
                if (Helper.isFieldEmpty(fld_e_mail) || Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_firstname) || Helper.isFieldEmpty(fld_surname) || Helper.isFieldEmpty(fld_pass) || Helper.isFieldEmpty(fld_pass_again)){
                    Helper.showMsg("fill");
                }else{
                    if (isEmailValid(email) && isPasswordsMatch(pass,pass_again)){
                        addNewStudent(name,uname,pass);
                        LoginGUI loginGUI = new LoginGUI();
                        dispose();
                    }
                }


            }
        });
    }

    private boolean isEmailValid(String mail){
        if (mail.contains("@gmail.com") || mail.contains("@hotmail.com") || mail.contains("@icloud.com") || mail.contains("@yahoo.com") || mail.contains("@yandex.com") || mail.contains("@outlook.com")){
            return true;
        }else {
            Helper.showMsg("Lütfen geçerli bir e-posta girin.");
            return false;
        }
    }
    private boolean isPasswordsMatch(String pass, String pass_again){
        if (!(pass.equals(pass_again))){
            Helper.showMsg("Şifreler aynı değil.");
            return false;
        }
        return true;
    }

    private boolean addNewStudent(String name, String uname, String pass){
        String query = "INSERT INTO public.user (name,uname,pass,type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(uname);
        if (findUser != null){
            Helper.showMsg("Bu kullancı adı alınmış. Lütfen farklı bir kullanıcı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setObject(4,"student", Types.OTHER);
            //int response = pr.executeUpdate();

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }

}
