package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39;")
  List<Student> search30s();

  @Select("SELECT * FROM students_courses WHERE course_name = 'Java';")
  List<StudentsCourses> searchStudentsCoursesJava();

//  @Select("SELECT * FROM students_courses")
//  List<StudentsCourses> searchStudentsCourses();
}
