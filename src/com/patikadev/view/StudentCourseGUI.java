package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;
import com.patikadev.model.Course;
import com.patikadev.model.CourseContent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentCourseGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_studentCourse_list;
    private JScrollPane scrl_studentCourse_list;
    private JButton btn_studentCourse_back;
    private JLabel lbl_studentCourse_name;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private String courseName;
    private Course course;

    public StudentCourseGUI(String courseName){
        this.courseName = courseName;
        this.course = ContentGUI.getSelectedCourse(this.courseName);
        add(wrapper);
        setSize(400,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_studentCourse_name.setText(courseName);
        mdl_content_list = new DefaultTableModel();
        Object[] col_contentList = {"Konular"};
        mdl_content_list.setColumnIdentifiers(col_contentList);

        row_content_list = new Object[col_contentList.length];
        tbl_studentCourse_list.setModel(mdl_content_list);
        tbl_studentCourse_list.getTableHeader().setReorderingAllowed(false);
        tbl_studentCourse_list.getTableHeader().setFont(new Font("Arial",Font.BOLD,15));

        loadContentModel();


        tbl_studentCourse_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_studentCourse_list.rowAtPoint(point);
                int selected_column = tbl_studentCourse_list.columnAtPoint(point);
                tbl_studentCourse_list.setRowSelectionInterval(selected_column,selected_row);
                StudentContentGUI studentContentGUI = new StudentContentGUI((String) tbl_studentCourse_list.getValueAt(selected_row,selected_column));
                //dispose();

            }
        });

        btn_studentCourse_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentPatikaGUI studentPatikaGUI = new StudentPatikaGUI(getPatikaNameById(courseName),course.getUser_id());
                dispose();
            }
        });

    }

    public static int getPatikaIdByCourseName(String courseName){
        String query = "SELECT patika_id FROM public.course WHERE name = ?";
        int id = 0;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,courseName);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                id = rs.getInt("patika_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static String getPatikaNameById(String courseName){
        int id = getPatikaIdByCourseName(courseName);
        String query = "SELECT name FROM public.patika WHERE id = " + id;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                return rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_studentCourse_list.getModel();
        clearModel.setRowCount(0);
        int i;
        int id = this.course.getId();
        for (CourseContent obj: CourseContent.getList(id)) {
            i = 0;
            String title = obj.getTitle();
            row_content_list[i++] = title;
            mdl_content_list.addRow(row_content_list);

        }
    }


}
