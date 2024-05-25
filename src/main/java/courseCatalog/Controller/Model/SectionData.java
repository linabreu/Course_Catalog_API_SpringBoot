package courseCatalog.Controller.Model;

import courseCatalog.entity.Course;
import courseCatalog.entity.Instructor;
import courseCatalog.entity.Section;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SectionData {
	
	private Long sectionId;
	private String semester;
	private String day;
	private String time;
	private String room;

	//private Long instructorId;
	private String instructorName;
	
	
	//convert from section entity to section data
	public SectionData(Section section)
	{
		sectionId = section.getSectionId();
		semester = section.getSemester();
		day = section.getDay();
		time = section.getTime();
		room = section.getRoom();
		//instructorId = section.getInstructor().getInstructorId();
		instructorName = section.getInstructor().getFirstName() + " " + section.getInstructor().getLastName();
	}

}
