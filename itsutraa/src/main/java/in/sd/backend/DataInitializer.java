package in.sd.backend;

import in.sd.backend.model.Course;
import in.sd.backend.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CourseRepository courseRepo;

    public DataInitializer(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public void run(String... args) {
        if (courseRepo.count() == 0) {
            courseRepo.save(new Course("Core Java", "Learn Java fundamentals", 49));
            courseRepo.save(new Course("Advanced Java", "Servlets, JSP, frameworks", 49));
            courseRepo.save(new Course("JDBC", "Java Database Connectivity", 19));
            courseRepo.save(new Course("SQL", "Database querying with SQL", 19));
            courseRepo.save(new Course("Spring Boot", "Full stack backend with Spring Boot", 199));
            courseRepo.save(new Course("Summer Internship", "Java + Spring Boot + Project", 49));
        }
    }
}

