package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Patika;
import com.patikadev.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JScrollPane scrl_student_list;
    private JTable tbl_student_list;
    private JLabel lbl_student_welcome;
    private JButton btn_student_logout;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;


    public StudentGUI(Student student){
        add(wrapper);
        setSize(600,700);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_student_welcome.setText("Hoşgeldiniz, " + student.getName());
        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"Kariyer patikanı seç ve yolculuğuna başla."};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        tbl_student_list.setModel(mdl_patika_list);
        tbl_student_list.getTableHeader().setReorderingAllowed(false);
        tbl_student_list.getTableHeader().setFont(new Font("Courier New",Font.CENTER_BASELINE,15));
        tbl_student_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_student_list.rowAtPoint(point);
                int selected_column = tbl_student_list.columnAtPoint(point);
                tbl_student_list.setRowSelectionInterval(selected_column,selected_row);
                dispose();
                StudentPatikaGUI studentPatikaGUI = new StudentPatikaGUI((String) tbl_student_list.getValueAt(selected_row,selected_column),student.getId());
            }
        });


        btn_student_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
    }
    private void loadPatikaModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_student_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }
}
