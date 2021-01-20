import java.lang.reflect.Array;
import java.util.*;

/**
 * @author TOMMY LE
 */
public class Scheduler {
    // Instance Variables
    TreeMap<String, ActiveCourse>   schedule;

    // Constructor Method
    public Scheduler(TreeMap<String, ActiveCourse> courses)
    {
        schedule = courses;
    }

    /**
     * === setDayAndTime(courseCode, day, startTime, duration) ===
     * This method will set the day and time of a course that already exists in the TreeMap of ActiveCourses
     * @param courseCode : the course code
     * @param day        : the day the course begins
     * @param startTime  : the time the course begins
     * @param duration   : the duration the course is elapsed
     */
    public void setDayAndTime(String courseCode, String day, int startTime, int duration)
    {
        // Local Variables
        int error = 0 ;                     // Default error number for switch case
        int end = 1600;                     // Default end time for course
        if (duration == 2) { end = 1500; }  // If course duration is 2, new end time
        if (duration == 3) { end = 1400; }  // If course duration is 3, new end time

        // The Sets of legal days and legal duration values for the course.
        Set<String> legalDays       = new HashSet<>(Arrays.asList("MON", "TUE", "WED", "THUR", "FRI"));
        Set<Integer> legalDuration  = new HashSet<>(Arrays.asList(1, 2, 3));

        // Conditions that indicate which error exception to throw
        if      (!schedule.containsKey(courseCode))                  { error = 1; }
        else if (!legalDays.contains(day))                           { error = 2; }
        else if (!(800 <= startTime && startTime <= end))            { error = 3; }
        else if (!legalDuration.contains(duration))                  { error = 4; }
        else if (!checkCollision(day, startTime, duration))          { error = 5; }

        // Throw the appropriate exception error based on which case is activated by the conditions above
        switch (error)
        {
            case 1  : throw new UnknownCourseException("Unknown course: " + courseCode);
            case 2  : throw new InvalidDayException("Invalid Lecture Day");
            case 3  : throw new InvalidTimeException("Invalid Lecture Start Time");
            case 4  : throw new InvalidDurationParameter("Invalid Lecture Duration");
            case 5  : throw new LectureTimeCollisionException("Lecture Time Collision");
            default :
                // By default, if no cases are activated, it is safe to set the schedule values for the course
                schedule.get(courseCode).setDay(day);
                schedule.get(courseCode).setStart(startTime);
                schedule.get(courseCode).setDuration(duration);
        }
    }

    /**
     * === clearSchedule(courseCode) ===
     * This method will clear the schedule of the ActiveCourse object
     * @param courseCode : the course code
     */
    public void clearSchedule(String courseCode)
    {
        if (schedule.containsKey(courseCode))
        {
            schedule.get(courseCode).setDay("");
            schedule.get(courseCode).setStart(0);
            schedule.get(courseCode).setDuration(0);
        }
    }


    /**
     * === printSchedule() ===
     * This method will print the 2D array of the timetable for all courses in the TreeMap
     */
    public void printSchedule()
    {
        // Begin with a default grid for the timetable schedule and default time and day values
        String [][] easyGrid =
                {
                        {"    ", "Mon", "Tue", "Wed", "Thur", "Fri"},
                        {"0800", "   ", "   ", "   ", "   ", "   "},
                        {"0900", "   ", "   ", "   ", "   ", "   "},
                        {"1000", "   ", "   ", "   ", "   ", "   "},
                        {"1100", "   ", "   ", "   ", "   ", "   "},
                        {"1200", "   ", "   ", "   ", "   ", "   "},
                        {"1300", "   ", "   ", "   ", "   ", "   "},
                        {"1400", "   ", "   ", "   ", "   ", "   "},
                        {"1500", "   ", "   ", "   ", "   ", "   "},
                        {"1600", "   ", "   ", "   ", "   ", "   "}
                };

        // Cycle through each course in the TreeMap
        for (String c : schedule.keySet()) {
            // Local Variables
            String course   = schedule.get(c).getCode();                                            // Get the course from the TreeMap
            String day      = schedule.get(c).getDay();                                             // Get the course day from the TreeMap
            String start    = Integer.toString(schedule.get(c).getStart());                         // Get the course start time from the TreeMap
            int duration    = schedule.get(c).getDuration();                                        // Get the course duration from the TreeMap
            String end      = Integer.toString((Integer.parseInt(start) + duration * 100 - 100));   // Get the course end time from the TreeMap

            // If the start or end times are these specific values, add a "0" at the beginning
            if (start.equals("800") || start.equals("900")) { start = "0" + start; }
            if (end.equals("800")   || end.equals("900"))   { end   = "0" + end; }

            // Local variables
            int date    = 0;    // Keeps where the day index is for course to add onto
            int initial = 0;    // Keeps where the initial index is for course to add onto
            int stop    = 0;    // Keeps where the last index is for course to add onto

            // If default values are NOT used, continue
            if (!(day.equals("") && start.equals("0"))) {
                for (int row = 0; row != easyGrid.length; row++) {
                    for (int col = 0; col != easyGrid[0].length; col++) {
                        // Match and record the indices for each value to pinpoint where to plot on the 2D array
                        if (easyGrid[row][col].equalsIgnoreCase(day))   { date = col; }
                        if (easyGrid[row][col].equals(start))           { initial = row; }
                        if (easyGrid[row][col].equals(end))             { stop = row; }
                    }
                }
                // Mutate the 2D array with the recorded values
                for (int i = initial; i <= stop; i++) { easyGrid[i][date] = course; }
            }
        }
        // Display the 2D array
        for (String[] arr : easyGrid) {
            for (String content : arr) {
                // Add extra space padding for the day labels
                if (content.equals("Mon") || content.equals("Tue") || content.equals("Wed") || content.equals("Fri")) {content += "   ";}
                // Add one extra space padding for "Thur" because its 4 characters long
                if (content.equals("Thur")) {content += " ";}
                // Add extra space padding for the empty blocks of time to even out the 2D array
                if (content.equals("   ")) {content += "   ";}

                // Print each block of time with a tab space
                System.out.print(content + "\t");
            }
            System.out.println(" ");
        }
    }

    /**
     * === checkCollisions(day, testStart, duration) ===
     * This method will check if there is a collision with another course that is already scheduled
     * @param day       : new course starting day you want to add
     * @param testStart : new course start time you want to add
     * @param duration  : new course duration you want to add
     * @return          : true if it is safe to schedule the course
     */
    public boolean checkCollision(String day, int testStart, int duration)
    {
        int testEnd = testStart + duration*100;
        for (String c : schedule.keySet())
        {
            String targetD = schedule.get(c).getDay();
            int start = schedule.get(c).getStart();
            int end = start + schedule.get(c).getDuration()*100;
            if (targetD.equalsIgnoreCase(day) && !(testStart < start && testEnd <= start || testStart >= end)) { return false; }
        }
        return true;
    }

    // Exception for unknown course for scheduler
    class UnknownCourseException extends RuntimeException
    {
        public UnknownCourseException(String message) {super(message);}

    }
    // Exception for Invalid day for scheduler
    class InvalidDayException extends RuntimeException
    {
        public InvalidDayException(String message) {super(message);}
    }

    // Exception for Invalid time for scheduler
    class InvalidTimeException extends RuntimeException
    {
        public InvalidTimeException(String message) {super(message);}
    }

    // Exception for Invalid duration for scheduler
    class InvalidDurationParameter extends RuntimeException
    {
        public InvalidDurationParameter(String message) {super(message);}
    }

    // Exception for lecture time collisions for scheduler
    class LectureTimeCollisionException extends RuntimeException
    {
        public LectureTimeCollisionException(String message) {super(message);}
    }
}
