package bgu.spl.net.objects;

import bgu.spl.net.api.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Courses{
    private final Map< Integer, Course > courseMap = new HashMap<>();


    public Courses(){}

    public void addCourse (Course course){
        courseMap.put(course.getCourseNumber(), course);
    }

    public boolean register (User user , int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return false;
        return course.registerStudent(user);

    }

    public List<Integer> getKdamCourses(int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return null;
        return course.getKdamCourses();
    }

    public boolean kdamCheck (User user , int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return false;
        return course.kdamCheck(user);
    }

    public String getCourseStatus(int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return null;
        return course.courseStatus();
    }

    public boolean isRegister (User user , int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return false;
        return course.isRegister(user);
    }

    public boolean unregister(User user , int courseNum){
        Course course = courseMap.get(courseNum);
        if (course == null)
            return false;
        return course.unregister(user);
    }


}
