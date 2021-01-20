/**
 * @author TOMMY LE
 */

public class Course
{
	// Instance Variables
	private String code;
	private String name;
	private String description;
	private String format;

	// General or Default Constructor Method
	public Course()
	{
		this.code        = "";
		this.name        = "";
		this.description = "";
		this.format      = "";
	}

	// Constructor Method with Parameter Values
	public Course(String name, String code, String descr, String fmt)
	{
		this.code        = code;
		this.name        = name;
		this.description = descr;
		this.format      = fmt;
	}


	/**
	 * === getCode() ===
	 * This method will be used to retrieve and return the course code.
	 * e.g. "CPS209"
	 *
	 * @return : The string representing the course code.
	 */
	public String getCode() { return code; }

	/**
	 * === getName() ===
	 * This method will be used to retrieve and return the course name.
	 * e.g. "Computer Science II"
	 *
	 * @return : The string representing the course name.
	 */
	public String getName() { return name; }

	/**
	 * === getFormat() ===
	 * This method will be used to retrieve and return course's class structure or format.
	 * e.g. "3Lec 2Lab" means 3 hrs of Lectures, and 2 hrs of Labs.
	 *
	 * @return : The string representing the course structure or format.
	 */
	public String getFormat() { return format; }

	/**
	 * === getDescription() ===
	 * This method will be used to retrieve and return the course description, along with its course code, name, and
	 * format, in the following specific sequence:
	 *
	 * e.g.
	 * "CPS209 - Computer Science II
	 * Learn how to write complex programs!
	 * 3Lec 2Lab"
	 *
	 * @return : The string representing the course code, course name, course description, and course format in a
	 * specific sequence.
	 */
	public String getDescription() { return code +" - " + name + "\n" + description + "\n" + format; }

	/**
	 * === getInfo() ===
	 * This method will be used to retrieve and return the course code and course name.
	 * e.g. "CPS209 - Computer Science II"
	 *
	 * @return : The string representing the course code and course name.
	 */
	public String getInfo()
	{
		return code +" - " + name;
	}


	/**
	 * The CONSTANT variable values for each letter grade relative to its initial or minimum numerical score.
	 */
	public static final double
			APLUS = 92.0, A = 88.0, AMINUS = 85.0,
			BPLUS = 82.0, B = 78.0, BMINUS = 75.0,
			CPLUS = 72.0, C = 68.0, CMINUS = 65.0,
			D = 55.0, F = 0;

	/**
	 * === convertNumericGrade() ===
	 * This is a static method that will convert a numerical score value representing the student's
	 * grade to a letter grade string, based on what numerical range it falls within.
	 * e.g. 91 --> "A"
	 *
	 * @param score : Input parameter expects a double type numerical value.
	 * @return : The letter grade string relative to its numerical "score" value under a certain grade range.
	 */
	public static String convertNumericGrade(double score)
	{
		// "A+" : Score must be within the following range: 92 to 100
		if (score >= APLUS && score <= 100) { return "A+"; }

		// "A" : Score must be within the following range: 88 to 91
		else if (score >= A && score < APLUS) {return "A";}

		// "A-" : Score must be within the following range: 85 to 87
		else if (score >= AMINUS && score < A) {return "A-";}

		// "B+" : Score must be within the following range: 82 to 84
		else if (score >= BPLUS && score < AMINUS) {return "B+";}

		// "B" : Score must be within the following range: 78 to 81
		else if (score >= B && score < BPLUS) {return "B";}

		// "B-" : Score must be within the following range: 75 to 77
		else if (score >= BMINUS && score < B) {return "B-";}

		// "C+" : Score must be within the following range: 72 to 74
		else if (score >= CPLUS && score < BMINUS) {return "C+";}

		// "C" : Score must be within the following range: 68 to 71
		else if (score >= C && score < CPLUS) {return "C";}

		// "C-" : Score must be within the following range: 65 to 67
		else if (score >= CMINUS && score < C) {return "C-";}

		// "D" : Score must be within the following range: 55 to 64
		else if (score >= D && score < CMINUS) {return "D";}

		// "F" : Score must be within the following range: 0 to 54
		else if (score >= F && score < D) {return "F";}

		// Error case: Score is out of range between 0 to 100.
		else {return null;}

	}

}
