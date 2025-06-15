package in.sd.backend.controller;
import in.sd.backend.model.Course;
import in.sd.backend.repository.CourseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/courses")
    public String viewCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "courses";
    }

    @GetMapping("/course-details/{id}")
    public String courseDetails(@PathVariable Long id, Model model, HttpSession session) {
        Course course = courseRepo.findById(id).orElse(null);
        if (course == null) return "redirect:/courses";
        model.addAttribute("course", course);
        return "course-details";
    }
}
