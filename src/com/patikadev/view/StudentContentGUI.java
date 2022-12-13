package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;
import com.patikadev.model.CourseContent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentContentGUI extends JFrame{
    private JPanel wrapper;
    private JTextArea text_dscrptn;
    private JTextArea text_quiz;
    private JPanel pnl_quiz;
    private JPanel pnl_dscrptn;
    private JPanel pnl_ytLink;
    private JFormattedTextField textField_ytLink;
    private JPanel pnl_comment;
    private JLabel lbl_studentContent_title;
    private JLabel lbl_studentContent_subTitle;
    private JButton btn_studentContent_back;
    private JLabel lbl_studentContent_dscrptn;
    private String title;
    private CourseContent content;

    public StudentContentGUI(String title){
        this.title = title;
        add(wrapper);
        setSize(800,900);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        loadContentModel(title);
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
        lbl_studentContent_title.setText(title);


        btn_studentContent_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int courseID = CourseContent.getFetch(title).getCourse_id();
                StudentCourseGUI studentCourseGUI = new StudentCourseGUI(getCourseNameByCourseId(courseID));
                dispose();
            }
        });
    }


    private void loadContentModel(String title){
        content = CourseContent.getFetch(title);
        textField_ytLink.setText(content.getYtLink());
        textField_ytLink.setEditable(false);
        text_dscrptn.setText(content.getDescrption());
        text_dscrptn.setLineWrap(true);
        text_dscrptn.setWrapStyleWord(true);
        text_dscrptn.setEditable(false);
        text_quiz.setText(content.getQuizQuestn());
        text_quiz.setLineWrap(true);
        text_quiz.setWrapStyleWord(true);
        text_quiz.setEditable(false);
        //remove(pnl_quiz);


    }

    public static String getCourseNameByCourseId(int id){
        String query = "SELECT name FROM public.course WHERE id = " + id;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                return rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
