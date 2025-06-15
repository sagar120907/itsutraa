package in.sd.backend.controller;

import in.sd.backend.model.*;
import in.sd.backend.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EnrollmentController {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @PostMapping("/enroll/{courseId}")
    public String enrollInCourse(@PathVariable Long courseId, HttpSession session) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) return "redirect:/login";

        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) return "redirect:/courses";

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPaymentStatus(true); // mock success
        enrollment.setPaymentId("MOCK-PAYMENT-ID");

        enrollmentRepo.save(enrollment);

        return "redirect:/profile";
    }
}

