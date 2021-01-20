import java.util.ArrayList;

/**
 * @author TOMMY LE
 */

public class Student implements Comparable<Student>
{
  // Private Instance Variables
  private String studentName;
  private String id;
  private String semester;

  // Public Instance Variables
  public  ArrayList<CreditCourse> courses; // List of courses for ONE INDIVIDUAL STUDENT has taken in the past, or currently enrolled.
  public boolean active;
  public double grade;

  // Constructor Method with Parameter Values
  public Student(String name, String id)
  {
    studentName  = name;
    this.id      = id;

    // Creates a unique ArrayList only for this specific STUDENT.
    courses      = new ArrayList<CreditCourse>();
  }


  /**
   * === CheckPastCourses(courseCode) ===
   * This method will check if the student has already taken this course indicated by the course code in the past by
   * examining their credit course list.
   * @param courseCode : The string representing course code.
   * @return : Returns a boolean value of true if student is already taking the course, or false if not yet.
   */
  public boolean CheckPastCourses(String courseCode)
  {
    for (CreditCourse c : courses)
    {
      if (c.getCode().equalsIgnoreCase(courseCode)) { return true; }
    }
    return false;
  }


  /**
   * === setFinalCourse(courseCode, grade) ===
   * This method will set the student's currently enrolled course to the grade parameter value and the course to inactive.
   * @param courseCode : The string representing the course code.
   * @param grade : The double value representing the FINAL grade for the student.
   */
  public void setFinalCourse(String courseCode, double grade)
  {
    // Search through the student's internal credit course list for the specific course via "courseCode"
    // Examine the ArrayList<CreditCourse>
    for (CreditCourse cc : courses)
    {
      // If true, "courseCode" will connect with the CreditCourse object.
      if (cc.getCode().equalsIgnoreCase(courseCode))
      {
        // Since CreditCourse IS A <Course> object, use its method to set final grade and course inactive.
        cc.setGrade(grade);
        cc.setInactive();
      }
    }
  }


  /**
   * === getGrade(courseCode) ===
   * This method will return the student's grade in the specific course indicated via "courseCode"
   * @param courseCode : The string representing the courseCode.
   * @return : Returns the double value representing the grade achieved in the course.
   */
  public double getGrade(String courseCode)
  {
    // Search through the student's internal list of courses to connect <CreditCourse> object with "courseCode"
    // Examines the ArrayList<CreditCourse>
    for (CreditCourse cc : courses)
    {
      if (cc.getCode().equalsIgnoreCase(courseCode))
      {
        // Retrieve the course's grade from its inherited variable, "grade" using a method.
        return cc.returnGrade();
      }
    }
    // If course not found in ArrayList, return 0
    return 0;
  }


  /**
   * === getId() ===
   * This method will return the student's ID.
   * @return : The string representing the student's 5-digit character ID.
   */
  public String getId() { return id; }


  /**
   * === getName() ===
   * This method will return the student's name.
   * @return : The string representing the student's name.
   */
  public String getName() { return studentName; }


  /**
   * === addCourse(courseName, courseCode, descr, format, sem, grade) ===
   * This method will add a FINISHED and INACTIVE
   * @param courseName : The string representing course name.
   * @param courseCode : The string representing course code.
   * @param descr      : The string representing course description.
   * @param format     : The string representing course format.
   * @param sem        : The string representing the academic semester.
   */
  public void addCourse(String courseName, String courseCode, String descr, String format,String sem, double grade)
  {
    // create a CreditCourse object
    CreditCourse program = new CreditCourse(courseName, courseCode, descr, format, sem, grade);

    // set course active
    // Redundant?
    program.setActive();

    // add to courses array list
    courses.add(program);
  }


  // Prints a student transcript
  // Prints all completed (i.e. non active) courses for this student (course code, course name,
  // semester, letter grade
  public void printTranscript()
  {
    // Go through the list of ArrayList "courses".
    for (CreditCourse c : courses)
    {
      // Search for INACTIVE courses with getActive().
      if (!c.getActive())
      {
        // Print the following format: course code, course name, semester, letter grade.
        System.out.println(c.displayGrade());
      }
    }
  }


  // Prints all active courses this student is enrolled in
  public void printActiveCourses()
  {
    // Go through the list of ArrayList "courses".
    for (CreditCourse c : courses)
    {
      // Search for ACTIVE courses with getActive().
      if (c.getActive())
      {
        // Print the course description for each course inside the student's internal list.
        System.out.println(c.getDescription());
      }
    }
  }


  /**
   * === removeActiveCourse(courseCode) ===
   * This method will only remove a course from the ArrayList <CreditCourse> if the input string containing the course
   * code matches the same found in the ArrayList, and if that course is actively being taken by the student.
   * @param courseCode : The string representing the course code.
   */
  public void removeActiveCourse(String courseCode)
  {
    // Find the credit course in courses ArrayList.
    for (CreditCourse c : courses)
    {
      // Only remove it if there is a match between the object's memory content and object is an active course.
      // Comparison will be case insensitive using .equalsIgnoreCase()
      if (c.getCode().equalsIgnoreCase(courseCode) && c.getActive() )
      {
        courses.remove(c);
        break;
      }
    }
  }


  // Return the object content of id and studentName variable, instead of memory location address.
  public String toString()
  {
    return "Student ID: " + id + " Name: " + studentName;
  }


  /**
   * === equals ===
   * Checks if a student is inside another object
   * @param other : the object that is different from the student
   * @return : true -> student matched; false -> student not matched
   */
  public boolean equals(Object other)
  {
    Student student2 = (Student) other;
    return this.getName().equals(student2.getName()) && this.getId().equals(student2.getId());
  }


  // Set up the compareTo to return the condition value for the order.
  @Override
  public int compareTo(Student o) {
    return studentName.compareTo(o.getName());
  }
}
