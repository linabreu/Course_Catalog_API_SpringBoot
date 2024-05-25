package courseCatalog.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import courseCatalog.Controller.Model.CourseData;
import courseCatalog.Controller.Model.DepartmentData;
import courseCatalog.Controller.Model.InstructorData;
import courseCatalog.Controller.Model.SectionData;
import courseCatalog.dao.CourseDao;
import courseCatalog.dao.DepartmentDao;
import courseCatalog.dao.InstructorDao;
import courseCatalog.dao.SectionDao;
import courseCatalog.entity.Course;
import courseCatalog.entity.Department;
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
	
	@Autowired
	private DepartmentDao departmentDao;

	
//--------------------------- save methods for the data objects------------------
	

	@Transactional(readOnly = false)
	public CourseData saveCourse(CourseData courseData) //either find or create a courses
	{
		Long courseID = courseData.getCourseId();
		Course course = findOrCreateCourse(courseID);
		setCourseFields(course, courseData);
		return new CourseData(courseDao.save(course));
	}
	
	@Transactional(readOnly = false)
	public InstructorData saveInstructor(InstructorData instructorData, Long departmentID)
	{
		Department department = findDepartmentById(departmentID);
		Long instructorID = instructorData.getInstructorId();
		Instructor instructor = findOrCreateInstructor(instructorID);
		setInstructorFields(instructor, instructorData);
		department.getInstructors().add(instructor);
		instructor.getDepartments().add(department);
		return new InstructorData(instructorDao.save(instructor));
	}
	@Transactional(readOnly = false)
	public InstructorData patchInstructor(Long instructorID, Long departmentID)
	{
		Department department = findDepartmentById(departmentID);
		Instructor instructor = findOrCreateInstructor(instructorID);
		department.getInstructors().add(instructor);
		instructor.getDepartments().add(department);
		return new InstructorData(instructorDao.save(instructor));
	}
	
	
	
	
	
	//like pet store to employee
	//update the controller method
	
	@Transactional(readOnly = false)
	public SectionData saveSection(SectionData sectionData, Long instructorID, Long courseID)
	{
		
		Instructor instructor = findInstructorById(instructorID);
		Course course = findCourseById(courseID);
		Long sectionID = sectionData.getSectionId();
		Section section = findOrCreateSection(sectionID);
		setSectionFields(section, sectionData);
		section.setInstructor(instructor);
		section.setCourse(course);
		instructor.getSections().add(section);
		course.getSections().add(section);
		return new SectionData(sectionDao.save(section));
	}
	
	@Transactional(readOnly = false)
	public DepartmentData saveDepartment(DepartmentData departmentData)
	{
		Long departmentID = departmentData.getDepartmentId();
		Department department = findOrCreateDepartment(departmentID);
		setDepartmentFields(department, departmentData);
		return new DepartmentData(departmentDao.save(department));	
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
		//section.setInstructor(sectionData.getInstructor());
	}
	
	private void setDepartmentFields(Department department, DepartmentData departmentData)
	{
		department.setDepartmentId(departmentData.getDepartmentId());
		department.setDepartmentName(departmentData.getDepartmentName());
	}
	
//------------------ find or create methods for C and U operations ------------------
	private Course findOrCreateCourse(Long courseID) 
	{
		Course course;
		
		if(Objects.isNull(courseID))//create new course
		{
			course = new Course();
		}
		else//update existing course
		{
			course = findCourseById(courseID);
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
	
	private Department findOrCreateDepartment(Long departmentID) 
	{
		Department department;
		
		if(Objects.isNull(departmentID))//create new course
		{
			department = new Department();
		}
		else//update existing course
		{
			department = findDepartmentById(departmentID);
		}
		return department;	
	}

	@Transactional(readOnly = true)
	private Course findCourseById(Long courseID) 
	{
		return courseDao.findById(courseID).orElseThrow(()-> new NoSuchElementException
				("Instructor with ID " + courseID + " not found!"));
	}
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
	
	@Transactional(readOnly = true)
	private Department findDepartmentById(Long departmentID) 
	{
		return departmentDao.findById(departmentID).orElseThrow(
				() -> new NoSuchElementException("Petstore with ID " + departmentID + " was not found!"));
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
	public List<DepartmentData> retrieveAllDepartments()
	{
		List<Department> departments = departmentDao.findAll();
		List<DepartmentData> departmentData = new LinkedList<>();
		
		for (Department department: departments)
		{
			departmentData.add(new DepartmentData(department));
		}
		return departmentData;
	}
	
	@Transactional(readOnly = true)
	public CourseData retrieveCourseById(Long courseID)
	{
		Course course = findCourseById(courseID);
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
	
	@Transactional(readOnly = true)
	public DepartmentData retrieveDepartmentById(Long departmentID, Long instructorID)
	{
		Department department = findDepartmentById(departmentID);
		return new DepartmentData(department); //convert course entity to data to return it
	}
	
//------------------ delete methods ------------------
	
	@Transactional(readOnly = false)
	public void deleteCourseById(Long courseID) 
	{
		Course course = findCourseById(courseID);
		courseDao.deleteById(courseID);
	}

	@Transactional(readOnly=false)
	public void deleteInstructorById(Long instructorID) 
	{
		Instructor instructor = findInstructorById(instructorID);
		instructorDao.delete(instructor);
	}
	
	@Transactional(readOnly = false)
	public void deleteSectionById(Long sectionID)
	{
		Section section = findSectionById(sectionID);
		sectionDao.deleteById(sectionID);
	}
	
	@Transactional(readOnly = false)
	public void deleteDepartmentById(Long departmentID) 
	{
		Department department = findDepartmentById(departmentID);
		departmentDao.deleteById(departmentID);
	}

	public List<Instructor> retrieveAllInstructorsForInit() 
	{
		List<Instructor> instructors = instructorDao.findAll();
		
		for (Instructor instructor : instructors)
		{
			//instructor.getCoursesTaught().size();
			//instructor.getCoursesTaught();
			instructor.getSections();
		}
		return instructors;
	}


}
