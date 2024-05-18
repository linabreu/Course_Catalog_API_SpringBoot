package courseCatalog.Controller.Model;

import java.util.HashSet;
import java.util.Set;

import courseCatalog.entity.Instructor;
import courseCatalog.entity.Section;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructorData {
	

	private Long instructorId;
	private String firstName;
	private String lastName;
	private String email;
	private Set<SectionData> sections = new HashSet<>();
	
	//this is already in course
	//private Set<CourseData> coursesTaught = new HashSet<>();
	
	//convert from instructor entity to instructor PJO
	public InstructorData(Instructor instructor) 
	{
		instructorId = instructor.getInstructorId();
		firstName = instructor.getFirstName();
		lastName = instructor.getLastName();
		email = instructor.getEmail();
		
		for (Section section: instructor.getSections())
		{
			sections.add(new SectionData(section));
		}
	}

}
