package bgu.spl.net.objects;

import bgu.spl.net.api.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class Course {

    private final int courseNumber;
    private final String courseName;
    private final List<Integer> kdamCourses;
    private final int MAX_STUDENTS;
    private int numOfRegistersStudent = 0;
    private final List<String> StudentsRegisters = new ArrayList<>();

    public Course(int courseNumber, String courseName, List<Integer> kdamCourses, int MAX_STUDENTS) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.kdamCourses = kdamCourses;
        this.MAX_STUDENTS = MAX_STUDENTS;
    }

    private Boolean placeAvailable() {
        return numOfRegistersStudent < MAX_STUDENTS;
    }

    //synchronized inorder to prevent students overflow
    public synchronized boolean registerStudent(User user) {
        if (isRegister(user) | !placeAvailable())
            return false;
        StudentsRegisters.add(user.getUsername());
        numOfRegistersStudent++;
        return true;
    }

    public synchronized boolean unregister(User user) {
        boolean output = StudentsRegisters.remove(user.getUsername());
        if (output)
            numOfRegistersStudent--;
        return output;

    }

    public boolean isRegister(User user) {
        return StudentsRegisters.contains(user.getUsername());
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public List<Integer> getKdamCourses() {
        return kdamCourses;
    }

    public boolean kdamCheck(User user) {
        return new HashSet<>(user.getCourseList()).containsAll(kdamCourses);
    }

    //synchronized inorder to prevent change in course during making the course status
    public synchronized String courseStatus() {
        int numOfSetAvailable = MAX_STUDENTS - numOfRegistersStudent;
        StudentsRegisters.sort(Comparator.naturalOrder());

        return "Course: ("+ courseNumber +") "+ courseName
                + '\n' + "Seats Available: " + numOfSetAvailable + "/" + MAX_STUDENTS
                + '\n' + "Students Registered: " + StudentsRegisters.toString().replaceAll("\\s","")+'\0';
    }


}
