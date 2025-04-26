package bgu.spl.net.api;

import bgu.spl.net.objects.Course;
import bgu.spl.net.objects.Courses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Passive object representing the bgu.spl.net.api.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private final Map<String, User> userHashMap = new ConcurrentHashMap<>();
    private final Courses courses = new Courses();
    private final Map<String, List<Integer>> userCourseMap = new HashMap<>();
    private final Map<Integer, Integer> lineOfCourseMap = new HashMap<>();

    private static class DatabaseHolder {
        private static final Database Instance = new Database();
    }

    //to prevent user from creating new bgu.spl.net.api.Database
    private Database() {
        /** TODO: implement */
        try {
            initialize("Courses.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return DatabaseHolder.Instance;
    }

    /**
     * loads the courses from the file path specified
     * into the bgu.spl.net.api.Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath) throws FileNotFoundException {
        /** TODO: implement */
        try {
            File file = new File(coursesFilePath);
            Scanner scanner = new Scanner(file);
            int numOfLine = 0;
            while (scanner.hasNextLine()) {
                Course course = parseLine(scanner.nextLine());
                lineOfCourseMap.put(course.getCourseNumber(), numOfLine);
                numOfLine++;
                courses.addCourse(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private boolean UserNameAvailable(String UserName) {
        return !userHashMap.containsKey(UserName);
    }

    public boolean addUser(User user) {
        synchronized (userHashMap) {
            if (!UserNameAvailable(user.getUsername()))
                return false;
            userHashMap.put(user.getUsername(), user);
            userCourseMap.put(user.getUsername(), user.getCourseList());
        }
        return true;

    }

    public User getUser(String username) {
        return userHashMap.get(username);
    }

    public boolean registerToCourse(User user, int courseNumber) {
        if (!kdamCheck(user, courseNumber))
            return false;
        return courses.register(user, courseNumber);
    }

    public boolean kdamCheck(User user, int courseNum) {
        return courses.kdamCheck(user, courseNum);
    }

    public List<Integer> getCourseList(String username) {
        return userCourseMap.get(username);
    }

    public List<Integer> getKdams(int courseNum) {
        return courses.getKdamCourses(courseNum);
    }

    public String getCourseStatus(int courseNum) {
        return courses.getCourseStatus(courseNum);
    }

    public Comparator<Integer> CourseLinesComparator() {
        return (o1, o2) -> {
            if (lineOfCourseMap.get(o1) == null | lineOfCourseMap.get(o2) == null)
                throw new IllegalArgumentException("elements not found");
            return lineOfCourseMap.get(o1).compareTo(lineOfCourseMap.get(o2));
        };
    }

    private Course parseLine(String line) {
        // parse the course num
        int from = 0, to;
        to = line.indexOf('|');
        String courseNum = line.substring(from, to);

        //parse the course name
        from = to + 1;
        line = line.substring(from);
        to = line.indexOf('|');
        String courseName = line.substring(0, to);

        //parse the kdams
        from = to + 1;
        line = line.substring(from);
        to = line.indexOf("|");
        String kdams = line.substring(0, to);
        String maxStudents = line.substring(to + 1);
        kdams = kdams.substring(1, kdams.length() - 1);
        List<Integer> kdamList = new ArrayList<>();
        if (!kdams.equals("")) {
            // make the kdam as array
            int[] kdamsArray = Stream.of(kdams.split(",")).mapToInt(Integer::parseInt).toArray();
            //update the array to a list for more application
            for (int i : kdamsArray) {
                kdamList.add(i);
            }
        }

        //parse the data
        int maxStudent = Integer.parseInt(maxStudents);
        int courseNumber = Integer.parseInt(courseNum);

        return new Course(courseNumber, courseName, kdamList, maxStudent);

    }

    public boolean unregister(User user, Integer courseNumber) {
        boolean output = courses.unregister(user, courseNumber);
        if (output) {
            user.getCourseList().remove(courseNumber);
            userCourseMap.get(user.getUsername()).remove(courseNumber);
        }
        return output;
    }


}
