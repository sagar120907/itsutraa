package in.sd.backend.controller;

import in.sd.backend.model.Enrollment;
import in.sd.backend.model.Student;
import in.sd.backend.repository.EnrollmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @GetMapping("/profile")
    public String studentProfile(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) return "redirect:/login";

        List<Enrollment> enrollments = enrollmentRepo.findByStudent(student);
        model.addAttribute("student", student);
        model.addAttribute("enrollments", enrollments);

        return "profile";
    }
}
