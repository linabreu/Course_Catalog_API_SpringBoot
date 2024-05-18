package courseCatalog.Controller.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import courseCatalog.Controller.Model.CourseData;
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
	
	@PostMapping("/{instructorID}/course") //post request to /course_catalog/course -> mapped to resource
	@ResponseStatus(code = HttpStatus.CREATED)
	public CourseData createCourse(@RequestBody CourseData courseData, 
			@PathVariable Long instructorID)
	{
		log.info("Creating couse {}", courseData); //specify replaceable parameter
		return courseCatalogService.saveCourse(courseData, instructorID);
	}
	
	@PostMapping("/instructor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public InstructorData createInstructor(@RequestBody InstructorData instructorData)
	{
		log.info("Creating instructor {}", instructorData);
		return courseCatalogService.saveInstructor(instructorData);
	}
	
	@PostMapping("/section")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SectionData createSection(@RequestBody SectionData sectionData, @PathVariable Long courseID, @PathVariable Long instructorID)
	{
		log.info("creating section {}", sectionData);
		return courseCatalogService.saveSection(sectionData, courseID, instructorID);
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
	
	//read a specific entity
	@GetMapping("/course/{instructorID}/{courseID}")
	public CourseData retrieveCourseById(@PathVariable Long courseID, @PathVariable Long instructorID)
	{
		log.info("Retrieving course with ID = {}");
		return courseCatalogService.retrieveCourseById(courseID, instructorID);	
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
	
// ------------------------ Update ------------------------------------------------
	
	@PutMapping("/course/{courseID}")
	public CourseData updateCourse(@PathVariable Long courseID, @PathVariable Long instructorID,
			@RequestBody CourseData courseData)
	{
		log.info("Updating course with ID {}");
		courseData.setCourseId(courseID);
		return courseCatalogService.saveCourse(courseData, instructorID);
	}
	
	@PutMapping("/instructor/{instructorID}")
	public InstructorData updateInstructor(@PathVariable Long instructorID, 
			@RequestBody InstructorData instructorData)
	{
		log.info("Updating instructor with ID {}");
		instructorData.setInstructorId(instructorID);
		return courseCatalogService.saveInstructor(instructorData);
	}
	
	@PutMapping("/section/{sectionID}")
	public SectionData updateSection(@PathVariable Long sectionID, @PathVariable Long instructorID, @PathVariable Long courseID,
			@RequestBody SectionData sectionData)
	{
		log.info("Updating section with ID {}");
		sectionData.setSectionId(sectionID);
		return courseCatalogService.saveSection(sectionData, courseID, instructorID);
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
	
	
	//allow for deletion of single entity from DB
	@DeleteMapping("/course/{courseID}")
	public Map <String, String> deleteCourseByID(@PathVariable Long courseID, @PathVariable Long instructorID)
	{
		log.info("Deleting course with ID {}", courseID);
		courseCatalogService.deleteCourseById(courseID, instructorID);
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
	

}
