package in.sd.backend.controller;

import in.sd.backend.model.Student;
import in.sd.backend.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private StudentRepository studentRepo;

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model) {
        if (studentRepo.findByEmail(student.getEmail()) != null) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        // TODO: encrypt password before saving in real projects
        studentRepo.save(student);
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String loginStudent(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        Student student = studentRepo.findByEmail(email);
        if (student == null || !student.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        session.setAttribute("loggedStudent", student);

        // Redirect based on whether user is admin or normal student
        if (student.isAdmin()) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/profile";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
