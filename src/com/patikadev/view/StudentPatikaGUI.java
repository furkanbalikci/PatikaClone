package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;
import com.patikadev.model.*;

import javax.swing.*;
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

public class StudentPatikaGUI extends JFrame{
    private JPanel wrapper;
    private JScrollPane scrl_studentPatika_list;
    private JTable tbl_studentPatika_list;
    private JLabel lbl_welcome_patikaName;
    private JButton btn_studentPatika_back;
    private JButton btn_studentPatika_ptkStrt;
    private final JButton btn_studentPatika_join = new JButton("Katıl");
    private String patikaName;
    private int user_id;
    private int patika_id;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    public StudentPatikaGUI(String patikaName,int user_id){
        this.patikaName = patikaName;
        patika_id = User.getPatikaID(patikaName);
        this.user_id = user_id;
        add(wrapper);
        setSize(490,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome_patikaName.setText(this.patikaName +  " Patikası");

        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"Dersler",""};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();
        tbl_studentPatika_list.setModel(mdl_course_list);
        tbl_studentPatika_list.getTableHeader().setReorderingAllowed(false);
        tbl_studentPatika_list.getTableHeader().setFont(new Font("Arial",Font.BOLD,15));

        tbl_studentPatika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_studentPatika_list.rowAtPoint(point);
                int selected_column = tbl_studentPatika_list.columnAtPoint(point);
                tbl_studentPatika_list.setRowSelectionInterval(selected_column,selected_row);
                int isJoined = checkPatikaHasJoined(user_id,patika_id);
                if ((isJoined != 0)){
                    StudentCourseGUI studentCourseGUI = new StudentCourseGUI((String) tbl_studentPatika_list.getValueAt(selected_row,selected_column));
                    dispose();
                }else {
                    Helper.showMsg("Ders içeriklerini görmek için önce patikaya katılmalısın.");
                }
            }
        });

        btn_studentPatika_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = Student.getFetch(user_id);
                StudentGUI studentGUI = new StudentGUI((student));
                dispose();
            }
        });
        btn_studentPatika_ptkStrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int isJoined = checkPatikaHasJoined(user_id,patika_id);
                if ((isJoined == 0)){
                    if (joinToPatika(user_id,patika_id)){
                        btn_studentPatika_ptkStrt.setText("Patikaya katıldın");
                    }
                }
            }
        });
    }
    private void loadCourseModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_studentPatika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj: Course.getList()){
            if (obj.getPatika_id() == patika_id) {
                i = 0;
                row_course_list[i++] = obj.getName();
                mdl_course_list.addRow(row_course_list);
            }
        }
        int isJoined = checkPatikaHasJoined(user_id,patika_id);
        if ((isJoined != 0)){
            btn_studentPatika_ptkStrt.setText("Patikaya katıldın");
        }
    }
    private boolean joinToPatika(int user_id,int patika_id){
        String query = "INSERT INTO public.join_patika (user_id,patika_id) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int checkPatikaHasJoined(int user_id,int patikaID){
        String query = "SELECT patika_id FROM public.join_patika WHERE user_id = " + user_id + " AND  patika_id = " + patikaID;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                return rs.getInt("patika_id");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return 0;
    }

}
