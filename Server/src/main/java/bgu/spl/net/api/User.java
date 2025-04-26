package bgu.spl.net.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    /** That class save the relevant user information so that when client
     * send commend we can fetch the relevant data from the data base  */
    private String password;
    private String username;
    private boolean isLoggedIn;
    private boolean isAdmin;
    private List<Integer> courseList = null;

    public User(){}
    public User(User user){
    }
    public User(String username, String password, boolean isAdmin) {
        //once it make a user the relevant data will be stored at the data base
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
        this.isAdmin = isAdmin;
        courseList = Collections.synchronizedList(new ArrayList<>());
    }


    public void init(String username , String password , boolean isAdmin){
        //after confirm the data for the data base update the relevant data
        //at th class
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
        this.isAdmin = isAdmin;
        isLoggedIn = true;
        courseList = Database.getInstance().getCourseList(username);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    public List<Integer> getCourseList() {
        return courseList;
    }

    public void addCourse(int courseNum) {
        courseList.add(courseNum);
    }

    public String getStudentStatus() {
        if (isAdmin)
            return null;
        Database database = Database.getInstance();
        courseList.sort(database.CourseLinesComparator());
        String courses = courseList.toString().replaceAll("\\s","");
        return "Student: " + username + "\n"
                + "Courses: " + courseList.toString().replaceAll("\\s","")+"\0";
    }
    public boolean isRegister(int courseNum){
        return courseList.contains(courseNum);
    }



}
