import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author TOMMY LE
 */

public class StudentRegistrySimulator
{
	public static void main(String[] args){
		// Default registry value
		Registry registry = null;

		// Try to create a new registry; if errors are found, exceptions will be caught here
		try {
			registry = new Registry();
		} catch (FileNotFoundException e) {
			System.out.println("students.txt File Not Found");
		} catch (NoSuchElementException e) {
			System.out.println("Bad File Format students.txt");
		}

		// If the registry was successfully made, proceed with program
		if (registry != null) {
			// Creates a <Scheduler> object with TreeMap<> courses passed in.
			Scheduler scheduler = new Scheduler(registry.getCourses());

			Scanner scanner = new Scanner(System.in);
			System.out.print(">");

			while (scanner.hasNextLine()) {
				String inputLine = scanner.nextLine();
				if (inputLine == null || inputLine.equals("")) continue;

				Scanner commandLine = new Scanner(inputLine);
				String command = commandLine.next();

				if (command == null || command.equals("")) continue;

				else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST")) {
					registry.printAllStudents();
				} else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT"))
					return;

					// Sets the schedule for an Active course.
				else if (command.equalsIgnoreCase("SCH")) {
					try {
						String code = commandLine.next().toUpperCase();
						String day = commandLine.next().toUpperCase();
						int start = commandLine.nextInt();
						int duration = commandLine.nextInt();

						scheduler.setDayAndTime(code, day, start, duration);
					}
					catch (NoSuchElementException ignored) {}
					catch (	Scheduler.UnknownCourseException |
							Scheduler.InvalidDayException |
							Scheduler.InvalidTimeException |
							Scheduler.InvalidDurationParameter |
							Scheduler.LectureTimeCollisionException e ) { System.out.println(e.getMessage()); }
				}

				// Clears the schedule of the given course.
				else if (command.equalsIgnoreCase("CSCH")) {
					try {
						String code = commandLine.next().toUpperCase();
						scheduler.clearSchedule(code);
					} catch (NoSuchElementException ignored) {}
				}

				// Prints the entire timetable schedule for all courses.
				else if (command.equalsIgnoreCase("PSCH")) {
					try { scheduler.printSchedule(); }
					catch (NoSuchElementException ignored) {}
				}

				// Register a new student in registry
				else if (command.equalsIgnoreCase("REG")) {
					// Try the REG code.
					try {
						// Get name and student id string
						// e.g. reg JohnBoy 74345
						String name = commandLine.next();
						String id = commandLine.next();
						boolean isDupe = registry.checkDupes(name, id);

						// Checks:
						// Condition i) ensure name is all alphabetic characters.
						// Condition ii) ensure id string is all numeric characters.
						// Condition iii) ensure student name is not already registered.
						// Condition iv) ensure the id is 5 digits long.
						if (isStringOnlyAlphabet(name) && isNumeric(id) && isDupe && id.length() == 5) {
							registry.addNewStudent(name, id);
							System.out.println("Student succesfully added!");
						}

						// If conditions failed, see which failed condition message to print.
						else {
							// If name isn't valid, print error message.
							if (!isStringOnlyAlphabet(name)) {
								System.out.println("Invalid Characters in Name " + name);
							}
							// If id isn't valid, print error message.
							if (!isNumeric(id)) {
								System.out.println("Invalid Characters in ID " + id);
							}
							// If name or id is already in the register, print error message.
							if (!isDupe) {
								System.out.println("Student " + name + " or the ID is already registered");
							}
							// If the id is not 5 digits long, print error message.
							if (id.length() != 5) {
								System.out.println("ID " + id + " must be 5 digits long");
							}
						}
					}
					// If the next() input is empty, just do nothing.
					catch (NoSuchElementException ignored) {}
				}


				// Delete a student from registry
				else if (command.equalsIgnoreCase("DEL")) {
					try {
						// Get student id
						String id = commandLine.next();

						// Condition i) ensure id string is all numeric characters.
						// Condition ii) ensure the id exists in the registry.
						if (isNumeric(id) && registry.doyouExist(id)) {
							// Remove student from registry.
							registry.removeStudent(id);
							System.out.println("Student " + id + " removed.");
						}
						// If failed condition, print the conditional failure's corresponding error message.
						else {
							// Condition i) failure message.
							if (!isNumeric(id)) {
								System.out.println("Invalid Characters in ID " + id);
							}
							// Condition ii) failure message.
							else {
								System.out.println(id + " does not exist.");
							}
						}
					} catch (NoSuchElementException ignored) {}

				}


				// Add a student to an active course
				else if (command.equalsIgnoreCase("ADDC")) {
					try {
						// Get student id and coruse code strings
						String id = commandLine.next();
						String courseCode = commandLine.next().toUpperCase();

						// Add student to course (see class Registry)
						registry.addCourse(id, courseCode);
					} catch (NoSuchElementException ignored) {}
				}


				// Drop a student from an active course
				else if (command.equalsIgnoreCase("DROPC")) {
					try {
						// get student id and course code strings
						String id = commandLine.next();
						String courseCode = commandLine.next().toUpperCase();

						// drop student from course (see class Registry)
						registry.dropCourse(id, courseCode);
					} catch (NoSuchElementException ignored) {}
				}


				// Print all active courses
				else if (command.equalsIgnoreCase("PAC")) {
					try {
						// print all active courses
						registry.printActiveCourses();
					} catch (NoSuchElementException ignored) {}
				}


				// Prints class list for an active course
				else if (command.equalsIgnoreCase("PCL")) {
					try {
						// get course code string
						String courseCode = commandLine.next().toUpperCase();

						// print class list (i.e. students) for this course
						registry.printClassList(courseCode);
					} catch (NoSuchElementException ignored) {}
				}


				// Prints student name, id, and grade for all students in an active course
				else if (command.equalsIgnoreCase("PGR")) {
					try {
						// get course code string
						String courseCode = commandLine.next().toUpperCase();
						// print name, id and grade of all students in active course
						registry.printGrades(courseCode);
					} catch (NoSuchElementException ignored) {}
				}


				// Prints all credit courses for a student
				else if (command.equalsIgnoreCase("PSC")) {
					try {
						// get student id string
						String studentId = commandLine.next();
						// print all credit courses of student
						registry.printStudentCourses(studentId);
					} catch (NoSuchElementException ignored) {}
				}


				// Print a student's transcript
				else if (command.equalsIgnoreCase("PST")) {
					try {
						// get student id string
						String studentId = commandLine.next();
						// print student transcript
						registry.printStudentTranscript(studentId);
					} catch (NoSuchElementException ignored) {}
				}


				// Set final grade of student
				else if (command.equalsIgnoreCase("SFG")) {
					try {
						// get course code, student id, numeric grade
						String courseCode = commandLine.next().toUpperCase();
						String id = commandLine.next();
						double grade = commandLine.nextDouble();

						// use registry to set final grade of this student (see class Registry)
						registry.setFinalGrade(courseCode, id, grade);
					} catch (NoSuchElementException ignored) {}
				}


				// Sort list of students in course by name (i.e. alphabetically)
				else if (command.equalsIgnoreCase("SCN")) {
					try {
						// get course code
						String courseCode = commandLine.next().toUpperCase();
						// sort list of students in course by name (i.e. alphabetically)
						registry.sortCourseByName(courseCode);
					} catch (NoSuchElementException ignored) {}
				}


				// Sort list of students in course by student id
				else if (command.equalsIgnoreCase("SCI")) {
					try {
						// get course code
						String courseCode = commandLine.next().toUpperCase();
						// sort list of students in course by student id
						registry.sortCourseById(courseCode);
					} catch (NoSuchElementException ignored) {}
				}

				System.out.print("\n>");
			}
		}
	}

	/**
	 * === isStringOnlyAlphabet(str) ===
	 * Checks if string 'str' contains only alphabetic characters
	 * @param str : the string of an user input
	 * @return : true -> string contains only alphabets; false -> string does NOT contain only alphabets
	 */
	private static boolean isStringOnlyAlphabet(String str)
	{
		return str.matches("[a-zA-Z]+");
	}

	/**
	 * === isNumeric(str) ===
	 * Checks if string 'str' contains only numeric characters
	 * @param str : the string of an user input
	 * @return : true -> string contains only numerals; false -> string does NOT contain only numerals
	 */
	public static boolean isNumeric(String str)
	{
		try { double num = Double.parseDouble(str); }
		catch (NumberFormatException e) {return false;}
		return true;
	}
}