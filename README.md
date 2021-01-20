# StudentRegistry
A student registration program that manages administrative functions such as enrolling students, adding courses to a student's schedule, dropping courses, setting grades, and printing student schedules.

## Objective
Construct the following commands and implement their expected function:

Command | Function | Example 
--- | --- | --- 
"L" | list all the students in the registry | `>L` 
"Q" | quit the program | `Q` | 
"REG" | register a student by reading a student name and student id from the commandLine scanner | `>reg ElonMusk 12345` |
"DEL" | deletes a student from the registry | `del 12345` | 
"ADDC" | adds a student to an active course | `addc 12345 cps209` | 
"DROPC" | drops a student from an active course | `dropc 12345 cps209` |
"PAC" | prints all active course(s) | `pac` | 
"PCL" | prints class list of students for an active course | `pcl` |
"PGR" | prints student ID and grade for ALL students in an active course | `pgr cps209`
"PSC" | prints all credit courses for a student | `psc 12345`
"SFG | set final grade of a student in a course | `sfg cps209` |
"SCN" | sort list of students in a course by student name | `scn cps209` |
"SCI" | sort list of students in a course by student ID | `sci cps209` |
"SCH" | schedules a course for a certain day, start time and duration | `sch cps209 Mon 800 2`
"CSCH" | clears the schedule of a given course | `csch ` |
"PSCH" | prints the entire schedule |`psch`|


## Analysis & Design

## Testing

## Obstacles

## Conclusion
