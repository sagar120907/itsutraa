package in.sd.backend.controller;

import in.sd.backend.model.Course;
import in.sd.backend.model.Enrollment;
import in.sd.backend.model.Student;
import in.sd.backend.repository.CourseRepository;
import in.sd.backend.repository.EnrollmentRepository;
import in.sd.backend.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Student loggedStudent = (Student) session.getAttribute("loggedStudent");
        if (loggedStudent == null || !loggedStudent.isAdmin()) {
            return "redirect:/login";
        }

        List<Enrollment> enrollments = enrollmentRepo.findAll();
        List<Student> students = studentRepo.findAll();
        List<Course> courses = courseRepo.findAll();

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("students", students);
        model.addAttribute("courses", courses);

        return "admin-dashboard";
    }

    // ====================== COURSE CRUD ======================
    @GetMapping("/courses")
    public String viewCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "admin-courses";
    }

    @GetMapping("/courses/new")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course) {
        courseRepo.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id).orElseThrow();
        model.addAttribute("course", course);
        return "course-form";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return "redirect:/admin/courses";
    }

    // ====================== STUDENT MANAGEMENT ======================
    @GetMapping("/students")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "admin-students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
        return "redirect:/admin/students";
    }
}
