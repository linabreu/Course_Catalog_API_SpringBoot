package courseCatalog.Controller.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import courseCatalog.Controller.Model.CourseData;
import courseCatalog.Controller.Model.DepartmentData;
import courseCatalog.Controller.Model.InstructorData;
import courseCatalog.Controller.Model.SectionData;
import courseCatalog.service.CourseCatalogService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/course_catalog")
@Slf4j
public class CourseCatalogController {
	
	@Autowired //managed bean
	private CourseCatalogService courseCatalogService;
	
//------------------------------- CREATE ----------------------------------------------
	
	@PostMapping("/course") //post request to /course_catalog/course -> mapped to resource
	@ResponseStatus(code = HttpStatus.CREATED)
	public CourseData createCourse(@RequestBody CourseData courseData)
	{
		log.info("Creating couse {}", courseData); //specify replaceable parameter
		return courseCatalogService.saveCourse(courseData);
	}
	
	@PostMapping("/{departmentID}/instructor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public InstructorData createInstructor(@RequestBody InstructorData instructorData, 
			@PathVariable Long departmentID)
	{
		log.info("Creating instructor {}", instructorData);
		return courseCatalogService.saveInstructor(instructorData, departmentID);
	}
	
	@PostMapping("/{courseID}/{instructorID}/section")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SectionData createSection(@RequestBody SectionData sectionData, @PathVariable Long courseID, @PathVariable Long instructorID)
	{
		log.info("creating section {}", sectionData);
		return courseCatalogService.saveSection(sectionData, courseID, instructorID);
	}
	
	
	@PostMapping("/department")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DepartmentData createDepartment(@RequestBody DepartmentData departmentData)
	{
		log.info("Creating department {}", departmentData);
		return courseCatalogService.saveDepartment(departmentData);
	}
	
	
//-------------------------------- READ ---------------------------------------------
	
	//read all entities
	@GetMapping("/course")
	public List<CourseData> retrieveAllCourses()
	{
		log.info("Retrieving all courses offered by Cactus College");
		return courseCatalogService.retrieveAllCourses();
	}
	
	@GetMapping("/instructor")
	public List<InstructorData> retrieveAllInstructors()
	{
		log.info("Retrieving all instructors at Cactus College");
		return courseCatalogService.retrieveAllInstructors();
	}
	
	@GetMapping("/section")
	public List<SectionData> retrieveAllSections()
	{
		log.info("Retrieving all course sections at Cactus College");
		return courseCatalogService.retrieveAllSections();
	}
	
	@GetMapping("/department")
	public List <DepartmentData> retrieveAllDepartments()
	{
		log.info("Retrieving all departments");
		return courseCatalogService.retrieveAllDepartments();
		
	}
	
	//read a specific entity
	@GetMapping("/course/{courseID}")
	public CourseData retrieveCourseById(@PathVariable Long courseID)
	{
		log.info("Retrieving course with ID = {}");
		return courseCatalogService.retrieveCourseById(courseID);	
	}
	
	@GetMapping("/instructor/{instructorID}")
	public InstructorData retrieveInstructorById(@PathVariable Long instructorID)
	{
		log.info ("Retrieving instructor with ID = {}");
		return courseCatalogService.retrieveInstructorById(instructorID);
	}
	
	@GetMapping("/section/{sectionID}")
	public SectionData retrieveSectionById(@PathVariable Long sectionID)
	{
		log.info("Retrieving section with ID = {}");
		return courseCatalogService.retrieveSectionById(sectionID);
	}
	
	@GetMapping("/department/{instructorID}/{departmentID}")
	public DepartmentData retrieveDepartmentById(@PathVariable Long departmentID, @PathVariable Long instructorID)
	{
		log.info("Retrieving department with ID = {})");
		return courseCatalogService.retrieveDepartmentById(departmentID, instructorID);
	}
	
// ------------------------ Patch -------------------------------------------
	@PatchMapping("/{departmentID}/{instructorID}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public InstructorData patchInstructor( 
			@PathVariable Long departmentID, @PathVariable Long instructorID)
	{
		log.info("Adding instructor {} to department {}", instructorID, departmentID );
		return courseCatalogService.patchInstructor(instructorID, departmentID);
	}
	
// ------------------------ Update ------------------------------------------------
	
	@PutMapping("/course/{courseID}")
	public CourseData updateCourse(@PathVariable Long courseID, @RequestBody CourseData courseData)
	{
		log.info("Updating course with ID {}");
		courseData.setCourseId(courseID);
		return courseCatalogService.saveCourse(courseData);
	}
	
	@PutMapping("/instructor/{instructorID}")
	public InstructorData updateInstructor(@PathVariable Long instructorID, @PathVariable Long departmentID,
			@RequestBody InstructorData instructorData)
	{
		log.info("Updating instructor with ID {}");
		instructorData.setInstructorId(instructorID);
		return courseCatalogService.saveInstructor(instructorData, departmentID);
	}
	
	@PutMapping("/section/{sectionID}")
	public SectionData updateSection(@PathVariable Long sectionID, @PathVariable Long instructorID, @PathVariable Long courseID,
			@RequestBody SectionData sectionData)
	{
		log.info("Updating section with ID {}");
		sectionData.setSectionId(sectionID);
		return courseCatalogService.saveSection(sectionData, courseID, instructorID);
	}
	
	@PutMapping("/department/{instructorID}/{courseID}")
	public DepartmentData updateDepartment (@PathVariable Long departmentID, 
			@PathVariable Long instructorID, @RequestBody DepartmentData departmentData)
	{
		log.info("Updating department with ID {}");
		departmentData.setDepartmentId(departmentID);
		return courseCatalogService.saveDepartment(departmentData);
	}
	
	//------------------------------- Delete ----------------------------------------------
	
	// prevent deletion of all entities in DB
	@DeleteMapping("/course")
	public void deleteAllContributors()
	{
		log.info("Attempting to delete all courses!");
		throw new UnsupportedOperationException("Deleting all courses is not allowed!");	
	}
	@DeleteMapping("/instructor")
	public void deleteAllInstructors()
	{
		log.info("Attempting to delete all instructors!");
		throw new UnsupportedOperationException("Deleting all instructors is not allowed!");	
	}
	@DeleteMapping("/sections")
	public void deleteAllSections()
	{
		log.info("Attempting to delete all sections!");
		throw new UnsupportedOperationException("Deleting the whole course catalog is not allowed!");	
	}
	@DeleteMapping("/department")
	public void deleteAllDepartments()
	{
		log.info("Attempting to delete all departments!");
		throw new UnsupportedOperationException("Deleting all departments is not allowed");
	}
	
	//allow for deletion of single entity from DB
	@DeleteMapping("/course/{courseID}")
	public Map <String, String> deleteCourseByID(@PathVariable Long courseID)
	{
		log.info("Deleting course with ID {}", courseID);
		courseCatalogService.deleteCourseById(courseID);
		return Map.of("message", "Deletion of course with ID =" + courseID + " was sucessful!");
	}
	@DeleteMapping("/instructor/{instructorID}")
	public Map<String, String>deleteInstructorByID(@PathVariable Long instructorID)
	{
		log.info("Deleting instructor with ID {}");
		courseCatalogService.deleteInstructorById(instructorID);
		return Map.of("message", "Deletion of instructor with ID =" + instructorID + " was sucessful!");
	}
	
	@DeleteMapping("/section/{sectionID}")
	public Map<String, String>deleteSectionById(@PathVariable Long sectionID)
	{
		log.info("Deleting section with ID {}");
		courseCatalogService.deleteSectionById(sectionID);
		return Map.of("message", "Deletion of section with ID = " + sectionID + " was sucessful!");
	}
	@DeleteMapping("/department/{departmentID}")
	public Map<String, String>deleteDepartmentById(@PathVariable Long departmentID)
	{
		log.info("Deleting instructor in department ID{)");
		return Map.of("message", "Deletion of department ID " + departmentID + " was sucessful");
	}
	

}
