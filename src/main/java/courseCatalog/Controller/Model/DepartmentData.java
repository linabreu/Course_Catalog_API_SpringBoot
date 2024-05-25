package courseCatalog.Controller.Model;

import java.util.HashSet;
import java.util.Set;

import courseCatalog.entity.Department;
import courseCatalog.entity.Instructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentData {
	

	private Long departmentId;
	private String departmentName;
	private Set<InstructorData> instructors = new HashSet<>();
	
	public DepartmentData(Department department) 
	{

		departmentId = department.getDepartmentId();
		departmentName = department.getDepartmentName();
		
		for (Instructor instructor: department.getInstructors())
		{
			instructors.add(new InstructorData(instructor));
		}
	}
	

}
