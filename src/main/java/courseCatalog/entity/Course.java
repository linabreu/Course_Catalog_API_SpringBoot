package courseCatalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	private String courseName;
	
	@Column(length = 2048)
	private String courseDesc;
	private int credits;
	
	//one to many is course to sections, this course is offered in multiple sections
	//Cascade strategy - delete a course and delete associated sections of that course
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Set<Section> sections = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "coursesTaught", cascade = CascadeType.PERSIST) ///courses is the java field name for the set of courses in instructor class
	private Set<Instructor> instructors = new HashSet<>();
	
	/*
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petstores = new HashSet<>();
	 */


}
