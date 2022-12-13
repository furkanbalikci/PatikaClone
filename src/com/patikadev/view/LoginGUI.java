package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Educator;
import com.patikadev.model.Operator;
import com.patikadev.model.Student;
import com.patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbuttom;
    private JTextField fld_user_uname;
    private JButton btn_login;
    private JPasswordField fld_user_pass;
    private JButton btn_signin;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)){
                    Helper.showMsg("fill");
                }else {
                    User user = User.getFetch(fld_user_uname.getText(),fld_user_pass.getText());
                    if (user == null) {
                        Helper.showMsg("Kullanıcı Bulunamadı!");
                    }else {
                        switch (user.getType()){
                            case "operator":
                                OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                                break;
                            case "educator":
                                EducatorGUI educatorGUI = new EducatorGUI((Educator) user);
                                break;
                            case "student":
                                StudentGUI studentGUI = new StudentGUI((Student) user);
                                break;
                        }
                        dispose();
                    }
                }
            }
        });
        btn_signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInGui signInGui = new SignInGui();
                dispose();
            }
        });
    }

    /*public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
    }*/

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
