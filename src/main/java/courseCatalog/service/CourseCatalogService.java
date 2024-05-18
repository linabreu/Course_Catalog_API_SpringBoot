package courseCatalog.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import courseCatalog.Controller.Model.CourseData;
import courseCatalog.Controller.Model.InstructorData;
import courseCatalog.Controller.Model.SectionData;
import courseCatalog.dao.CourseDao;
import courseCatalog.dao.InstructorDao;
import courseCatalog.dao.SectionDao;
import courseCatalog.entity.Course;
import courseCatalog.entity.Instructor;
import courseCatalog.entity.Section;



@Service
public class CourseCatalogService {

	@Autowired 
	private CourseDao courseDao;
	
	@Autowired 
	private InstructorDao instructorDao;
	
	@Autowired
	private SectionDao sectionDao;

	
//--------------------------- save methods for the data objects------------------
	
	//course is like customer in PetStore and instructor is like the petStore
	//need to add the ID in the controller method as well for this to work
	@Transactional(readOnly = false)
	public CourseData saveCourse(CourseData courseData, Long instructorID) //either find or create a courses
	{
		Instructor instructor = findInstructorById(instructorID);
		Long courseID = courseData.getCourseId();
		Course course = findOrCreateCourse(courseID, instructorID);
		setCourseFields(course, courseData);
		course.getInstructors().add(instructor);
		instructor.getCoursesTaught().add(course);
		return new CourseData(courseDao.save(course));
		
		/*
		PetStore petStore = findPetStoreById(petStoreID);
		Long customerID = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(customerID, petStoreID );
		copyCustomerFields(customer, customerData);
		customer.getPetstores().add(petStore);
		petStore.getCustomers().add(customer);
		return new PetStoreCustomer(customerDao.save(customer));
		 */
	}
	
	@Transactional(readOnly = false)
	public InstructorData saveInstructor(InstructorData instructorData)
	{
		Long instructorID = instructorData.getInstructorId();
		Instructor instructor = findOrCreateInstructor(instructorID);
		setInstructorFields(instructor, instructorData);
		return new InstructorData(instructorDao.save(instructor));
	}
	
	//like pet store to employee
	//update the controller method
	
	@Transactional(readOnly = false)
	public SectionData saveSection(SectionData sectionData, Long instructorID, Long courseID)
	{
		
		Instructor instructor = findInstructorById(instructorID);

		Long sectionID = sectionData.getSectionId();
		Section section = findOrCreateSection(sectionID);
		setSectionFields(section, sectionData);
		return new SectionData(sectionDao.save(section));
		
		/*
		 * section to instructor is like employee to pet store
		PetStore petStore = findPetStoreById(petStoreID);
		Long employeeID = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeID, petStoreID);
		copyEmployeeFields(employee, employeeData);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		return new PetStoreEmployee(employeeDao.save(employee));
		 */
	}

//------------------ set methods for entity objects ------------------
	private void setCourseFields(Course course, CourseData courseData) 
	{
		course.setCourseDesc(courseData.getCourseDesc());
		course.setCourseName(courseData.getCourseName());
		course.setCredits(courseData.getCredits());
		
		//not sure how to do this part
		//course.setInstructors(courseData.getInstructors());
		//course.setSections(courseData.getSections());
	}

	private void setInstructorFields(Instructor instructor, InstructorData instructorData) 
	{
		instructor.setFirstName(instructorData.getFirstName());
		instructor.setLastName(instructorData.getLastName());
		instructor.setEmail(instructorData.getEmail());	
	}
	
	private void setSectionFields(Section section, SectionData sectionData)
	{
		section.setSemester(sectionData.getSemester());
		section.setDay(sectionData.getDay());
		section.setRoom(sectionData.getRoom());
		section.setTime(sectionData.getTime());
	}
	
//------------------ find or create methods for C and U operations ------------------
	private Course findOrCreateCourse(Long courseID, Long instructorID) 
	{
		Course course;
		
		if(Objects.isNull(courseID))//create new course
		{
			course = new Course();
		}
		else//update existing course
		{
			course = findCourseById(courseID, instructorID);
		}
		return course;	
	}
	
	private Instructor findOrCreateInstructor(Long instructorID)
	{
		Instructor instructor;
		
		if(Objects.isNull(instructorID))
		{
			instructor = new Instructor();
		}
		else
		{
			instructor = findInstructorById(instructorID);
		}
		return instructor;
	}
	
	private Section findOrCreateSection(Long sectionID)
	{
		Section section;
		
		if(Objects.isNull(sectionID))
		{
			section = new Section();
		}
		else
		{
			section = findSectionById(sectionID);
		}
		return section;
	}

	@Transactional(readOnly = true)
	private Course findCourseById(Long courseID, Long instructorID) 
	{
		Course course = courseDao.findById(courseID).orElseThrow(()-> new NoSuchElementException
				("Course with ID " + courseID + " not found!"));
		boolean found = false;
		for (Instructor instructor: course.getInstructors())
		{
			if(instructor.getInstructorId() == instructorID)
			{
				found = true;
				break;
			}
		}
		if (!found)
		{
			throw new IllegalArgumentException("Course with" + courseID + " not taught by instructor " + instructorID);
		}
		return course;
	}
	
	/*
	private Customer findCustomerById(Long customerID, Long petStoreID) 
	{
		Customer customer = customerDao.findById(customerID).orElseThrow(
				()-> new NoSuchElementException("Customer with ID " + customerID + " was not found!"));
		
		boolean found = false;
		for (PetStore petStore: customer.getPetstores())
		{
			if(petStore.getPetStoreID() == petStoreID)
			{
				found = true;
				break;
			}	
		}
		if(!found)
		{
			throw new IllegalArgumentException("Customer with" + customerID + " not in petStore with " + petStoreID);
		}
		return customer;
	}
	 */
	
	@Transactional(readOnly = true)
	private Instructor findInstructorById(Long instructorID)
	{
		return instructorDao.findById(instructorID).orElseThrow(()-> new NoSuchElementException
				("Instructor with ID " + instructorID + " not found!"));
	}
	
	@Transactional(readOnly = true)
	private Section findSectionById(Long sectionID)
	{
		return sectionDao.findById(sectionID).orElseThrow(()-> new NoSuchElementException
				("Section with ID " + sectionID + " not found!"));
	}

//------------------ retrieve methods ------------------
	@Transactional(readOnly = true)
	public List<CourseData> retrieveAllCourses() 
	{
		//retrieve all course entities
		List<Course> courses = courseDao.findAll();
		//create list to hold all of the entities converted into data 
		List<CourseData> courseData = new LinkedList<>();
		
		for(Course course: courses)
		{
			courseData.add(new CourseData(course));
		}
		
		return courseData;
	}
	
	@Transactional(readOnly = true)
	public List<InstructorData> retrieveAllInstructors()
	{
		List<Instructor>instructors = instructorDao.findAll();
		List <InstructorData> instructorData = new LinkedList<>();
		
		for (Instructor instructor: instructors)
		{
			instructorData.add(new InstructorData(instructor));
		}
		return instructorData;
	}
	
	@Transactional(readOnly = true)
	public List <SectionData> retrieveAllSections()
	{
		List<Section> sections = sectionDao.findAll();
		List <SectionData> sectionData = new LinkedList<>();
		
		for (Section section: sections)
		{
			sectionData.add(new SectionData(section));
		}
		return sectionData;
	}
	
	@Transactional(readOnly = true)
	public CourseData retrieveCourseById(Long courseID, Long instructorID)
	{
		Course course = findCourseById(courseID, instructorID);
		return new CourseData(course); //convert course entity to data to return it
	}
	
	@Transactional(readOnly = true)
	public InstructorData retrieveInstructorById(Long instructorID)
	{
		Instructor instructor = findInstructorById(instructorID);
		return new InstructorData(instructor);
	}
	
	@Transactional(readOnly = true)
	public SectionData retrieveSectionById(Long sectionID)
	{
		Section section = findSectionById(sectionID);
		return new SectionData(section);
	}
	
//------------------ delete methods ------------------
	
	@Transactional(readOnly = false)
	public void deleteCourseById(Long courseID, Long instructorID) 
	{
		Course course = findCourseById(courseID, instructorID);
		courseDao.deleteById(courseID);
	}

	@Transactional(readOnly=false)
	public void deleteInstructorById(Long instructorID) 
	{
		Instructor instructor = findInstructorById(instructorID);
		instructorDao.deleteById(instructorID);
	}
	
	@Transactional(readOnly = false)
	public void deleteSectionById(Long sectionID)
	{
		Section section = findSectionById(sectionID);
		sectionDao.deleteById(sectionID);
	}


}
