package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  public void updateStudentCourseName(int studentId, String courseName) {
    repository.updateStudentCourseName(studentId, courseName);
  }

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.findAllStudentsCourses();
  }

  public List<StudentsCourses> searchStudentsCoursesByStudentId(String studentId) {
    return repository.findStudentsCoursesByStudentId(studentId);
  }

  public Student searchStudentById(int id) {
    return repository.findStudentById(id);
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setCourseStartAt(LocalDate.now());
      studentsCourses.setCourseEndAt(LocalDate.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourses);
    }
  }

  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  @Transactional
  public void updateStudentsCourses(StudentsCourses studentsCourses) {
    repository.updateStudentsCourses(studentsCourses);
  }

  public List<Student> searchCanceledStudentList() {
    return repository.findCanceledStudents();
  }

  @Transactional
  public void restoreStudent(int id) {
    repository.restoreStudent(id);
  }
}