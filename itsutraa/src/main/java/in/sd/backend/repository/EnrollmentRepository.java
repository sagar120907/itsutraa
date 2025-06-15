package in.sd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sd.backend.model.Enrollment;
import in.sd.backend.model.Student;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
}