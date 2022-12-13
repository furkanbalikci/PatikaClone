package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseContent {
    private int id;
    private String title;
    private String descrption;
    private String ytLink;
    private String quizQuestn;
    private int course_id;
    private Course course;

    public CourseContent(int id, String title, String descrption, String ytLink, String quizQuestn, int course_id) {
        this.id = id;
        this.title = title;
        this.descrption = descrption;
        this.ytLink = ytLink;
        this.quizQuestn = quizQuestn;
        this.course_id = course_id;
    }

    public static ArrayList<CourseContent> getList(int courseId){
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.content WHERE course_id = " + courseId);
            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String descrption = rs.getString("descrptn");
                String ytLink = rs.getString("yt_link");
                String quiz_qstn = rs.getString("quiz_qstn");
                int course_id = rs.getInt("course_id");
                obj = new CourseContent(id,title,descrption,ytLink,quiz_qstn,course_id);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static ArrayList<CourseContent> getList(String contentTitle){
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
             PreparedStatement pr = DBConnector.getInstance().prepareStatement("SELECT * FROM public.content WHERE title = ?");
             pr.setString(1,contentTitle);
             ResultSet rs = pr.executeQuery();
             while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String descrption = rs.getString("descrptn");
                String ytLink = rs.getString("yt_link");
                String quiz_qstn = rs.getString("quiz_qstn");
                int course_id = rs.getInt("course_id");
                obj = new CourseContent(id,title,descrption,ytLink,quiz_qstn,course_id);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static boolean add(String title,String dscrptn,String ytLink,int course_id) {
        String query = "INSERT INTO public.content (title,descrptn,yt_link,course_id) VALUES (?,?,?,?)";
        CourseContent findContent = getFetch(title);
        if (findContent != null){
            Helper.showMsg("Aynı derse ait aynı konu başlığı olamaz.");
            return false;
        }else {
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1,title);
                pr.setString(2,dscrptn);
                pr.setString(3,ytLink);
                pr.setInt(4,course_id);
                return pr.executeUpdate() != -1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static CourseContent getFetch(String title){
        CourseContent obj = null;
        String query = "SELECT * FROM public.content WHERE title = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new CourseContent(rs.getInt("id"),rs.getString("title"),rs.getString("descrptn"),rs.getString("yt_link"),rs.getString("quiz_qstn"),rs.getInt("course_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean delete(int content_id){
        String query = "DELETE FROM public.content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<CourseContent> searchContentForTitle(String title,int id){
        String query = "SELECT * FROM public.content WHERE title ILIKE '%{{title}}%' AND course_id = " +id;
        query = query.replace("{{title}}",title);
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new CourseContent(rs.getInt("id"),rs.getString("title"),rs.getString("descrptn"),rs.getString("yt_link"),rs.getString("quiz_qstn"),rs.getInt("course_id"));
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static int getCourseID(String name){
        String query = "SELECT id FROM public.course WHERE name = ?";
        int id = 0;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
               id = rs.getInt("id");
            }
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean addQuiz(String title,String quizQuestn){
        String query = "UPDATE public.content SET quiz_qstn=? WHERE title =?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,quizQuestn);
            pr.setString(2,title);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getYtLink() {
        return ytLink;
    }

    public void setYtLink(String ytLink) {
        this.ytLink = ytLink;
    }

    public String getQuizQuestn() {
        return quizQuestn;
    }

    public void setQuizQuestn(String quizQuestn) {
        this.quizQuestn = quizQuestn;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
