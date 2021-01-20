import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author TOMMY LE
 */

// Active University Course
// This class keeps an array list of students enrolled in the course.
public class ActiveCourse extends Course
{
   // Instance Variables
   private ArrayList<Student> students; // The ArrayList of all students taking the one course.
   private String             semester; // The semester for that one course.
   private String             code;
   private int                lectureStart = 0;
   private int                lectureDuration = 0;
   private String             lectureDay = "";

   /**
    *  === ActiveCourse(name, code, descr, fmt, semester, students) ===
    *  Constructor Method
    *  Object representing an active course with an array of students currently enrolled into this object course.
    * @param name       : Course name, "Computer Science I"
    * @param code       : Course code, "CPS109"
    * @param descr      : Course description, "An introduction to computer science using Python"
    * @param fmt        : Course format, the combination of course name, code and description into a format
    * @param semester   : Course semester, "4"
    * @param students   : An array of students
    */
   public ActiveCourse(String name, String code, String descr, String fmt, String semester, ArrayList<Student> students)
   {
      super(name, code, descr, fmt);
      this.semester = semester;

      // this creates a new array list for each active course object and copies all student object references from the incoming array list.
      this.students = new ArrayList<Student> (students);
   }

   /**
    * === setStart(time) ===
    * Setter method for 'lectureStart' instance variable
    * @param time : the integer representing the time a course begins
    */
   public void setStart(int time)
   {
      lectureStart = time;
   }

   /**
    * === setDuration(time) ===
    * Setter method for 'lectureDuration' instance variable
    * @param time : the integer representing how long a course elapses
    */
   public void setDuration(int time)
   {
      lectureDuration = time;
   }

   /**
    * === setDay(day) ===
    * Setter method for 'setDay' instance variable
    * @param day : the string representing the day when the course is conducted
    */
   public void setDay(String day)
   {
      lectureDay = day;
   }

   /**
    * == getDay() ===
    * Getter method for 'lectureDay' instance variable
    * @return : returns the day the course is conducted
    */
   public String getDay() { return lectureDay; }

   /**
    * === getStart() ===
    * Getter method for 'lectureStart' instance variable
    * @return : returns the start time of a course
    */
   public int getStart() { return lectureStart; }

   /**
    * === getDuration() ===
    * @return : returns the time elapsed for a course in hours
    */
   public int getDuration() {return lectureDuration;}


   /**
    * === doyouExist(studentId) ===
    * Checks if a student is currently enrolled in the active course via their student ID.
    * @param studentId : The string representing the student's ID.
    * @return : Returns the boolean value true if the student is currently enrolled in the course, or false if not.
    */
   public boolean doyouExist(String studentId)
   {
      for (Student s : students)
      {
         if (s.getId().equals(studentId)) {return true;}
      }
      return false;
   }


   /**
    * === addNewKid(newkid) ===
    * Adds a new student to the "students" ArrayList<Student>
    * @param newkid : The <Student> object containing their student name and ID.
    */
   public void addNewKid(Student newkid)
   {
      students.add(newkid);
   }


   /**
    * === removeNewKid(newkid) ===
    * Removes a student from the "students" ArrayList<Student>
    * @param newkid : The <Student> object containing their student name and ID.
    */
   public void removeNewKid(Student newkid)
   {
      students.remove(newkid);
   }


   /**
    * === getOG_description() ===
    * Refers to the original or superclass/parent's getDescription() from Course.java
    * @return : Returns the course description.
    */
   public String getOG_description() {return super.getDescription();}


   /**
    * === printAC_Grades(courseCode) ===
    * Prints each student in this course along with name and student id.
    * @param courseCode : The string representing the course code.
    */
   public void printAC_Grades(String courseCode)
   {
      for (Student s : students)
      {
         System.out.println(s.getId() + " " + s.getName() + " " + s.getGrade(courseCode));
      }
   }


   /**
    * === getAC_Grade(studentId) ===
    * Gets the numeric grade in this Active course for the specific student via their student ID.
    * @param studentId : The string representing the student's ID.
    * @return : Returns the numeric grade in this course.
    */
   public double getAC_Grade(String studentId)
   {
      // Connect the "studentId" with the <Student> object.
      // Examines the ArrayList<Student>
      for (Student s : students)
      {
         // If true, "studentId" is now matched with the <Student> object.
         if (studentId.equals(s.getId()))
         {
            // Search the student's internal list of credit courses to find the course code that matches this
            // active course.
            // This ActiveCourse has a courseCode; use inherited method getCode().
            // Return the grade stored in the credit course object
            return s.getGrade(getCode());
         }
      }
      // Return 0 if student not found.
      return 0;
   }


   // Returns the semester
   public String getSemester() { return semester; }


   // Prints the students in this course  (name and student id)
   public void printClassList()
   {
      for (Student s : students)
      {
         System.out.println("Student ID: " + s.getId() + " Name: " + s.getName());
      }
   }


   // Returns a String containing the course information as well as the semester and the number of students
   // enrolled in the course
   public String getDescription()
   {
      return super.getDescription() + " " + semester + " Enrolment: " + students.size() + "\n";
   }


   // Sort the students in the course by name using the Collections.sort() method with appropriate arguments
   public void sortByName()
   {
      Collections.sort(students, new NameComparator());
   }


   // This class is used to compare two Student objects based on student name
   private class NameComparator implements Comparator<Student>
   {
      @Override
      public int compare(Student o1, Student o2) {
         return o1.getName().compareTo(o2.getName());
      }
   }


   // Sort the students in the course by student id using the Collections.sort()
   public void sortById()
   {
      Collections.sort(students, new IdComparator());
   }


   // This class is used to compare two Student objects based on student id
   private class IdComparator implements Comparator<Student>
   {
      @Override
      public int compare(Student o1, Student o2) {
         return o1.getId().compareTo(o2.getId());
      }
   }
}
