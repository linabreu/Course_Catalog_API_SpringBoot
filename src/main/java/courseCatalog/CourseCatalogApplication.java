package courseCatalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import courseCatalog.Controller.Model.InstructorData;
import courseCatalog.entity.Instructor;
import courseCatalog.service.CourseCatalogService;



 @SpringBootApplication
 public class CourseCatalogApplication 
 {


	public static void main(String[] args) {
		SpringApplication.run(CourseCatalogApplication.class, args);

	}

}

/*
public class CourseCatalogApplication implements CommandLineRunner {
	
	@Autowired 
	private CourseCatalogService courseCatalogService;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(CourseCatalogApplication.class, args);
		
	}
		
		//breed in video is the same as instructor
		@Override
		public void run(String... args)throws Exception
		{
			List<Instructor> instructors = courseCatalogService.retrieveAllInstructorsForInit();
			instructors.forEach(System.out::println);
		}
}*/