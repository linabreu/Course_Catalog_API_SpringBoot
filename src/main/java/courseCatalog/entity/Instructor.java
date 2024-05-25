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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;




//does this need to be embedded??

@Entity
@Data
public class Instructor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long instructorId;
	private String firstName;
	private String lastName;
	
	//@Column(unique = true)
	private String email; //
	

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Section> sections = new HashSet<>();
	
	
	 @EqualsAndHashCode.Exclude
		@ToString.Exclude
		@ManyToMany(cascade = CascadeType.PERSIST)
	 	@JoinTable(name = "instructor_departments", //join table
		joinColumns = @JoinColumn(name = "instructor_id"), //use the snake casing
		inverseJoinColumns = @JoinColumn(name = "department_id"))
		private Set<Department> departments = new HashSet<>();



}
