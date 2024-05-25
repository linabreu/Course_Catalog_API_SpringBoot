package courseCatalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	/*
	 * Relationships
	 * Section - one to many. One course offered in many sections
	 * Instructor- one course taught by many instructors
	 */
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Set<Section> sections = new HashSet<>();
	
	
	/*@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "instructor_id", nullable = false) //use the table name snake case not the java name
	private Instructor instructor;
	*/
	
	
	/*@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "coursesTaught", cascade = CascadeType.PERSIST) ///courses is the java field name for the set of courses in instructor class
	private Set<Instructor> instructor = new HashSet<>();
	
	/*
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petstores = new HashSet<>();
	 */


}
