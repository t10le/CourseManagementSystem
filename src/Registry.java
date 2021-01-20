import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author TOMMY LE
 */

public class Registry
{
    // Instance Variables
    private TreeMap<String, Student>          students   = new TreeMap<>(); // TreeMap of ALL Students; aka Registry.
    private TreeMap<String, ActiveCourse>     courses    = new TreeMap<>(); // TreeMap of Available ACTIVE courses.

    // Constructor Method
    public Registry() throws FileNotFoundException {
        // Add some students
        File humans = new File("students.txt");
        Scanner scanner = new Scanner(humans);

        // Scan through each line inside the text file, then each name and ID.
        // Program terminates with appropriate error message if the following is found in the file:
        // case 1 : file cannot be found
        // case 2 : file is missing a name or ID
        while (scanner.hasNextLine())
        {
            String studentName = scanner.next();
            String ID = scanner.next();
            students.put(ID, new Student(studentName, ID));
        }


        // ==== DEFAULT COURSES ====
        ArrayList<Student> list = new ArrayList<Student>();

        // CPS209
        // Declare and initialize variables.
        String courseName   = "Computer Science II";
        String courseCode   = "CPS209";
        String descr        = "Learn how to write complex programs!";
        String format       = "3Lec 2Lab";

        // Add a new course to "courses" ArrayList<ActiveCourse>
        courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));


        // CPS511
        courseName = "Computer Graphics";
        courseCode = "CPS511";
        descr = "Learn how to write cool graphics programs";
        format = "3Lec";
        courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));

        // CPS643
        courseName = "Virtual Reality";
        courseCode = "CPS643";
        descr = "Learn how to write extremely cool virtual reality programs";
        format = "3Lec 2Lab";
        courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));

        // CPS706
        courseName = "Computer Networks";
        courseCode = "CPS706";
        descr = "Learn about Computer Networking";
        format = "3Lec 1Lab";
        courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020", list));

        // CPS616
        courseName = "Algorithms";
        courseCode = "CPS616";
        descr = "Learn about Algorithms";
        format = "3Lec 1Lab";
        courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020", list));
    }


    /**
     * === getcourses() ===
     * This method will get the course TreeMap.
     * @return : Returns the the TreeMap<> courses.
     */
    public TreeMap<String, ActiveCourse> getCourses()
    {
        return courses;
    }


    /**
     * === checkDupes(name, studentId) ===
     * This method will check if there is a student already registered in the registry.
     * @param name : The string representing the student's name.
     * @param studentId : The string representing the student's ID.
     * @return : Returns the boolean value true if student is NOT already in the registry, or false if they are.
     */
    public boolean checkDupes(String name, String studentId)
    {
        return !(students.containsKey(studentId) || students.containsValue(name));
    }


    /**
     * === doyouExist(studentId) ===
     * This method will see if a student already exists in the registry via their "studentId"
     * @param studentId : The string presenting the student's ID.
     * @return : Return the boolean value true if student is already in the registry, or false if not.
     */
    public boolean doyouExist(String studentId)
    {
        return students.containsKey(studentId);
    }


    /**
     * === addNewStudent(name, id) ===
     * This method will add a new student to the "students" TreeMap, that is the registry.
     * It will only add the student if there are no duplicate matches between the new student and registry.
     * @param name : The string representing the student's name.
     * @param id : The string representing the student's ID.
     * @return : Returns a boolean value of true if new student can be added, or false if not.
     */
    public boolean addNewStudent(String name, String id)
    {
        // If student is not already in the TreeMap, add their key and object value.
        // Return true to indicate that adding the student was successful.
        if (!(students.containsKey(id) || students.containsValue(name)))
        {
            students.put(id, new Student(name, id));
            return true;
        }
        // Return false to indicate adding failed.
        return false;
    }


    /**
     * === removeStudent(studentId) ===
     * This method will remove a student from the "students" TreeMap, that is the registry.
     * It will only remove the student if their student ID is found within the registry.
     * @param studentId : The string representing the student's ID.
     * @return : Returns a boolean value of true if student was successfully removed, or false if not.
     */
    public boolean removeStudent(String studentId)
    {
        if (students.containsKey(studentId))
        {
            students.remove(studentId);
            return true;
        }
        return false;
    }


    /**
     * === printAllStudents() ===
     * Print all registered students.
     */
    public void printAllStudents()
    {
        for (String k : students.keySet())
        {
            System.out.println("ID: " + students.get(k).getId() + " Name: " + students.get(k).getName());
        }
    }


    /**
     * === addCourse(studentId, courseCode) ===
     * This method will add a student to the active course, based on the student Id and a course code.
     * The student must satisfy two conditions:
     * i) student has NOT taken the course in the past.
     * ii) student is NOT currently taking the course.
     *
     * @param studentId : The string representing the student's ID.
     * @param courseCode : The string representing the course code.
     */
    public void addCourse(String studentId, String courseCode)
    {
        // If the student is found in the TreeMap, create the student object and course object.
        if (students.containsKey(studentId) && courses.containsKey(courseCode))
        {
            Student pupil = students.get(studentId);
            ActiveCourse c = courses.get(courseCode);

            // If the student hasn't taken the course already and the course hasn't enrolled the student yet
            if (!pupil.CheckPastCourses(courseCode) && !c.doyouExist(studentId))
            {
                // Add the student to the ActiveCourse list
                c.addNewKid(pupil);
                // Add the course to the student's Course list
                pupil.addCourse(c.getName(), c.getCode(), c.getOG_description(), c.getFormat(), c.getSemester(), 0.0);
            }
        }
    }

    /**
     * === dropCourse(studentId, courseCode) ===
     * This method will remove a student from an active course, based on their student ID and the active course's code.
     * The removal process must satisfy two conditions:
     * i) course code is found in the TreeMap
     * ii) student is currently enrolled in the active course found in the TreeMap
     *
     * @param studentId : The string representing the student's ID.
     * @param courseCode : The string representing the active course's code.
     */
    public void dropCourse(String studentId, String courseCode)
    {
        // If the course is found in the TreeMap, create the student object and course object.
        if (courses.containsKey(courseCode) && students.containsKey(studentId))
        {
            ActiveCourse c = courses.get(courseCode);
            Student pupil = students.get(studentId);
            // If the student is found in the ActiveCourse
            if (c.doyouExist(studentId))
            {
                // Remove the student from the ActiveCourse list
                c.removeNewKid(pupil);
                // Remove the course from the Student's object list of courses
                pupil.removeActiveCourse(courseCode);
            }
        }
    }


    /**
     * === printActiveCourses() ===
     * This method will print all active courses.
     */
    public void printActiveCourses()
    {
        for (String k : courses.keySet())
        {
            System.out.println(courses.get(k).getDescription());
        }
    }


    /**
     * === printClassList(courseCode) ====
     * This method will print the list of students in an active course, indicated via "courseCode".
     * @param courseCode : The string representing the course code.
     */
    public void printClassList(String courseCode)
    {
        if (courses.containsKey(courseCode)) { courses.get(courseCode).printClassList(); }
    }


    // Given a course code, find course and sort class list by student name
    public void sortCourseByName(String courseCode)
    {
        if (courses.containsKey(courseCode)) { courses.get(courseCode).sortByName(); }
    }


    // Given a course code, find course and sort class list by student name
    public void sortCourseById(String courseCode)
    {
        if (courses.containsKey(courseCode)) { courses.get(courseCode).sortById(); }
    }


    /**
     * === printGrades(courseCode) ===
     * This method will print the student names and their grade achieved in the course indicated via "courseCode".
     * @param courseCode : The string representing a course code.
     */
    public void printGrades(String courseCode)
    {
        if (courses.containsKey(courseCode)) { courses.get(courseCode).printAC_Grades(courseCode); }
    }


    /**
     * === printStudentCourses(studentId) ===
     * This method will print all active courses for the specific student indicated via "studentId".
     * @param studentId : The string representing the student's ID.
     */
    public void printStudentCourses(String studentId)
    {
        if (students.containsKey(studentId)) { students.get(studentId).printActiveCourses(); }
    }


    /**
     * === printStudentTranscript(studentId) ===
     * This method will print all completed courses and final grades for the student indicated by student's ID.
     * @param studentId : The string representing student's ID.
     */
    public void printStudentTranscript(String studentId)
    {
        if (students.containsKey(studentId)) { students.get(studentId).printTranscript(); }
    }


    /**
     * === setFinalGrade(courseCode, studentId, grade) ===
     * This method will set the final grade of a course for the student, and set the course inactive, since the
     * student has completed the course.
     * @param courseCode : The string representing the course code.
     * @param studentId : The string representing the student ID.
     * @param grade : The double value representing the course grade.
     */
    public void setFinalGrade(String courseCode, String studentId, double grade)
    {
        // If the course is found in the TreeMap, create the student object and set their grade.
        if (courses.containsKey(courseCode) && students.containsKey(studentId))
        {
            Student pupil = students.get(studentId);
            pupil.setFinalCourse(courseCode, grade);
        }
    }
}
