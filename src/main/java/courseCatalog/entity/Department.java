package courseCatalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;
	private String departmentName;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "departments", cascade = CascadeType.PERSIST) //will not delete the course if we delete and instructor
	//@JoinTable(name = "department_staff") //join table
		//joinColumns = @JoinColumn(name = "department_id"), //use the snake casing
		//inverseJoinColumns = @JoinColumn(name = "instructor_id"))
	private Set<Instructor> instructors = new HashSet<>();
	
	
	/*
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST) //will not delete the course if we delete and instructor
	@JoinTable(name = "course_instructors", //join table
		joinColumns = @JoinColumn(name = "instructor_id"), //use the snake casing
		inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> coursesTaught = new HashSet<>();
	*/

}
