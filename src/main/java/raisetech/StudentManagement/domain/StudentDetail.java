package raisetech.StudentManagement.domain;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {
  @Valid
  private Student student;
  private List<StudentsCourses> studentsCourses;
  private List<String> availableCourses = Arrays.asList("Java", "English", "Python", "Physics", "Chemistry");
}
