package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDeatil;

@Component
public class StudentConverter {

  public List<StudentDeatil> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDeatil> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDeatil studentDeatil = new StudentDeatil();
      studentDeatil.setStudent(student);

      List<StudentsCourses> convertStudentCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getStudentId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDeatil.setStudentsCourses(convertStudentCourses);
      studentDetails.add(studentDeatil);
    });
    return studentDetails;
  }
}
