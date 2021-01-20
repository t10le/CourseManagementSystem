/**
 * @author TOMMY LE
 */

public class CreditCourse extends Course
{
	// Instance Variables
	private String semester;
	private Double grade;
	private Boolean active; // indicates whether this course is currently happening

	// Constructor Method with Parameter Values
	public CreditCourse(String courseName, String code, String descr, String fmt, String sem, double grade)
	{
		super(courseName, code, descr, fmt); // invoke the parametric constructor from Course.java
		semester = sem;
		this.grade = grade;
		active = true; // true by default
	}

	/**
	 * === setGrade(grade) ===
	 * This method will change the grade for the course.
	 * @param g : The double value representing the grade achieved in the course.
	 */
	public void setGrade(double g)
	{
		grade = g;
	}

	/**
	 * === returnGrade() ===
	 * This method will return the student's grade achieved the course.
	 * @return : Returns the double value representing the student's grade in the course.
	 */
	public double returnGrade()
	{
		return grade;
	}


	/**
	 * === getActive() ===
	 * This method will return the state of the creditcourse Object.
	 * @return : return the boolean value true if the course is active, or false if not.
	 */
	public boolean getActive() { return active; }

	// Set the <CreditCourse> object to true.
	public void setActive() { active = true; }

	// Set the <CreditCourse> object to false.
	public void setInactive() { active = false; }


	/**
	 * === displayGrade() ===
	 * This method will display a specific format along with the grade within this course object.
	 * @return : The string format containing the course info, semester, and letter grade.
	 */
	public String displayGrade()
	{
		return getInfo() + " " + semester + " Grade " + convertNumericGrade(grade);
	}

}